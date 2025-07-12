package com.jjoony.assetmanagement.domain.auth.service;

import com.jjoony.assetmanagement.domain.auth.dto.LoginRequest;
import com.jjoony.assetmanagement.domain.auth.dto.TokenResponse;
import com.jjoony.assetmanagement.domain.member.entity.Member;
import com.jjoony.assetmanagement.domain.member.repository.MemberRepository;
import com.jjoony.assetmanagement.global.exception.ApiException;
import com.jjoony.assetmanagement.global.exception.ErrorCode;
import com.jjoony.assetmanagement.global.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class AuthService {

    private final JwtProvider jwtProvider;
    private final RefreshTokenService refreshTokenService;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    //accessToken 재발급
    public TokenResponse reissue(String refreshToken) {

        log.info("AccessToken 재발급 요청: refreshToken={}", refreshToken);

        //토큰 만료되었는지 검사
        if (jwtProvider.isExpired(refreshToken)) {
            throw new ApiException(ErrorCode.TOKEN_ALREADY_EXPIRED);
        }

        String email = jwtProvider.getEmail(refreshToken);
        String role = jwtProvider.getRole(refreshToken);

        //저장된 리프레시 토큰과 비교
        String savedToken = refreshTokenService.getToken(email);
        if (!refreshToken.equals(savedToken)) {
            throw new ApiException(ErrorCode.INVALID_REFRESH_TOKEN);
        }

        //회원 조회
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new ApiException(ErrorCode.MEMBER_NOT_FOUND));

        //새로운 토큰 발급
        String newAccessToken = jwtProvider.createAccessToken(email, role);
        String newRefreshToken = jwtProvider.createRefreshToken(email);

        //refreshToken 갱신
        long refreshExpireTime = 1000L * 60 * 60 * 24 * 7;
        refreshTokenService.saveToken(email, newRefreshToken, refreshExpireTime);

        log.info("AccessToken 재발급 완료: email={}, newAccessToken={}", email, newAccessToken);

        return new TokenResponse(newAccessToken, newRefreshToken);
    }

    //로그아웃 블랙리스트 사용X refreshToken만 삭제 후 accessToken은 자동 만료
    //TODO 웹소켓+Redis를 통한 실시간 통신도 있어서 BlackList 사용시 서버 과부하 가능성 때문에 해당 문제 부분 문제 없을 시 BlackList 사용
    public void logout(String refreshToken) {

        log.info("로그아웃 요청: refreshToken={}", refreshToken);

        if (jwtProvider.isExpired(refreshToken)) {
            throw new ApiException(ErrorCode.TOKEN_ALREADY_EXPIRED);
        }

        String email = jwtProvider.getEmail(refreshToken);

        refreshTokenService.deleteToken(email);
        log.info("로그아웃 완료: email={}", email);
    }

    public TokenResponse lgoin(LoginRequest request) {

        Member member = memberRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ApiException(ErrorCode.MEMBER_NOT_FOUND));

        if (!passwordEncoder.matches(request.getPassword(), member.getPassword())) {
            throw new ApiException(ErrorCode.INVALID_PASSWORD);
        }

        String email = member.getEmail();
        String accessToken = jwtProvider.createAccessToken(member.getEmail(), member.getRole());
        String refreshToken = jwtProvider.createRefreshToken(member.getEmail());

        long refreshExpireTime = 1000L * 60 * 60 * 24 * 7;
        refreshTokenService.saveToken(email, refreshToken, refreshExpireTime);

        return new TokenResponse(accessToken, refreshToken);
    }
}

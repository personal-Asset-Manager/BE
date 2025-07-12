package com.jjoony.assetmanagement.global.jwt;

import com.jjoony.assetmanagement.domain.member.entity.Member;
import com.jjoony.assetmanagement.domain.member.repository.MemberRepository;
import com.jjoony.assetmanagement.global.auth.PrincipalDetails;
import com.jjoony.assetmanagement.global.exception.ApiException;
import com.jjoony.assetmanagement.global.exception.ErrorCode;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtProvider {

    private final SecretKey secretKey;
    private final MemberRepository memberRepository;

    private final long accessExpireTime = 1000L * 60 * 30; // 30분
    private final long refreshExpireTime = 1000L * 60 * 60 * 24 * 7; // 7일

    public JwtProvider(@Value("${spring.jwt.secret}") String secret, MemberRepository memberRepository) {
        this.secretKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), Jwts.SIG.HS256.key().build().getAlgorithm());
        this.memberRepository = memberRepository;
    }

    //accessToken 생성
    public String createAccessToken(String email, String role) {
        return Jwts.builder()
                .claim("email", email)
                .claim("category", "access_token")
                .claim("role", role)
                .setExpiration(new Date(System.currentTimeMillis() + accessExpireTime))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }
    //refreshToken 생성
    public String createRefreshToken(String email) {
        return Jwts.builder()
                .claim("email", email)
                .claim("category", "refresh_token")
                .setExpiration(new Date(System.currentTimeMillis() + refreshExpireTime))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    //권한 확인
    public Authentication getAuthentication(String token) {
        if (!getCategory(token).equals("access_token")) {
            throw new ApiException(ErrorCode.INVALID_ACCESS_TOKEN);
        }

        Member member = memberRepository.findByEmail(getEmail(token))
                .orElseThrow(() -> new ApiException(ErrorCode.MEMBER_NOT_FOUND));

        PrincipalDetails principalDetails = new PrincipalDetails(member, null);
        return new UsernamePasswordAuthenticationToken(principalDetails, null, principalDetails.getAuthorities());
    }

    // 사용자명 추출
    public String getEmail(String token) {
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("email", String.class);
    }

    // 권한 추출
    public String getRole(String token) {
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("role", String.class);
    }

    // token 유효확인
    public Boolean isExpired(String token) {
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().getExpiration().before(new Date());
    }

    // accessToken인지 refreshToken 인지 확인
    public String getCategory(String token) {
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("category", String.class);
    }
}

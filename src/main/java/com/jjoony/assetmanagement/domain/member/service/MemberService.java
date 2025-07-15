package com.jjoony.assetmanagement.domain.member.service;

import com.jjoony.assetmanagement.domain.member.dto.MemberMapper;
import com.jjoony.assetmanagement.domain.member.dto.MemberResponse;
import com.jjoony.assetmanagement.domain.member.dto.SignUpRequest;
import com.jjoony.assetmanagement.domain.member.entity.Member;
import com.jjoony.assetmanagement.domain.member.repository.MemberRepository;
import com.jjoony.assetmanagement.global.auth.PrincipalDetails;
import com.jjoony.assetmanagement.global.exception.ApiException;
import com.jjoony.assetmanagement.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final MemberMapper memberMapper;
    private final PasswordEncoder passwordEncoder;

    public MemberResponse updateMember(PrincipalDetails principalDetails, SignUpRequest signUpRequest) {

        log.info("소셜 유저 추가정보 업데이트: signUpRequest={}", signUpRequest);

        Member member = memberRepository.findByEmail(principalDetails.getMember().getEmail())
                .orElseThrow(()-> new ApiException(ErrorCode.MEMBER_NOT_FOUND));

        memberMapper.toUpdate(member,signUpRequest);
        member.setSignedUp(true);
        memberRepository.save(member);

        log.info("추가정보 업데이트 완료: member={}", member);
        return memberMapper.toResponse(member);

    }

    public MemberResponse signup(SignUpRequest signUpRequest) {

        log.info("일반 유저 회원가입: signUpRequest={}", signUpRequest);

        if (memberRepository.findByEmail(signUpRequest.getEmail()).isPresent()) {
            throw new ApiException(ErrorCode.MEMBER_ALREADY_SIGNEDUP);
        }

        String encodePassword = passwordEncoder.encode(signUpRequest.getPassword());

        Member member = memberMapper.toEntity(signUpRequest);
        member.setPassword(encodePassword);
        member.setSignedUp(true);
        memberRepository.save(member);

        log.info("일반유저 회원가입 완료: member={}", member);
        return memberMapper.toResponse(member);
    }
}

package com.jjoony.assetmanagement.domain.auth.service;

import com.jjoony.assetmanagement.domain.auth.dto.KakaoResponse;
import com.jjoony.assetmanagement.domain.auth.dto.OAuth2Response;
import com.jjoony.assetmanagement.domain.member.entity.Member;
import com.jjoony.assetmanagement.domain.member.repository.MemberRepository;
import com.jjoony.assetmanagement.global.auth.PrincipalDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final MemberRepository memberRepsitory;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        //부모 클래스의 메서드를 사용하여 객체를 생성
        OAuth2User oAuth2User = super.loadUser(userRequest);
        Map<String, Object> attributes = oAuth2User.getAttributes();

        OAuth2Response oAuth2Response = new KakaoResponse(attributes);
        String userEmail = oAuth2Response.getEmail();

        //DB에 있는 회원인지 확인 없을시 DB에 저장
        Member member = memberRepsitory.findByEmail(userEmail).orElseGet(() -> {
            Member newMember = Member.builder()
                    .email(userEmail)
                    .role("ROLE_USER")
                    .build();
            return memberRepsitory.save(newMember);
        });

        return new PrincipalDetails(member, oAuth2Response);
    }
}

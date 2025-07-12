package com.jjoony.assetmanagement.global.auth;


import com.jjoony.assetmanagement.domain.auth.dto.OAuth2Response;
import com.jjoony.assetmanagement.domain.member.entity.Member;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@Getter
@RequiredArgsConstructor
public class PrincipalDetails implements OAuth2User, UserDetails {

    private final Member member;
    private final OAuth2Response oAuth2Response;

    @Override
    public String getPassword() { return null; }

    @Override
    public String getUsername() { return member.getEmail(); }

    @Override
    public Map<String, Object> getAttributes() {
        return oAuth2Response.getAttributes();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(member.getRole()));
    }

    @Override
    public String getName() { return member.getEmail(); }

    //밑의 네개의 메서드는 사용자 계정의 필요에 의해 활성화 또는 비활성화

    @Override
    //계정 만료
    public boolean isAccountNonExpired() { return true; }

    @Override
    //계정 잠금
    public boolean isAccountNonLocked() { return true; }

    @Override
    //자격증명 만료
    public boolean isCredentialsNonExpired() { return true; }

    @Override
    //계정 비활성화
    public boolean isEnabled() { return true; }

}

package com.jjoony.assetmanagement.domain.auth.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jjoony.assetmanagement.domain.auth.service.RefreshTokenService;
import com.jjoony.assetmanagement.global.auth.PrincipalDetails;
import com.jjoony.assetmanagement.global.jwt.JwtProvider;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtProvider jwtProvider;
    private final RefreshTokenService refreshTokenService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        PrincipalDetails principal = (PrincipalDetails) authentication.getPrincipal();

        String email = principal.getUsername();
        String role = principal.getAuthorities().toString();

        //토큰 생성
        String accessToken = jwtProvider.createAccessToken(email, role);
        String refreshToken = jwtProvider.createRefreshToken(email);

        //7일 후 만료
        refreshTokenService.saveToken(email, refreshToken, 1000L * 60 * 60 * 24 * 7);

        Map<String, String> tokens = Map.of(
                "access_token", accessToken,
                "refresh_token", refreshToken,
                "email", email
        );

        //토큰을 json형태로 응답
        response.setContentType("application/json");
        response.getWriter().write(new ObjectMapper().writeValueAsString(tokens));

        response.setStatus(HttpServletResponse.SC_OK);

    }
}

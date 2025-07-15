package com.jjoony.assetmanagement.domain.auth.controller;

import com.jjoony.assetmanagement.domain.auth.dto.LoginRequest;
import com.jjoony.assetmanagement.domain.auth.dto.TokenRequest;
import com.jjoony.assetmanagement.domain.auth.dto.TokenResponse;
import com.jjoony.assetmanagement.domain.auth.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @Operation(summary ="토큰 재발급", description = "리프레시 토큰 재발급을 위한 api 입니다.")
    @PostMapping("/reissue")
    public ResponseEntity<TokenResponse> reissue(@RequestBody @Validated TokenRequest request) {
        TokenResponse response = authService.reissue(request.getRefreshToken());
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "일반 회원 로그인", description = "일반 회원 로그인을 위한 api 입니다.")
    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@RequestBody @Validated LoginRequest request) {
        TokenResponse response = authService.lgoin(request);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "로그아웃", description = "로그아웃을 위한 api 입니다.")
    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestBody @Validated TokenRequest request) {
        authService.logout(request.getRefreshToken());
        return ResponseEntity.ok().body("logout success");
    }
}

package com.jjoony.assetmanagement.domain.auth.controller;

import com.jjoony.assetmanagement.domain.auth.dto.TokenRequest;
import com.jjoony.assetmanagement.domain.auth.dto.TokenResponse;
import com.jjoony.assetmanagement.domain.auth.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @Operation(summary = "리프레시 토큰 재발급")
    @PostMapping("/reissue")
    public ResponseEntity<TokenResponse> reissue(@RequestBody TokenRequest request) {
        TokenResponse response = authService.reissue(request.getRefreshToken());
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "로그아웃")
    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestBody TokenRequest request) {
        authService.logout(request.getRefreshToken());
        return ResponseEntity.ok().body("logout success");
    }
}

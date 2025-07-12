package com.jjoony.assetmanagement.domain.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LoginRequest {

    @Schema(description = "email 입력", example = "example@gmail.com")
    @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$",
            message = "올바른 이메일 형식이 아닙니다.")
    @NotBlank
    private String email;

    @Schema(description = "비밀번호 입력", example = "abcde1234!@")
    @NotBlank
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[!@#$%^&*()_+=-])[A-Za-z\\d!@#$%^&*()_+=-]{8,18}$",
            message = "비밀번호는 8~20자 영문, 숫자, 특수문자를 포함해야 합니다.")
    private String password;
}

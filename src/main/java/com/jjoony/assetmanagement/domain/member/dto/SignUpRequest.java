package com.jjoony.assetmanagement.domain.member.dto;

import com.jjoony.assetmanagement.domain.member.entity.JobType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Getter
@NoArgsConstructor
public class SignUpRequest {

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

        @Schema(description = "닉네임 입력", example = "김예시")
        @Length(min = 2, max = 10)
        private String nickname;

        @Schema(description = "성별 입력 남자 : false, 여자 : true", example = "false")
        private Boolean gender;

        @Schema(description = "생일 입력", example = "2001-01-01")
        @NotBlank
        @Pattern(regexp = "^\\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01])$",
                message = "생일은 yyyy-MM-dd 형식이어야 합니다.")
        private String birth;

        @Schema(description = "직업선택", example = "TECH")
        private JobType jobType;

        private String role = "ROLE_USER";

        private SignUpRequest(String email, String password, String nickname, Boolean gender,JobType jobType, String birth, String role) {
            this.email = email;
            this.password = password;
            this.nickname = nickname;
            this.gender = gender;
            this.birth = birth;
            this.jobType = jobType;
            this.role = role;
        }
}

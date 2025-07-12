package com.jjoony.assetmanagement.domain.member.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "member_seq")
    @SequenceGenerator(name = "member_seq", sequenceName = "member_seq", allocationSize = 1)
    private Long memberId;

    @Column(nullable = false, unique = true)
    @NotBlank(message = "이메일은 필수입니다.")
    private String email;

    @Setter
    private String password;

    @Setter
    @Column(length = 50)
    private String nickname;

    @Setter
    @Enumerated(EnumType.STRING)
    private JobType jobType;

    @Setter
    private Boolean gender;

    @Setter
    @Column(length = 50)
    private String birth;

    private final LocalDateTime createdAt = LocalDateTime.now();

    @Setter
    private LocalDateTime loginAt = LocalDateTime.now();

    @Setter
    private boolean isSignedUp = false;

    @Setter
    private String role;

    @Builder
    private Member(String email, String nickname, Boolean gender, String birth,JobType jobType, String role) {
        this.email = email;
        this.gender = gender;
        this.birth = birth;
        this.nickname = nickname;
        this.jobType = jobType;
        this.role = role;
    }
}

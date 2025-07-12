package com.jjoony.assetmanagement.domain.member.entity;


import jakarta.persistence.*;
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
    @Setter
    private String email;

    @Setter
    private String password;

    @Setter
    private String nickname;

    @Setter
    private String role;

    private String name;

    private LocalDateTime createdAt = LocalDateTime.now();


    @Builder
    private Member(Long memberId, String email, String name, String nickname, String role) {
        this.memberId = memberId;
        this.email = email;
        this.nickname = nickname;
        this.name = name;
        this.role = role;
    }
}

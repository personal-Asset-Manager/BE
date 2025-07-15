package com.jjoony.assetmanagement.domain.Category.entity;

import com.jjoony.assetmanagement.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Category {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    private boolean type;

    @Setter
    @Column(unique = true, nullable = false)
    private String name;

    private boolean isDefault;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @Builder
    public Category(boolean type, String name, boolean isDefault, Member member) {
        this.type = type;
        this.name = name;
        this.isDefault = isDefault;
        this.member = member;
    }
}

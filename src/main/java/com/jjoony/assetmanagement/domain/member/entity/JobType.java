package com.jjoony.assetmanagement.domain.member.entity;

public enum JobType {
    OFFICE("사무직"),
    PROFESSIONAL("전문직"),
    TECH("IT/개발자"),
    SERVICE("서비스직"),
    SALES("영업직"),
    STUDENT("학생"),
    FREELANCER("프리랜서"),
    SELF_EMPLOYED("자영업자"),
    RETIRED("은퇴/무직"),
    ETC("기타");

    private final String label;

    JobType(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}

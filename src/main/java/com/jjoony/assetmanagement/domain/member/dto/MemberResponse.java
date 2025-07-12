package com.jjoony.assetmanagement.domain.member.dto;

import com.jjoony.assetmanagement.domain.member.entity.JobType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberResponse {


    private String nickname;

    private Boolean gender;

    private String birth;

    private JobType jobType;

    @Builder
    public MemberResponse(String nickname, Boolean gender, String birth, JobType jobType) {
        this.nickname = nickname;
        this.gender = gender;
        this.birth = birth;
        this.jobType = jobType;
    }
}

package com.jjoony.assetmanagement.domain.member.dto;

import com.jjoony.assetmanagement.domain.member.entity.Member;
import org.springframework.stereotype.Component;

@Component
public class MemberMapper {

    // DTO -> Entity
    public Member toEntity(SignUpRequest signUpRequest){

        return Member.builder()
                .email(signUpRequest.getEmail())
                .jobType(signUpRequest.getJobType())
                .nickname(signUpRequest.getNickname())
                .birth(signUpRequest.getBirth())
                .gender(signUpRequest.getGender())
                .role(signUpRequest.getRole())
                .build();
    }

    // Entity -> DTO
    public MemberResponse toResponse(Member member){

        return MemberResponse.builder()
                .nickname(member.getNickname())
                .jobType(member.getJobType())
                .gender(member.getGender())
                .birth(member.getBirth())
                .build();
    }

    public void toUpdate(Member member, SignUpRequest signUpRequest){
        member.setNickname(signUpRequest.getNickname());
        member.setJobType(signUpRequest.getJobType());
        member.setGender(signUpRequest.getGender());
        member.setBirth(signUpRequest.getBirth());
    }
}

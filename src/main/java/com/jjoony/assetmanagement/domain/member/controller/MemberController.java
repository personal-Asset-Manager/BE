package com.jjoony.assetmanagement.domain.member.controller;

import com.jjoony.assetmanagement.domain.member.dto.MemberResponse;
import com.jjoony.assetmanagement.domain.member.dto.SignUpRequest;
import com.jjoony.assetmanagement.domain.member.service.MemberService;
import com.jjoony.assetmanagement.global.auth.PrincipalDetails;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/member")
public class MemberController {

    private final MemberService memberService;

    @Operation(summary = "일반 이메일 회원가입", description = "일반 회원가입을 위한 api 입니다.")
    @PostMapping("signup")
    public ResponseEntity<MemberResponse> signup(@Validated @RequestBody SignUpRequest signUpRequest) {

        MemberResponse response = memberService.signup(signUpRequest);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "회원 가입 후 추가 정보 기입", description = "추가 정보 기입을 위한 api 입니다.")
    @PutMapping("update")
    public ResponseEntity<MemberResponse> updateMemberInfo(@AuthenticationPrincipal PrincipalDetails principalDetails,
                                                           @RequestBody @Validated SignUpRequest signUpRequest) {

        MemberResponse response = memberService.updateMember(principalDetails, signUpRequest);

        return ResponseEntity.ok(response);
    }


}

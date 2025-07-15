package com.jjoony.assetmanagement.domain.Category.service;

import com.jjoony.assetmanagement.domain.Category.dto.CategoryMapper;
import com.jjoony.assetmanagement.domain.Category.dto.CategoryResponse;
import com.jjoony.assetmanagement.domain.Category.dto.CategoryRequest;
import com.jjoony.assetmanagement.domain.Category.entity.Category;
import com.jjoony.assetmanagement.domain.Category.repository.CategoryRepository;
import com.jjoony.assetmanagement.domain.member.entity.Member;
import com.jjoony.assetmanagement.domain.member.repository.MemberRepository;
import com.jjoony.assetmanagement.global.auth.PrincipalDetails;
import com.jjoony.assetmanagement.global.exception.ApiException;
import com.jjoony.assetmanagement.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final MemberRepository memberRepository;
    private final CategoryMapper categoryMapper;

    public CategoryResponse createCategory(CategoryRequest categoryRequest, PrincipalDetails principalDetails) {

        log.info("사용자 정의 카테고리 추가: name={}", categoryRequest.getName());

        Member member = memberRepository.findByEmail(principalDetails.getMember().getEmail())
                .orElseThrow(() -> new ApiException(ErrorCode.MEMBER_NOT_FOUND));

        Category category = categoryMapper.toEntity(categoryRequest, member);

        category = categoryRepository.save(category);

        log.info("카테고리 추가 완료: category={}", category);
        return categoryMapper.toResponse(category);
    }

    public List<CategoryResponse> getAllCategories(PrincipalDetails principalDetails) {
        log.info("회원의 카테고리 전체 조회: principalDetails={}", principalDetails);

        Member member = memberRepository.findByEmail(principalDetails.getMember().getEmail())
                .orElseThrow(() -> new ApiException(ErrorCode.MEMBER_NOT_FOUND));

        List<Category> categoryList = categoryRepository.findByMemberCategories(member);
        return categoryList.stream()
                .map(category -> {
                    CategoryResponse categoryResponse = categoryMapper.toResponse(category);
                    return categoryResponse;
                }).collect(Collectors.toList());
    }
}

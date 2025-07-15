package com.jjoony.assetmanagement.domain.Category.controller;

import com.jjoony.assetmanagement.domain.Category.dto.CategoryResponse;
import com.jjoony.assetmanagement.domain.Category.dto.CategoryRequest;
import com.jjoony.assetmanagement.domain.Category.service.CategoryService;
import com.jjoony.assetmanagement.global.auth.PrincipalDetails;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;
    
    @Operation(summary = "카테고리 생성", description = "사용자 정의 카테고리 생성 api 입니다.")
    @PostMapping
    public ResponseEntity<CategoryResponse> createCategory(@RequestBody @Validated CategoryRequest categoryRequest, @AuthenticationPrincipal PrincipalDetails principalDetails) {

        CategoryResponse response = categoryService.createCategory(categoryRequest,principalDetails);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "카테고리 조회", description = "회원의 카테고리들을 조회하는 api 입니다.")
    @GetMapping
    public ResponseEntity<List<CategoryResponse>> getAllCategories(@AuthenticationPrincipal PrincipalDetails principalDetails) {
        List<CategoryResponse> response = categoryService.getAllCategories(principalDetails);
        System.out.println("------------------조히회왜안돼"+response);
        return ResponseEntity.ok(response);
    }
}

package com.jjoony.assetmanagement.domain.Category.dto;

import com.jjoony.assetmanagement.domain.Category.entity.Category;
import com.jjoony.assetmanagement.domain.member.entity.Member;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {

    public Category toEntity(CategoryRequest categoryRequest, Member member) {
        return Category.builder()
                .name(categoryRequest.getName())
                .type(categoryRequest.isType())
                .isDefault(true)
                .member(member)
                .build();
    }

    public CategoryResponse toResponse(Category category) {
        return CategoryResponse.builder()
                .name(category.getName())
                .type(category.isType())
                .isDefault(category.isDefault())
                .build();
    }
}

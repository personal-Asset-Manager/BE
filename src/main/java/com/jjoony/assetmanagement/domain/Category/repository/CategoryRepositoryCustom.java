package com.jjoony.assetmanagement.domain.Category.repository;

import com.jjoony.assetmanagement.domain.Category.entity.Category;
import com.jjoony.assetmanagement.domain.member.entity.Member;

import java.util.List;

public interface CategoryRepositoryCustom {
    public List<Category> findByMemberCategories(Member member);
}

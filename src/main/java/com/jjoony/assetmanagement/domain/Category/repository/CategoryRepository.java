package com.jjoony.assetmanagement.domain.Category.repository;

import com.jjoony.assetmanagement.domain.Category.dto.CategoryResponse;
import com.jjoony.assetmanagement.domain.Category.entity.Category;
import com.jjoony.assetmanagement.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long>, CategoryRepositoryCustom {
}

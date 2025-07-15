package com.jjoony.assetmanagement.domain.Category.repository;

import com.jjoony.assetmanagement.domain.Category.entity.Category;
import com.jjoony.assetmanagement.domain.Category.entity.QCategory;
import com.jjoony.assetmanagement.domain.member.entity.Member;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class CategoryRepositoryImpl implements CategoryRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    QCategory qCategory = QCategory.category;



    @Override
    public List<Category> findByMemberCategories(Member member) {

        BooleanExpression isDefaultCategory = qCategory.isDefault.isTrue();
        BooleanExpression isUserCategory = qCategory.member.memberId.eq(member.getMemberId());

        return queryFactory
                .select(qCategory)
                .from(qCategory)
                .where(isDefaultCategory
                        .or(isUserCategory))
                .fetch();
    }
}

//package com.kobobook.www.kobobook.repository.custom;
//
//import com.kobobook.www.kobobook.domain.Item;
//import com.kobobook.www.kobobook.domain.Order;
//import com.querydsl.core.QueryResults;
//import com.querydsl.jpa.impl.JPAQueryFactory;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageImpl;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
//import org.springframework.stereotype.Repository;
//
//import static com.kobobook.www.kobobook.domain.QItem.item;
//import static com.kobobook.www.kobobook.domain.QReview.review;
//
//@Repository
//public class ItemRepositoryImpl extends QuerydslRepositorySupport implements CustomItemRepository {
//
//    private JPAQueryFactory queryFactory;
//
//    public ItemRepositoryImpl(JPAQueryFactory queryFactory) {
//        super(Item.class);
//        this.queryFactory = queryFactory;
//    }
//
//    @Override
//    public Page<Item> findByCategoryId(Integer categoryId, Pageable pageable) {
//        QueryResults results = queryFactory.selectFrom(item)
//                .leftJoin(item.reviews, review).fetchJoin()
//                .where(item.category.id.eq(categoryId))
//                .offset(pageable.getOffset())
//                .limit(pageable.getPageSize())
//                .fetchResults();
//
//        return new PageImpl<>(results.getResults(), pageable, results.getTotal());
//    }
//}

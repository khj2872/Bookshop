package com.kobobook.www.kobobook.repository.custom;

import com.kobobook.www.kobobook.domain.Order;
import com.kobobook.www.kobobook.domain.OrderSearch;
import com.kobobook.www.kobobook.domain.OrderStatus;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;

import static com.kobobook.www.kobobook.domain.QMember.member;
import static com.kobobook.www.kobobook.domain.QOrder.order;

@Repository
public class OrderRepositoryImpl extends QuerydslRepositorySupport implements CustomOrderRepository {

    private final JPAQueryFactory queryFactory;

    public OrderRepositoryImpl(JPAQueryFactory queryFactory) {
        super(Order.class);
        this.queryFactory = queryFactory;
    }


    @Override
    public List<Order> search(OrderSearch orderSearch) {
        return queryFactory
                .selectFrom(order)
                .leftJoin(order.member, member)
                .where(containsMemberName(orderSearch.getMemberName()),
                        eqOrderStatus(orderSearch.getOrderStatus()))
                .fetch();
    }

    @Override
    public List<Order> findByStatus(OrderStatus orderStatus) {
        return queryFactory.selectFrom(order).where(order.status.eq(orderStatus)).fetch();
    }

    private BooleanExpression containsMemberName(String name) {
        if(StringUtils.isEmpty(name)) {
            return null;
        }
        return member.username.contains(name);
    }

    private BooleanExpression eqOrderStatus(OrderStatus orderStatus) {
        if(StringUtils.isEmpty(orderStatus)) {
            return null;
        }
        return order.status.eq(orderStatus);
    }
}

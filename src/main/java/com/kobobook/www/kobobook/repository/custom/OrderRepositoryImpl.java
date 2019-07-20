package com.kobobook.www.kobobook.repository.custom;

import com.kobobook.www.kobobook.domain.Order;
import com.kobobook.www.kobobook.domain.OrderSearch;
import com.kobobook.www.kobobook.domain.OrderStatus;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;

import static com.kobobook.www.kobobook.domain.QDelivery.delivery;
import static com.kobobook.www.kobobook.domain.QItem.item;
import static com.kobobook.www.kobobook.domain.QOrder.order;
import static com.kobobook.www.kobobook.domain.QOrderItem.orderItem;

@Repository
public class OrderRepositoryImpl extends QuerydslRepositorySupport implements CustomOrderRepository {

    private final JPAQueryFactory queryFactory;

    public OrderRepositoryImpl(JPAQueryFactory queryFactory) {
        super(Order.class);
        this.queryFactory = queryFactory;
    }

    @Override
    public List<Order> searchList(Integer memberId, OrderSearch orderSearch) {
        return queryFactory
                .selectDistinct(order).from(order)
                .join(order.orderItems, orderItem).fetchJoin()
                .join(orderItem.item, item).fetchJoin()
                .join(order.delivery, delivery).fetchJoin()
                .where(containsItemName(orderSearch.getItemName()),
                        eqOrderStatus(orderSearch.getOrderStatus()))
                .where(order.member.id.eq(memberId))
                .orderBy(order.orderDate.desc())
                .fetch();
    }

    @Override
    public Order searchOrderDetail(Integer orderId) {
        return queryFactory
                .selectDistinct(order).from(order)
                .join(order.orderItems, orderItem).fetchJoin()
                .join(orderItem.item, item).fetchJoin()
                .join(order.delivery, delivery).fetchJoin()
                .where(order.id.eq(orderId))
                .fetchOne();
    }

    /*
    * 상품명 검색 포함
    * */
    private BooleanExpression containsItemName(String name) {
        if(StringUtils.isEmpty(name)) {
            return null;
        }
        return orderItem.item.name.contains(name);
    }

    /*
    * 주문상태 검색 포함
    * */
    private BooleanExpression eqOrderStatus(OrderStatus orderStatus) {
        if(StringUtils.isEmpty(orderStatus)) {
            return null;
        }
        return order.status.eq(orderStatus);
    }
}

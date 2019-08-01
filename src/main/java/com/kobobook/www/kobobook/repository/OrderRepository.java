package com.kobobook.www.kobobook.repository;

import com.kobobook.www.kobobook.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    @Query("SELECT o " +
            "FROM Order o JOIN FETCH o.member " +
            "JOIN FETCH o.delivery " +
            "WHERE o.id = :orderId")
    Order findOrderWithMemberAndDelivery(Integer orderId);
}

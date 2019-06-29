package com.kobobook.www.kobobook.repository;

import com.kobobook.www.kobobook.domain.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Integer> {
}

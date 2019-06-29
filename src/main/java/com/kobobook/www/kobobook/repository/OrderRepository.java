package com.kobobook.www.kobobook.repository;

import com.kobobook.www.kobobook.domain.Member;
import com.kobobook.www.kobobook.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    List<Order> findByMember(Member member);
}

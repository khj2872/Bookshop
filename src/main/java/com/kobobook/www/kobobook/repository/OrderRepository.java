package com.kobobook.www.kobobook.repository;

import com.kobobook.www.kobobook.domain.Member;
import com.kobobook.www.kobobook.domain.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {

    Page<Order> findByMember(Member member, Pageable pageable);


}

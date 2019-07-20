package com.kobobook.www.kobobook.repository;

import com.kobobook.www.kobobook.domain.Cart;
import com.kobobook.www.kobobook.domain.Item;
import com.kobobook.www.kobobook.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Integer> {
    Cart findByMemberAndItem(Member member, Item item);

    @Query("SELECT c FROM Cart c JOIN FETCH c.item WHERE c.member = :member")
    List<Cart> findByMember(Member member);
}

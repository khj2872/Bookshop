package com.kobobook.www.kobobook.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Getter
@Setter
@ToString
public class Cart {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CART_ID")
    private Integer id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    private Item item;

    private long price;

    private int count;

    private double savingRate;

    public static Cart createCart(Member member, Item item, int count, double savingRate) {
        Cart cart = new Cart();
        cart.setMember(member);
        cart.setItem(item);
        cart.setCount(count);
        cart.setSavingRate(savingRate);

        return cart;
    }

}

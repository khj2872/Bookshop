package com.kobobook.www.kobobook.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class OrderSearch {

    private String itemName;

    private OrderStatus orderStatus;

}

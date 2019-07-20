package com.kobobook.www.kobobook.dto;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemDTO {

    private Integer id;

    private ItemDTO.ItemSimple item;

    private long price;

    private int count;

}

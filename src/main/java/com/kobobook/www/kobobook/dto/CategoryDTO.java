package com.kobobook.www.kobobook.dto;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDTO implements Serializable {

    private Integer id;
    private String name;

}

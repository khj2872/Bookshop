package com.kobobook.www.kobobook.dto;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDTO {
    private Integer id;

    private MemberDTO member;

    private double rating;

    private Date regDate;

    private String content;

}

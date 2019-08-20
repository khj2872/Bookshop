package com.kobobook.www.kobobook.dto;

import lombok.*;

import java.time.LocalDateTime;
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

    private LocalDateTime regDate;

    private String content;

}

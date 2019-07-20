package com.kobobook.www.kobobook.dto;

import lombok.*;

import java.util.Date;


public class ReviewDTO {

    @Getter
    @Setter
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    static class Review {
        private Integer id;

        private MemberDTO member;

        private double rating;

        private Date regDate;

        private String content;
    }

}

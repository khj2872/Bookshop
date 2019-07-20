package com.kobobook.www.kobobook.dto;

import lombok.*;

import java.util.Date;
import java.util.List;

public class ItemDTO {

    @Getter
    @Setter
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ItemDetail {
        private Integer id;

        private String name;

        private long price;

        private String image;

        private String writer;

        private String ISBN;

        private String publicationDate;

        private Date regDate;

        private Date updateDate;

        private String detail;

        private long stock;

        private double savingRate;

        private double avgRating;

        private CategoryDTO category;

        private List<ReviewDTO.Review> reviews;

    }

    @Getter
    @Setter
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ItemSimple {
        private Integer id;

        private String name;

        private long price;

        private String image;
    }

    @Getter
    @Setter
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Item {
        private Integer id;

        private String name;

        private long price;

        private String image;

        private String writer;

        private String ISBN;

        private String publicationDate;

        private Date regDate;

        private Date updateDate;

        private String detail;

        private long stock;

        private double savingRate;

        private double avgRating;

    }



}

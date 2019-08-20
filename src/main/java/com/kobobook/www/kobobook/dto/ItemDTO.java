package com.kobobook.www.kobobook.dto;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public class ItemDTO {

    @Getter
    @Setter
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ItemWithCategory implements Serializable {
        private Integer id;

        private String name;

        private long price;

        private String image;

        private String writer;

        private String ISBN;

        private String publicationDate;

        private LocalDateTime regDate;

        private LocalDateTime updateDate;

        private String detail;

        private long stock;

        private double savingRate;

        private double avgRating;

        private CategoryDTO category;

    }

    @Getter
    @Setter
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ItemSimple implements Serializable {
        private Integer id;

        private String name;

        private long price;

        private String image;

        private double avgRating;
    }

    @Getter
    @Setter
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Item implements Serializable {
        private Integer id;

        private String name;

        private long price;

        private String image;

        private String writer;

        private String ISBN;

        private String publicationDate;

        private LocalDateTime regDate;

        private LocalDateTime updateDate;

        private String detail;

        private long stock;

        private double savingRate;

        private double avgRating;

    }

}

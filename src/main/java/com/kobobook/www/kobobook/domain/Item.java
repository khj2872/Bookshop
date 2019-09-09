package com.kobobook.www.kobobook.domain;

import com.kobobook.www.kobobook.exception.NotEnoughStockException;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
@Builder
public class Item {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ITEM_ID")
    private Integer id;

    private String name;

    private String writer;

    private String ISBN;

    private String publicationDate;

    private LocalDateTime regDate;

    private LocalDateTime updateDate;

    @Lob
    private String detail;

    private long price;

    private long stock;

    private double savingRate;

    private double avgRating;

    @ManyToOne(fetch = FetchType.LAZY)
    private Category category;

    private String image;

    @OneToMany(mappedBy = "item")
    private List<Review> reviews = new ArrayList<>();

    /* 재고 추가 */
    public void addStock(long quantity) {
        this.stock += quantity;
    }

    public void removeStock(long quantity) {
        long restStock = this.stock - quantity;
        if(restStock < 0) {
            throw new NotEnoughStockException("need more stock");
        }
        this.stock = restStock;
    }

}

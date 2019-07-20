package com.kobobook.www.kobobook.domain;

import com.kobobook.www.kobobook.exception.NotEnoughStockException;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
public class Item {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ITEM_ID")
    private Integer id;

    private String name;

    private String writer;

    private String ISBN;

    private String publicationDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date regDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date updateDate;

    @Lob
    private String detail;

    private long price;

    private long stock;

    private double savingRate;

    private double avgRating;

//    @JsonManagedReference
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
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

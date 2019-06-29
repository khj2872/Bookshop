package com.kobobook.www.kobobook.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.kobobook.www.kobobook.exception.NotEnoughStockException;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

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

    private String subName;

    private String writer;

    private String writerInfo;

    private String translater;

    private String ISBN;

    private String pageSize;

    private String publicationDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date regDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date updateDate;

    @Lob
    private String detail;

    private Long price;

    private Long stock;

    private Integer savingRate;

    private Float avgRating;

    @ManyToOne
    @JsonManagedReference
    private Category category;

    @JsonIgnore
    @Transient
    private int categoryId;

    private String image;

    @JsonBackReference
    @OneToMany(mappedBy = "item")
    private List<Review> reviews = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY)
    private UploadFile uploadFile;

    @JsonIgnore
    @Transient
    private int uploadedId;

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

package com.kobobook.www.kobobook.elasticsearch;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.util.Date;

@Setter
@Getter
@ToString
@Document(indexName = "item", type = "_doc")
public class EsItem {

    @Id
    private Integer item_id;

    private String name;

    private String writer;

    private String ISBN;

    private String publicationDate;

    private Date regDate;

    private String detail;

    private Long price;

    private Long stock;

    private String image;

    private String category;

}

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
    private Integer itemid;

    private String name;

    private String subName;

    private String writer;

    private String writerInfo;

    private String translater;

    private String ISBN;

    private String pageSize;

    private String publicationdate;

    private Date regDate;

    private String detail;

    private Long price;

    private Long stock;

    private Integer savingRate;

    private String image;

    private String category;

}

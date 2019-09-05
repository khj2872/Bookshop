package com.kobobook.www.kobobook.elasticsearch;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.*;

import java.io.Serializable;
import java.util.Date;

@Setter
@Getter
@ToString
@Document(indexName = "items", type = "_doc")
@Setting(settingPath = "/settings/settings.json")
@Mapping(mappingPath = "/mappings/mappings.json")
public class EsItem implements Serializable {

    @Id
    private Integer id;

    private String name;

    private String writer;

    private String publicationdate;

    private Date regdate;

    private String detail;

    private Long price;

    private Long stock;

    private String image;

    private String category;

    private double avgrating;

}

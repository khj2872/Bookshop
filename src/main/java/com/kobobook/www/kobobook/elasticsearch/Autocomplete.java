package com.kobobook.www.kobobook.elasticsearch;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.elasticsearch.annotations.Document;

import java.io.Serializable;

@Setter
@Getter
@ToString
@Document(indexName = "please", type = "_doc")
public class Autocomplete implements Serializable {

    private String name;

}

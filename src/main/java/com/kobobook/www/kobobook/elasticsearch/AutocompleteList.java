package com.kobobook.www.kobobook.elasticsearch;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.elasticsearch.annotations.Document;

@Setter
@Getter
@ToString
@Document(indexName = "item", type = "_doc")
public class AutocompleteList {

    private String name;

}

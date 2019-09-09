package com.kobobook.www.kobobook.repository;

import com.kobobook.www.kobobook.elasticsearch.EsItem;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface EsItemRepository extends ElasticsearchRepository<EsItem, Integer> {
}

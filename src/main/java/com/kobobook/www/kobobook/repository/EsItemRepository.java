package com.kobobook.www.kobobook.repository;

import com.kobobook.www.kobobook.elasticsearch.EsItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface EsItemRepository extends ElasticsearchRepository<EsItem, Integer> {

    Page<EsItem> findByCategory(String category, Pageable pageable);

}

package com.kobobook.www.kobobook.service;

import com.kobobook.www.kobobook.config.EsClient;
import com.kobobook.www.kobobook.domain.Category;
import com.kobobook.www.kobobook.repository.CategoryRepository;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private EsClient client;

    /*
    * 카테고리 리스트
    * */
//    @Transactional
//    public List<Map<String, Object>> readCategories() {
//        List<Map<String, Object>> mapList = new ArrayList<>();
//        try {
//            TermsAggregationBuilder aggs = AggregationBuilders.terms("uniq_category")
//                    .field("category")
//                    .size(100);
//
//            SearchResponse response = client.client().prepareSearch("item")
//                    .setTypes("_doc")
//                    .addAggregation(aggs)
//                    .setSize(0)
//                    .get();
//            Terms termBucket = response.getAggregations().get("uniq_category");
//
//            for(Terms.Bucket bucket : termBucket.getBuckets()) {
//                Map<String, Object> results = new HashMap<>();
//                results.put("key", bucket.getKeyAsString());
//                results.put("doc_count", bucket.getDocCount());
//                mapList.add(results);
//            }
//
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//
//        return mapList;
//    }

    @Transactional
    public List<Category> readCategoryList() {
        return categoryRepository.findAll();
    }
}

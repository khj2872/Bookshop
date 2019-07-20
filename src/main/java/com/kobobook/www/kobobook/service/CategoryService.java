package com.kobobook.www.kobobook.service;

import com.kobobook.www.kobobook.domain.Category;
import com.kobobook.www.kobobook.dto.CategoryDTO;
import com.kobobook.www.kobobook.repository.CategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class CategoryService {

    private CategoryRepository categoryRepository;

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
    public List<CategoryDTO> readCategoryList() {
        return categoryRepository.findCategoriesAll();
    }
}

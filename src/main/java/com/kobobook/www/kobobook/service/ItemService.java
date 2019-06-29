package com.kobobook.www.kobobook.service;

import com.kobobook.www.kobobook.domain.Category;
import com.kobobook.www.kobobook.domain.Item;
import com.kobobook.www.kobobook.domain.UploadFile;
import com.kobobook.www.kobobook.elasticsearch.AutocompleteList;
import com.kobobook.www.kobobook.elasticsearch.EsItem;
import com.kobobook.www.kobobook.repository.CategoryRepository;
import com.kobobook.www.kobobook.repository.ItemRepository;
import com.kobobook.www.kobobook.repository.UploadFileRepository;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.matchQuery;
import static org.elasticsearch.index.query.QueryBuilders.prefixQuery;

@Service
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private CategoryRepository categoryRepository;

//    @Autowired
//    private UploadFileRepository uploadFileRepository;

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Transactional
    public void create(Item item) {
        Optional<Category> category = categoryRepository.findById(item.getCategoryId());
        item.setCategory(category.orElse(null));
//        Optional<UploadFile> uploadFile = uploadFileRepository.findById(item.getUploadedId());
//        item.setUploadFile(uploadFile.orElse(null));
        item.setRegDate(new Date());
        itemRepository.save(item);
    }

    /*
    * 자동완성 아이템 목록
    * */
    @Transactional
    public List<AutocompleteList> readItemsByAutocomplete(String userQuery) {
        QueryBuilder queryBuilder = QueryBuilders
                .boolQuery()
                .should(prefixQuery("name", userQuery))
                .should(matchQuery("namengram", userQuery))
                .should(matchQuery("namengramedge", userQuery))
                .should(matchQuery("namengramedgeback", userQuery));

        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withPageable(PageRequest.of(0, 10))
                .withQuery(queryBuilder)
                .build();

        return elasticsearchTemplate.queryForList(searchQuery, AutocompleteList.class);
    }

    /*
    * 검색
    * */
    @Transactional
    public List<EsItem> searchItems(String userQuery) {
        QueryBuilder queryBuilder = QueryBuilders
                .boolQuery()
                .should(prefixQuery("name", userQuery))
                .should(matchQuery("namengram", userQuery))
                .should(matchQuery("namengramedge", userQuery))
                .should(matchQuery("namengramedgeback", userQuery));

        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withPageable(PageRequest.of(0, 20))
                .withQuery(queryBuilder)
                .build();

        return elasticsearchTemplate.queryForList(searchQuery, EsItem.class);
    }



    @Transactional
    public Page<Item> readItemsByCategory(Integer categoryId) {
        Category category = categoryRepository.findById(categoryId).orElse(null);
        return itemRepository.findByCategory(category, PageRequest.of(0, 20));
    }

    /*
    * 아이템 상세정보
    * */
//    @Transactional
//    public Item readItemDetail(Item item) {
//
//        return item;
//    }
}

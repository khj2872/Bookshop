package com.kobobook.www.kobobook.service;

import com.kobobook.www.kobobook.domain.Category;
import com.kobobook.www.kobobook.domain.Item;
import com.kobobook.www.kobobook.dto.ItemDTO;
import com.kobobook.www.kobobook.elasticsearch.Autocomplete;
import com.kobobook.www.kobobook.elasticsearch.EsItem;
import com.kobobook.www.kobobook.repository.CategoryRepository;
import com.kobobook.www.kobobook.repository.ItemRepository;
import lombok.AllArgsConstructor;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.elasticsearch.index.query.QueryBuilders.prefixQuery;
import static org.elasticsearch.index.query.QueryBuilders.termQuery;

@Service
@AllArgsConstructor
public class ItemService {

    private ItemRepository itemRepository;

    private CategoryRepository categoryRepository;

    private ElasticsearchTemplate elasticsearchTemplate;

    private ModelMapper modelMapper;

    @Transactional
    public void create(Item item) {
        Optional<Category> category = categoryRepository.findById(item.getCategory().getId());
        item.setCategory(category.orElse(null));
        item.setRegDate(new Date());
        itemRepository.save(item);
    }

    /*
     * 자동완성 아이템 목록
     * */
    @Transactional
    public List<Autocomplete> readItemsByAutocomplete(String userQuery) {
        QueryBuilder queryBuilder = QueryBuilders
                .boolQuery()
                .should(prefixQuery("name", userQuery))
                .should(termQuery("name_ngram", userQuery))
                .should(termQuery("name_ngram_edge", userQuery))
                .should(termQuery("name_ngram_edge_back", userQuery))
                .minimumShouldMatch(1);

        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withPageable(PageRequest.of(0, 10))
                .withQuery(queryBuilder)
                .build();
        return elasticsearchTemplate.queryForList(searchQuery, Autocomplete.class);
    }

    /*
    * 검색
    * */
    @Transactional
    public Page<EsItem> searchItems(String userQuery, String searchOption, Pageable pageable) {
        QueryBuilder queryBuilder = QueryBuilders
                .boolQuery()
                .should(prefixQuery("name", userQuery))
                .should(termQuery("name_ngram", userQuery))
                .should(termQuery("name_ngram_edge", userQuery))
                .should(termQuery("name_ngram_edge_back", userQuery))
                .minimumShouldMatch(1);

        SearchQuery searchQuery = setSearchQuery(queryBuilder, searchOption, pageable);

        return elasticsearchTemplate.queryForPage(searchQuery, EsItem.class);
    }

    /*
    * Set searchQuery
    * */
    private NativeSearchQuery setSearchQuery(QueryBuilder queryBuilder, String searchOption, Pageable pageable) {
        NativeSearchQueryBuilder searchQueryBuilder = new NativeSearchQueryBuilder()
                .withPageable(pageable)
                .withQuery(queryBuilder);

        return setSearchOption(searchOption, searchQueryBuilder).build();

    }

    /*
    * Set searchOption
    * */
    private NativeSearchQueryBuilder setSearchOption(String searchOption, NativeSearchQueryBuilder searchQueryBuilder) {
        switch (searchOption) {
            case "accuracy" :
                break;

            case "highest" :
                searchQueryBuilder
                        .withSort(SortBuilders.fieldSort("price").order(SortOrder.DESC));
                break;

            case "lowest" :
                searchQueryBuilder
                        .withSort(SortBuilders.fieldSort("price").order(SortOrder.ASC));
                break;

            case "avgRating" :
                searchQueryBuilder
                        .withSort(SortBuilders.fieldSort("avg_rating").order(SortOrder.DESC));
                break;

            case "review" :
                searchQueryBuilder
                        .withSort(SortBuilders.fieldSort("review").order(SortOrder.DESC));
                break;
        }
        return searchQueryBuilder;
    }


    @Transactional
    public Page<ItemDTO.ItemSimple> readItemsByCategory(Integer categoryId) {
        return itemRepository.findByCategory(categoryId, PageRequest.of(0, 20))
                .map(i -> modelMapper.map(i, ItemDTO.ItemSimple.class));
    }

    @Transactional
    public ItemDTO.ItemDetail findItem(Integer itemId) {
        Item item = itemRepository.findItemWithCategoryAndReview(itemId);
        return modelMapper.map(item, ItemDTO.ItemDetail.class);
    }
}

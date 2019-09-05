package com.kobobook.www.kobobook.api;

import com.kobobook.www.kobobook.dto.ItemDTO;
import com.kobobook.www.kobobook.dto.ReviewDTO;
import com.kobobook.www.kobobook.elasticsearch.Autocomplete;
import com.kobobook.www.kobobook.elasticsearch.EsItem;
import com.kobobook.www.kobobook.service.ItemService;
import com.kobobook.www.kobobook.service.ReviewService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/items")
@AllArgsConstructor
public class ItemApiController {

    private ItemService itemService;

    private ReviewService reviewService;

    /*
    * 카테고리별 아이템
    * */
    @GetMapping("/category/{id}")
    public ResponseEntity<Map<String, Object>> readItemsByCategory(@PathVariable("id") Integer categoryId) {
        Page<ItemDTO.ItemSimple> itemPage = itemService.readItemsByCategory(categoryId);

        Map<String, Object> result = new HashMap<>();
//        result.put("pageno", itemPage.getNumber());
//        result.put("pagesize", pagesize);
//        result.put("totalcount", itemPage.getTotalElements());
        result.put("items", itemPage.getContent());

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /*
    * 자동완성
    * */
    @GetMapping("/autocomplete")
    public ResponseEntity<List<Autocomplete>> readItemsByAutocomplete(@RequestParam("userQuery") String userQuery) {
        return new ResponseEntity<>(itemService.readItemsByAutocomplete(userQuery), HttpStatus.OK);
    }

    /*
     * 검색
     * */
    @GetMapping("/{userQuery}/search/{searchOption}")
    public ResponseEntity<Map<String, Object>> searchItems(@PathVariable String userQuery,
                                                           @PathVariable String searchOption,
                                                           Pageable pageable) {
        Page<EsItem> itemPage = itemService.searchItems(userQuery, searchOption, pageable);
        Map<String, Object> returnMap = new HashMap<>();

        returnMap.put("items", itemPage.getContent());
        returnMap.put("totalItems", itemPage.getTotalElements());

        return new ResponseEntity<>(returnMap, HttpStatus.OK);
    }

    /*
    * 아이템 상세정보
    * */
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> readItemDetail(@PathVariable("id") Integer itemId) {

        ItemDTO.ItemWithCategory item = itemService.readItemDetail(itemId);
        List<ReviewDTO> reviews = reviewService.readReviewList(item.getId());

        Map<String, Object> returnMap = new HashMap<>();
        returnMap.put("item", item);
        returnMap.put("reviews" ,reviews);

        return new ResponseEntity<>(returnMap, HttpStatus.OK);
    }
}

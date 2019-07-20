package com.kobobook.www.kobobook.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kobobook.www.kobobook.domain.Item;
import com.kobobook.www.kobobook.dto.ItemDTO;
import com.kobobook.www.kobobook.elasticsearch.Autocomplete;
import com.kobobook.www.kobobook.elasticsearch.EsItem;
import com.kobobook.www.kobobook.exception.UnauthorizedException;
import com.kobobook.www.kobobook.repository.ItemRepository;
import com.kobobook.www.kobobook.service.ItemService;
import com.kobobook.www.kobobook.service.JwtService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Slf4j
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/items")
@AllArgsConstructor
public class ItemApiController {

    private ItemService itemService;

    private ItemRepository itemRepository;

    private JwtService jwtService;

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
        System.out.println(userQuery);
        return new ResponseEntity<>(itemService.readItemsByAutocomplete(userQuery), HttpStatus.OK);
    }

    /*
     * 검색
     * */
    @GetMapping("/{userQuery}/search/{searchOption}")
    public ResponseEntity<Map<String, Object>> searchItems(@PathVariable String userQuery,
                                                           @PathVariable String searchOption,
                                                           Pageable pageable) {
//        log.info("userQuery : " + userQuery);
//        log.info("searchOption : " + searchOption);
//        log.info("page : " + pageable.getPageNumber() + " " + pageable.getOffset());
//        log.info("size : " + pageable.getPageSize());
        Page<EsItem> itemPage = itemService.searchItems(userQuery, searchOption, pageable);
        Map<String, Object> returnMap = new HashMap<>();

        returnMap.put("items", itemPage.getContent());
        returnMap.put("totalItems", itemPage.getTotalElements());

        return new ResponseEntity<>(returnMap, HttpStatus.OK);
    }

    /*
    * 아이템 상세정보
    * */
    @GetMapping("/{itemId}")
    public ResponseEntity<Object> readItemDetail(@PathVariable("itemId") Integer itemId, HttpServletRequest request) {
        try {
            final String token = request.getHeader("Authorization");
            if(token != null && jwtService.isUsable(token)) {
                Integer memberId = (Integer) jwtService.get("member").get("id");

                // memberId, itemId 로그 기록
                Map<String, Object> resultMap = new TreeMap<>();
                resultMap.put("memberId", memberId);
                resultMap.put("itemId", itemId);
                log.info(new ObjectMapper().writeValueAsString(resultMap));

            }
        } catch (UnauthorizedException e) {
            e.printStackTrace();
        } finally {
            ItemDTO.ItemDetail itemDTO = itemService.findItem(itemId);
            return new ResponseEntity<>(itemDTO, HttpStatus.OK);
        }
    }
}

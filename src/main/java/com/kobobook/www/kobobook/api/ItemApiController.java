package com.kobobook.www.kobobook.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kobobook.www.kobobook.domain.Item;
import com.kobobook.www.kobobook.elasticsearch.AutocompleteList;
import com.kobobook.www.kobobook.elasticsearch.EsItem;
import com.kobobook.www.kobobook.exception.UnauthorizedException;
import com.kobobook.www.kobobook.repository.EsItemRepository;
import com.kobobook.www.kobobook.repository.ItemRepository;
import com.kobobook.www.kobobook.service.DataModelService;
import com.kobobook.www.kobobook.service.ItemService;
import com.kobobook.www.kobobook.service.JwtService;
import lombok.extern.slf4j.Slf4j;
import net.logstash.logback.marker.Markers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
public class ItemApiController {

    @Autowired
    private ItemService itemService;

    @Autowired
    private JwtService jwtService;

    /*
    * 카테고리별 아이템
    * */
    @GetMapping("/category/{id}")
    public ResponseEntity<Map<String, Object>> readItemsByCategory(@PathVariable("id") Integer categoryId) {

//        Page<EsItem> itemPage = esItemRepository.findById(categoryId, PageRequest.of(0, 20));

        Page<Item> itemPage = itemService.readItemsByCategory(categoryId);


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
    public ResponseEntity<List<AutocompleteList>> readItemsByAutocomplete(@RequestParam("userQuery") String userQuery) {
        System.out.println(userQuery);
        return new ResponseEntity<>(itemService.readItemsByAutocomplete(userQuery), HttpStatus.OK);
    }

    /*
     * 검색
     * */
    @GetMapping("/search")
    public ResponseEntity<List<EsItem>> searchItems(@RequestParam("userQuery") String userQuery) {
        System.out.println(userQuery);
        return new ResponseEntity<>(itemService.searchItems(userQuery), HttpStatus.OK);
    }

    /*
    * 아이템 상세정보
    * */
    @GetMapping("/{itemId}")
    public ResponseEntity<Object> readItemDetail(@PathVariable("itemId") Item item, HttpServletRequest request) {
        try {
            final String token = request.getHeader("Authorization");
            if(token != null && jwtService.isUsable(token)) {
                Integer memberId = (Integer) jwtService.get("member").get("id");
//                dataModelService.createDataModel(memberId, item);

                Map<String, Object> resultMap = new TreeMap<>();
                resultMap.put("memberId", memberId);
                resultMap.put("itemId", item.getId());
                String msg = new ObjectMapper().writeValueAsString(resultMap);
                log.info(msg);

            }
        } catch (UnauthorizedException e) {
            e.printStackTrace();
        } finally {
            return new ResponseEntity<>(item, HttpStatus.OK);
        }
    }
}

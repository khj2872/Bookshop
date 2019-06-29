package com.kobobook.www.kobobook.api;

import com.kobobook.www.kobobook.domain.Address;
import com.kobobook.www.kobobook.domain.Cart;
import com.kobobook.www.kobobook.domain.Order;
import com.kobobook.www.kobobook.repository.MemberRepository;
import com.kobobook.www.kobobook.service.JwtService;
import com.kobobook.www.kobobook.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.repository.support.AbstractElasticsearchRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/orders")
public class OrderApiController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private MemberRepository memberRepository;

    /*
    * 결제 항목 불러오기
    * */
    @GetMapping("/list")
    public ResponseEntity<Map<String, Object>> readOrderList(@RequestParam("id") Integer[] cartIdList) {
        System.out.println(Arrays.toString(cartIdList));
        Map<String, Object> orderInfo = new HashMap<>();
        orderInfo.put("member", memberRepository.findById((Integer) jwtService.get("member").get("id")).orElse(null));
        orderInfo.put("orderList", orderService.readOrderList(cartIdList));
        return new ResponseEntity<>(orderInfo, HttpStatus.OK);
    }

    /*
    * 단일항목 결제
    * */
//    @PostMapping("")
//    public ResponseEntity<Integer> createOrder(Address address,
//                                            @RequestParam("itemId") Integer itemId,
//                                            @RequestParam("count") int count) {
//        return new ResponseEntity<>(orderService.order((Integer)jwtService.get("member").get("id"), itemId, count), HttpStatus.OK);
//    }

    /*
    * 결제 처리
    * */
    @PostMapping("")
    public ResponseEntity<Integer> createCartOrder(@RequestBody Address address, Integer[] cartId) {
        return new ResponseEntity<>(orderService.cartOrder((Integer) jwtService.get("member").get("id"), cartId), HttpStatus.OK);
    }

    /*
    * 주문 취소
    * */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable("id") Integer cartId) {
        orderService.cancelOrder(cartId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /*
    *
    * */

}

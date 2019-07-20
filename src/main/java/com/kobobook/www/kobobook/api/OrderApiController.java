package com.kobobook.www.kobobook.api;

import com.kobobook.www.kobobook.domain.Member;
import com.kobobook.www.kobobook.domain.OrderSearch;
import com.kobobook.www.kobobook.domain.OrderStatus;
import com.kobobook.www.kobobook.dto.MemberDTO;
import com.kobobook.www.kobobook.dto.OrderInfo;
import com.kobobook.www.kobobook.dto.OrderDTO;
import com.kobobook.www.kobobook.service.JwtService;
import com.kobobook.www.kobobook.service.MemberService;
import com.kobobook.www.kobobook.service.OrderService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.jaxb.SpringDataJaxb;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/orders")
@Slf4j
@AllArgsConstructor
public class OrderApiController {

    private OrderService orderService;

    private MemberService memberService;

    private JwtService jwtService;

    private ModelMapper modelMapper;

    /*
    * 결제 처리
    * */
    @PostMapping("")
    public ResponseEntity<Integer> createCartOrder(@RequestBody OrderInfo orderInfo) {
        Integer orderId = orderService.cartOrder((Integer) jwtService.get("member").get("id"), orderInfo);
        return new ResponseEntity<>(orderId, HttpStatus.OK);
    }

    /*
    * 주문 취소
    * */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable("id") Integer cartId) {
        try {
            orderService.cancelOrder(cartId);
        } catch (RuntimeException re) {
            re.printStackTrace();
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    /*
    * 주문/배송조회
    * */
    @GetMapping("")
    public ResponseEntity<Map<String, Object>> readCompleteOrderList(@RequestParam("itemName") String itemName,
                                                                     @RequestParam("orderStatus") OrderStatus orderStatus,
                                                                     @RequestParam("page") int page,
                                                                     @RequestParam("size") int size) {
        Integer memberId = (Integer) jwtService.get("member").get("id");
        List<OrderDTO> orderDTOList = orderService.readCompleteOrderList(memberId, new OrderSearch(itemName, orderStatus));

        Map<String, Object> returnMap = new HashMap<>();
        returnMap.put("member", memberService.readMember(memberId));
        returnMap.put("orders", orderDTOList);
        return new ResponseEntity<>(returnMap, HttpStatus.OK);
    }

    /*
    * 주문 상세보기
    * */
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> readOrderDetail(@PathVariable("id") Integer orderId) {

        MemberDTO memberDTO = memberService.readMember((Integer) jwtService.get("member").get("id"));

        OrderDTO orderDTO = orderService.readOrderDetail(orderId);

        Map<String, Object> returnMap = new HashMap<>();
        returnMap.put("member", memberDTO);
        returnMap.put("order", orderDTO);

        return new ResponseEntity<>(returnMap, HttpStatus.OK);
    }

}

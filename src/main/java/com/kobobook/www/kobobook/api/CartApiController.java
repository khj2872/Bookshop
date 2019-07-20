package com.kobobook.www.kobobook.api;

import com.kobobook.www.kobobook.domain.Cart;
import com.kobobook.www.kobobook.dto.CartDTO;
import com.kobobook.www.kobobook.repository.MemberRepository;
import com.kobobook.www.kobobook.service.CartService;
import com.kobobook.www.kobobook.service.JwtService;
import com.kobobook.www.kobobook.service.MemberService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/carts")
@AllArgsConstructor
public class CartApiController {

    private CartService cartService;

    private MemberService memberService;

    private JwtService jwtService;

    /*
    * 장바구니 항목 추가
    * */
    @PostMapping("")
    public ResponseEntity<Integer> createCart(@RequestBody Map<String, Object> param) {
        Integer memberId = (Integer)jwtService.get("member").get("id");
        Integer itemId = (Integer) param.get("itemId");
        Integer count = (Integer) param.get("count");
        return new ResponseEntity<>(cartService.createCart(memberId, itemId, count), HttpStatus.OK);
    }

    /*
    * 장바구니 항목 삭제
    * */
    @DeleteMapping("/{id}")
    public ResponseEntity<List<CartDTO>> deleteCart(@PathVariable("id") Cart cart) {
        return new ResponseEntity<>(cartService.deleteCart(cart), HttpStatus.OK);
    }

    /*
    * 장바구니 항목 수정
    * */
    @PutMapping("/{id}")
    public ResponseEntity<List<CartDTO>> updateCart(@PathVariable("id") Cart cart, @RequestBody Map<String, Object> param) {
        return new ResponseEntity<>(cartService.updateCart(cart, (Integer) param.get("count")), HttpStatus.OK);
    }


    /*
    * 장바구니 리스트 출력
    * */
    @GetMapping("")
    public ResponseEntity<List<CartDTO>> readCartList() {
        List<CartDTO> cartDTOList = cartService.readCartList((Integer) jwtService.get("member").get("id"));
        System.out.println("444444444");
        return new ResponseEntity<>(cartDTOList, HttpStatus.OK);
    }

    /*
     * 결제 항목 불러오기
     * */
    @GetMapping("/list")
    public ResponseEntity<Map<String, Object>> readOrderList(@RequestParam("id") Integer[] cartIdList) {
        Map<String, Object> orderInfo = new HashMap<>();
        orderInfo.put("member", memberService.readMember((Integer) jwtService.get("member").get("id")));
        orderInfo.put("orderList", cartService.readOrderList(cartIdList));
        return new ResponseEntity<>(orderInfo, HttpStatus.OK);
    }
}

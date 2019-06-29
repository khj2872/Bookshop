package com.kobobook.www.kobobook.api;

import com.kobobook.www.kobobook.domain.Cart;
import com.kobobook.www.kobobook.service.CartService;
import com.kobobook.www.kobobook.service.JwtService;
import com.kobobook.www.kobobook.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/carts")
public class CartApiController {

    @Autowired
    private CartService cartService;

    @Autowired
    private MemberService memberService;

    @Autowired
    private JwtService jwtService;

    /*
    * 장바구니 항목 추가
    * */
    @PostMapping("")
    public ResponseEntity<Integer> createCart(@RequestBody Map<String, Object> param) {
        Integer memberId = (Integer)jwtService.get("member").get("id");
        Integer itemId = (Integer) param.get("itemId");
        Long count = (Long) param.get("count");
        return new ResponseEntity<>(cartService.createCart(memberId, itemId, count), HttpStatus.OK);
    }

    /*
    * 장바구니 항목 삭제
    * */
    @DeleteMapping("/{id}")
    public ResponseEntity<List<Cart>> deleteCart(@PathVariable("id") Cart cart) {
        return new ResponseEntity<>(cartService.deleteCart(cart), HttpStatus.OK);
    }

    /*
    * 장바구니 항목 수정
    * */
    @PutMapping("/{id}")
    public ResponseEntity<List<Cart>> updateCart(@PathVariable("id") Cart cart, @RequestBody Map<String, Object> param) {
        return new ResponseEntity<>(cartService.updateCart(cart, (Integer) param.get("count")), HttpStatus.OK);
    }


    /*
    * 장바구니 리스트 출력
    * */
    @GetMapping("")
    public ResponseEntity<List<Cart>> readCartList() {
        return new ResponseEntity<>(cartService.readCartList((Integer) jwtService.get("member").get("id")), HttpStatus.OK);
    }
}

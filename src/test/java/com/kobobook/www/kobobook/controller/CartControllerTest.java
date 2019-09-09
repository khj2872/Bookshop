//package com.kobobook.www.kobobook.controller;
//
//import com.kobobook.www.kobobook.api.CartApiController;
//import com.kobobook.www.kobobook.domain.Item;
//import com.kobobook.www.kobobook.domain.Member;
//import com.kobobook.www.kobobook.domain.Role;
//import com.kobobook.www.kobobook.repository.ItemRepository;
//import com.kobobook.www.kobobook.repository.MemberRepository;
//import com.kobobook.www.kobobook.service.CartService;
//import com.kobobook.www.kobobook.service.JwtService;
//import com.kobobook.www.kobobook.service.MemberService;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.Mock;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.ResultActions;
//import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//import org.springframework.transaction.annotation.Transactional;
//
//import static org.mockito.BDDMockito.given;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//@Transactional
//public class CartControllerTest {
//
//    private MockMvc mockMvc;
//
//    @MockBean
//    private CartApiController cartApiController;
//
//    @MockBean
//    private CartService cartService;
//
//    @MockBean
//    private MemberService memberService;
////
//    @Autowired
//    private ItemRepository itemRepository;
//
//    @Autowired
//    private MemberRepository memberRepository;
//
//    @MockBean
//    private JwtService jwtService;
//
//    @Before
//    public void setup() throws Exception {
//        mockMvc = MockMvcBuilders.standaloneSetup(cartApiController).build();
//    }
//
//    @Test
//    public void testCreateCart() throws Exception {
//        //given
////        Item item = Item.builder()
////                .name("테스트")
////                .build();
////        Item savedItem = itemRepository.save(item);
////
//        String json = "{\"itemId\":1, \"count\":2}";
////
////        given(cartApiController.createCart(json))
////
////        //when
////        ResultActions actions = mockMvc.perform(post("/api/carts")
////                .content("{\"itemId\":\"1\", \"count\":\"2\"}")
////                .contentType(MediaType.APPLICATION_JSON)
////        );
////
////        //then
////        actions
////                .andExpect(status().isOk())
////                .andExpect()
//
//        Member member = Member.builder()
//                .username("kang")
//                .userEmail("test@email.com")
//                .build();
//        Member savedMember = memberRepository.save(member);
//
//        String token = jwtService.createMember(savedMember.getId(), Role.ROLE_USER.toString());
//
//        mockMvc.perform(post("/api/carts")
//                .header("Authorization", token)
//                .content(json)
//                .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk());
////                .andExpect(content().string)
//    }
//
//}

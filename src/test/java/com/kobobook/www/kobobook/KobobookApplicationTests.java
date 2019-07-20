package com.kobobook.www.kobobook;

import com.kobobook.www.kobobook.api.OrderApiController;
import com.kobobook.www.kobobook.domain.OrderSearch;
import com.kobobook.www.kobobook.repository.OrderRepository;
import com.kobobook.www.kobobook.repository.custom.OrderRepositoryImpl;
import lombok.AllArgsConstructor;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.MockMvcClientHttpRequestFactory;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class KobobookApplicationTests {

    @Autowired
    private RestTemplate restTemplate;

    @Test
    public void restTemplateTest() {
//        ResponseEntity<Map<String, Object>> result = restTemplate.exchange(
//                "http://localhost:8090/test/{id}", String.class, "아야아어");
//
//        System.out.println(result);

        String uriTemplate = "http://localhost:8090/test/{id}";
        URI uri = UriComponentsBuilder.fromUriString(uriTemplate).build(42);
        RequestEntity<Void> requestEntity = RequestEntity.get(uri).build();
        ResponseEntity<Map> response = restTemplate.exchange(requestEntity, Map.class);
        HttpStatus resultCode = response.getStatusCode();

        System.out.println(resultCode);

        Map<String, Object> body = response.getBody();

        System.out.println(body);

    }


//    @Test
//    public void orderTest() {
//        // given
//        Member member = new Member();
//        member.setUsername("배송자");
//        member.setRegDate(new Date());
//        member.setRole("USER");
//        member.setUserEmail("test@mail.com");
//
//        Item item1 = new Item();
//        item1.setName("탑코더 알고리즘");
//        item1.setPrice(5000);
//        item1.setStock(14);
//
//        Item item2 = new Item();
//        item2.setName("클린 코드");
//        item2.setPrice(7000);
//        item2.setStock(100);
//
//        OrderItem[] orderItems = new OrderItem[2];
//
//        orderItems[0] = OrderItem.createOrderItem(item1, item1.getPrice(), 2);
//        orderItems[1] = OrderItem.createOrderItem(item2, item2.getPrice(), 3);
//
//        Address address = new Address();
//        address.setAddress("전북 익산시");
//        address.setReceiver("받는사람");
//        address.setTelPhone("01012341234");
//        address.setZipcode("01234");
//
//        Delivery delivery = new Delivery();
//        delivery.setStatus(DeliveryStatus.READY);
//        delivery.setAddress(address);
//
//        Order order1 = Order.createOrder(member, delivery, 50, 20, orderItems);
//
//        Order newOrder = orderRepository.save(order1);
//
//        Integer memberId = newOrder.getMember().getId();
//        OrderSearch orderSearch = new OrderSearch("클린", OrderStatus.ORDER);
//        Pageable pageable = PageRequest.of(0, 10);
//
//        List<Order> orderList = orderRepositoryImpl.searchList(memberId, orderSearch, pageable);
//        System.out.println(orderList.get(0).getOrderItems());
//        assertThat(orderList.size()).isEqualTo(1);
//
//
//    }

}

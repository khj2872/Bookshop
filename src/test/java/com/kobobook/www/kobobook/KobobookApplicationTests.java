package com.kobobook.www.kobobook;

import com.kobobook.www.kobobook.domain.Member;
import com.kobobook.www.kobobook.domain.Order;
import com.kobobook.www.kobobook.domain.OrderSearch;
import com.kobobook.www.kobobook.domain.OrderStatus;
import com.kobobook.www.kobobook.repository.*;
import com.kobobook.www.kobobook.repository.custom.OrderRepositoryImpl;
import lombok.AllArgsConstructor;
import org.aspectj.weaver.ast.Or;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class KobobookApplicationTests {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderRepositoryImpl orderRepositoryImpl;

    @Test
    public void contextLoads() {
        String memberName = "강혁진";
        OrderStatus orderStatus = OrderStatus.ORDER;
        Member member = new Member();
        member.setRole("USER");
        member.setUsername(memberName);
        member.setUserEmail("test@email.com");
        Order order = new Order();
        order.setMember(member);
        order.setOrderDate(new Date());
        order.setStatus(orderStatus);
        orderRepository.save(order);

        OrderSearch orderSearch = new OrderSearch(memberName, orderStatus);

        List<Order> orderList = orderRepositoryImpl.search(orderSearch);

        assertThat(orderList.size()).isEqualTo(1);
        assertThat(orderList.get(0).getMember().getUsername()).isEqualTo(memberName);

    }

    @Test
    public void orderTest() {
        Order order = new Order();
        order.setStatus(OrderStatus.ORDER);
        order.setOrderDate(new Date());
        orderRepository.save(order);

        List<Order> orderList = orderRepositoryImpl.findByStatus(OrderStatus.ORDER);

        System.out.println(orderList);
        assertThat(orderList.size()).isEqualTo(1);


    }

}

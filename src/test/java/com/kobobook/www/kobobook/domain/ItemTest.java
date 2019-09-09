package com.kobobook.www.kobobook.domain;

import com.kobobook.www.kobobook.exception.NotEnoughStockException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class ItemTest {

    @Test
    public void testAddStock() {
        //given
        Item item = Item.builder()
                .stock(3)
                .build();

        //when
        item.addStock(1);

        //then
        assertThat(item.getStock()).isEqualTo(4);
    }

    @Test
    public void testRemoveStock() {
        //given
        Item item = Item.builder()
                .stock(1)
                .build();

        //when, then
        assertThatExceptionOfType(NotEnoughStockException.class).isThrownBy(() -> {
            item.removeStock(2);
        });
        
    }

}

package com.kobobook.www.kobobook;

import com.kobobook.www.kobobook.elasticsearch.EsItem;
import com.kobobook.www.kobobook.repository.EsItemRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.core.AutoConfigureCache;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ElasticsearchTests {

    @Autowired
    private EsItemRepository esItemRepository;

    @Test
    public void input() {
//        EsItem esItem1 = new EsItem();
//        esItem1.setId(1934);
//        esItem1.setName("제발...");
//        esItem1.setReg_date(new Date());
//
//        esItemRepository.save(esItem1);

        EsItem esItem =  esItemRepository.findById(521).orElse(null);

        System.out.println(esItem);

    }

}

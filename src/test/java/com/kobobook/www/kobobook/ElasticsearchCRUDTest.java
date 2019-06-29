package com.kobobook.www.kobobook;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.InetAddress;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ElasticsearchCRUDTest {

//    @Autowired
//    private ItemEsRepository itemEsRepository;

    private String INDEX_NAME = "item";

    private String TYPE_NAME = "_doc";

    @Test
    public void createMapping() throws Exception {
        Settings settings = Settings.builder()
                .put("client.transport.sniff", true)
                .put("cluster.name", "javacafe-cluster")
                .build();

        TransportClient client = new PreBuiltTransportClient(settings);
        client.addTransportAddress(new TransportAddress(InetAddress.getByName("127.0.0.1"), 9300));


        XContentBuilder indexBuilder = jsonBuilder()
                .startObject()
                    .startObject(TYPE_NAME)
                    .startObject("properties")
                        .startObject("itemId")
                            .field("type", "integer")
                        .endObject()
                        .startObject("isbn")
                            .field("type", "keyword")
                            .field("store", "true")
                            .field("index_options", "docs")
                        .endObject()
                        .startObject("detail")
                            .field("type", "text")
                            .field("store", "true")
                            .field("index_options", "docs")
                        .endObject()
                        .startObject("image")
                            .field("type", "keyword")
                        .endObject()
                        .startObject("name")
                            .field("type", "text")
                            .field("store", "true")
                            .field("index_options", "docs")
                        .endObject()
                        .startObject("price")
                            .field("type", "long")
                            .field("store", "true")
                            .field("index_options", "docs")
                        .endObject()
                        .startObject("publicationDate")
                            .field("type", "keyword")
                        .endObject()
                        .startObject("regDate")
                            .field("type", "date")
                            .field("store", "true")
                            .field("index_options", "docs")
                        .endObject()
                        .startObject("stock")
                            .field("type", "long")
                            .field("store", "true")
                            .field("index_options", "docs")
                        .endObject()
                        .startObject("writer")
                            .field("type", "text")
                            .field("store", "true")
                            .field("index_options", "docs")
                        .endObject()
                        .startObject("category")
                            .field("type", "keyword")
                            .field("store", "true")
                            .field("index_options", "docs")
                        .endObject()
                    .endObject()
                .endObject()
                .endObject();

        // 세팅정보
        XContentBuilder settingBuilder = jsonBuilder()
                .startObject()
                    .startObject("index")
                        .startObject("analysis")
                            .startObject("tokenizer")
                                .startObject("nori_user_dict_tokenizer")
                                    .field("type","nori_tokenizer")
                                    .field("decompound_mode","mixed")
                                    .field("user_dictionary","userdict_ko.txt")
                                .endObject()
                            .endObject()
                            .startObject("analyzer")
                                .startObject("nori_token_analyzer")
                                    .field("tokenizer","nori_user_dict_tokenizer")
                                    .array("filter","lowercase")
                                .endObject()
                            .endObject()
                        .endObject()
                    .endObject()
                .endObject();

        // 인덱스 생성
        client.admin().indices().prepareCreate(INDEX_NAME)
                .setSettings(settingBuilder)
                .addMapping(TYPE_NAME, indexBuilder)
                .get();


    }

}

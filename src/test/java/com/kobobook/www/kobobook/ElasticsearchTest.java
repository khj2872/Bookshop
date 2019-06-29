package com.kobobook.www.kobobook;

import com.kobobook.www.kobobook.api.ItemApiController;
import com.kobobook.www.kobobook.config.EsClient;
import com.kobobook.www.kobobook.repository.CategoryRepository;
import com.kobobook.www.kobobook.repository.ItemRepository;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ElasticsearchTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private EsClient client;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Autowired
    private ItemApiController itemApiController;

    private String INDEX_NAME = "item";
    private String TYPE_NAME = "_doc";
    private String AGGREGATION_NAME = "uniq_category";
    private String AGGREGATION_FIELD = "category";

    @Test
    public void test() throws Exception {

//        System.out.println(itemApiController.readItemsByCategory("소설", 1, 10));
    }

    @Test
    public void createMapping() throws Exception {
//        String QUERY = "소설";
//
//        QueryBuilder queryBuilder = QueryBuilders.termQuery("category", QUERY);
//        SearchResponse response = client.client().prepareSearch(INDEX_NAME)
//                .addSort(FieldSortBuilder.DOC_FIELD_NAME, SortOrder.ASC)
//                .setFrom(3)
//                .setQuery(queryBuilder)
//                .setSize(10)
//                .get();
//
//        for(SearchHit hit : response.getHits().getHits()) {
//            System.out.println(hit.getSourceAsMap());
//        }

        TermsAggregationBuilder aggs = AggregationBuilders.terms(AGGREGATION_NAME)
                .field(AGGREGATION_FIELD)
                .size(100);

        SearchResponse response = client.client().prepareSearch(INDEX_NAME)
                .setTypes(TYPE_NAME)
                .addAggregation(aggs)
                .setSize(0)
                .get();

        Terms termBucket = response.getAggregations().get(AGGREGATION_NAME);
        Map<String, Object> results = new HashMap<>();

        for(Terms.Bucket bucket : termBucket.getBuckets()) {
            results.put(bucket.getKeyAsString(), bucket.getDocCount());
        }

        System.out.println(results);

    }

//    @Test
//    public void inputData() throws Exception{
//        String data = readData();
//
//        JSONParser jsonParser = new JSONParser();
//        JSONObject jsonObject = new JSONObject();
//
//
//        List<Item> itemList = new ArrayList<>();
//        jsonObject = (JSONObject) jsonParser.parse(data);
//        JSONArray items = (JSONArray) jsonObject.get("items");
//
//
//        Category mainCategory;
//        Category subCategory;
//
//        for(int i=0; i<items.size(); i++) {
//            Item item = new Item();
//
//            JSONObject tmp = (JSONObject) items.get(i);
//            String title = (String) tmp.get("title");
//            String isbn = (String) tmp.get("isbn");
//            String author = (String) tmp.get("author");
//            long price = Long.valueOf((String) tmp.get("price"));
//            String detail = (String) tmp.get("description");
//            String publicationDate = (String) tmp.get("pubdate");
//            String image = (String) tmp.get("image");
//
//            System.out.println("------" + (i+1) + "번째 검색 결과-------");
//            System.out.println("제목 : " + title.replace("<b>", "").replace("</b>", ""));
//            System.out.println("isbn : " + isbn);
//            System.out.println("author : " + author);
//            System.out.println("price : " + price);
//            System.out.println("detail : " + detail.replace("<b>", "").replace("</b>", ""));
//            System.out.println("publicationDate : " + publicationDate);
//            System.out.println("image : " + image);
//
//
//
//            item.setName(title.replace("<b>", "").replace("</b>", ""));
//            item.setISBN(isbn);
//            item.setWriter(author);
//            item.setPrice(price);
//            item.setDetail(detail.replace("<b>", "").replace("</b>", ""));
//            item.setPublicationDate(publicationDate);
//            item.setImage(image);
//            item.setStock(100l);
//            item.setSavingRate(5);
//            item.setRegDate(new Date());
//            item.setCategory(categoryRepository.findById(1).get());
//
//            itemList.add(item);
//        }
//
//        itemRepository.saveAll(itemList);
//    }
//
//    private String readData() {
//        String clientId = "pDdF5npa0HKcbAGKno90";//애플리케이션 클라이언트 아이디값";
//        String clientSecret = "Yv7dxNIKed";//애플리케이션 클라이언트 시크릿값";
//        try {
//            String text = URLEncoder.encode("", "UTF-8");
//            int display = 20;
//            String apiURL = "https://openapi.naver.com/v1/search/book.json?query=" + text + "&display=" + display; // json 결과
//            //String apiURL = "https://openapi.naver.com/v1/search/blog.xml?query="+ text; // xml 결과
//            URL url = new URL(apiURL);
//            HttpURLConnection con = (HttpURLConnection) url.openConnection();
//            con.setRequestMethod("GET");
//            con.setRequestProperty("X-Naver-Client-Id", clientId);
//            con.setRequestProperty("X-Naver-Client-Secret", clientSecret);
//            int responseCode = con.getResponseCode();
//            BufferedReader br;
//            if (responseCode == 200) { // 정상 호출
//                br = new BufferedReader(new InputStreamReader(con.getInputStream()));
//            } else {  // 에러 발생
//                br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
//            }
//            String inputLine;
//            StringBuffer response = new StringBuffer();
//            while ((inputLine = br.readLine()) != null) {
//                response.append(inputLine);
//            }
//            br.close();
//            System.out.println(response.toString());
//            return response.toString();
//
//
//        } catch (Exception e) {
//            System.out.println(e);
//            return null;
//        }
//    }

}

package com.kobobook.www.kobobook;

import com.kobobook.www.kobobook.domain.Item;
import com.kobobook.www.kobobook.repository.CategoryRepository;
import com.kobobook.www.kobobook.repository.ItemRepository;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class InputItemTests {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Test
    public void inputItem() throws Exception{
        String data = readData();

        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject;
        
        List<Item> itemList = new ArrayList<>();
        jsonObject = (JSONObject) jsonParser.parse(data);
        JSONArray items = (JSONArray) jsonObject.get("items");

        for(int i=0; i<items.size(); i++) {
            JSONObject tmp = (JSONObject) items.get(i);
            String title = (String) tmp.get("title");
            String isbn = (String) tmp.get("isbn");
            String author = (String) tmp.get("author");
            long price = Long.valueOf((String) tmp.get("price"));
            String detail = (String) tmp.get("description");
            String publicationDate = (String) tmp.get("pubdate");
            String image = (String) tmp.get("image");

            System.out.println("------" + (i+1) + "번째 검색 결과-------");
            System.out.println("제목 : " + title.replace("<b>", "").replace("</b>", ""));
            System.out.println("isbn : " + isbn);
            System.out.println("author : " + author);
            System.out.println("price : " + price);
            System.out.println("detail : " + detail.replace("<b>", "").replace("</b>", ""));
            System.out.println("publicationDate : " + publicationDate);
            System.out.println("image : " + image);

            Item item = Item.builder()
                    .name(title.replace("<b>", "").replace("</b>", ""))
                    .ISBN(isbn)
                    .writer(author)
                    .price(price)
                    .detail(detail.replace("<b>", "").replace("</b>", ""))
                    .publicationDate(publicationDate)
                    .image(image)
                    .stock(100l)
                    .savingRate(5)
                    .regDate(LocalDateTime.now())
                    .category(categoryRepository.findById(1).get())
                    .build();

            itemList.add(item);
        }

        itemRepository.saveAll(itemList);
    }

    private String readData() {
        String clientId = "aXJAQ9m_D_59dFXwH6Op";//애플리케이션 클라이언트 아이디값";
        String clientSecret = "pYBBRnj1UQ";//애플리케이션 클라이언트 시크릿값";
        try {
            String text = URLEncoder.encode("물리", "UTF-8");
            int display = 100;
            String apiURL = "https://openapi.naver.com/v1/search/book.json?query=" + text + "&display=" + display; // json 결과
            URL url = new URL(apiURL);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("X-Naver-Client-Id", clientId);
            con.setRequestProperty("X-Naver-Client-Secret", clientSecret);
            int responseCode = con.getResponseCode();
            BufferedReader br;
            if (responseCode == 200) { // 정상 호출
                br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            } else {  // 에러 발생
                br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
            }
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = br.readLine()) != null) {
                response.append(inputLine);
            }
            br.close();
            System.out.println(response.toString());
            return response.toString();
            
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

}

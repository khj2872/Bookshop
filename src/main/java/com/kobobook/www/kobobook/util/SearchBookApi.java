package com.kobobook.www.kobobook.util;

import com.kobobook.www.kobobook.domain.Item;
import com.kobobook.www.kobobook.repository.ItemRepository;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class SearchBookApi {
    public static void main(String[] args) throws Exception {
        String sb = readData();
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = new JSONObject();

        Item item = new Item();

        jsonObject = (JSONObject) jsonParser.parse(sb.toString());
        JSONArray items = (JSONArray) jsonObject.get("items");


        for(int i=0; i<items.size(); i++) {
            JSONObject tmp = (JSONObject) items.get(i);
            String title = (String) tmp.get("title");
            String isbn = (String) tmp.get("isbn");
            String author = (String) tmp.get("author");
            long price = Long.valueOf((String) tmp.get("price"));
            long discount = Long.valueOf((String) tmp.get("discount"));
            String detail = (String) tmp.get("description");
            String publicationDate = (String) tmp.get("pubdate");
            String image = (String) tmp.get("image");

            System.out.println("------" + (i+1) + "번째 검색 결과-------");
            System.out.println("제목 : " + title.replace("<b>", "").replace("</b>", ""));
            System.out.println("isbn : " + isbn);
            System.out.println("author : " + author);
            System.out.println("price : " + price);
            System.out.println("discount : " + discount);
            System.out.println("detail : " + detail.replace("<b>", "").replace("</b>", ""));
            System.out.println("publicationDate : " + publicationDate);
            System.out.println("image : " + image);

            item.setName(title);
            item.setISBN(isbn);
            item.setWriter(author);
            item.setPrice(price);
            item.setDetail(detail);
            item.setPublicationDate(publicationDate);
            item.setImage(image);


        }



    }

    private static String readData() {
        String clientId = "pDdF5npa0HKcbAGKno90";//애플리케이션 클라이언트 아이디값";
        String clientSecret = "Yv7dxNIKed";//애플리케이션 클라이언트 시크릿값";
        try {
            String text = URLEncoder.encode("java", "UTF-8");
            int display = 2;
            String apiURL = "https://openapi.naver.com/v1/search/book.json?query=" + text + "&display=" + display; // json 결과
            //String apiURL = "https://openapi.naver.com/v1/search/blog.xml?query="+ text; // xml 결과
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

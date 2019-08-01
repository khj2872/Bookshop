package com.kobobook.www.kobobook.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.kobobook.www.kobobook.domain.Member;
import com.kobobook.www.kobobook.service.JwtService;
import com.kobobook.www.kobobook.service.MemberService;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

@Component
public class OAuthLoginUtils {

    private static ObjectMapper mapper;

    private static JwtService jwtService;

    private static MemberService memberService;

    public OAuthLoginUtils(ObjectMapper mapper, JwtService jwtService, MemberService memberService) {
        OAuthLoginUtils.mapper = mapper;
        OAuthLoginUtils.jwtService = jwtService;
        OAuthLoginUtils.memberService = memberService;
    }

    public static class Naver {

        private static final String clientId = "aXJAQ9m_D_59dFXwH6Op";

        private static final String clientSecret = "pYBBRnj1UQ";

        private static final String accessTokenUri = "https://nid.naver.com/oauth2.0/token?grant_type=authorization_code";

        private static final String userInfoUri = "https://openapi.naver.com/v1/nid/me";

        /*
         * naver login
         * */
        public static String naverLogin(String code, String state) throws Exception {

            String apiURL = accessTokenUri + "&client_id=" + clientId + "&client_secret=" + clientSecret + "&code=" + code + "&state=" + state;

            URL url = new URL(apiURL);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");

            int responseCode = con.getResponseCode();
            BufferedReader br;

            if (responseCode == 200) { // 정상 호출
                br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            } else {  // 에러 발생
//                br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
                throw new RuntimeException("getAccessToken fail!!");
            }
            String inputLine;
            StringBuffer accessTokenRes = new StringBuffer();
            while ((inputLine = br.readLine()) != null) {
                accessTokenRes.append(inputLine);
            }
            br.close();

            Map<String, Object> map = mapper.readValue(accessTokenRes.toString(), new TypeReference<Map<String, String>>() {
            });

            String userInfo = getNaverUserInfo((String) map.get("access_token"));

            return makeJwtToken(userInfo);
        }

        /*
         * get UserInfo
         * */
        private static String getNaverUserInfo(String accessToken) throws Exception {
            String header = "Bearer " + accessToken; // Bearer 다음에 공백 추가

            URL url = new URL(userInfoUri);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Authorization", header);

            int responseCode = con.getResponseCode();
            BufferedReader br;

            if (responseCode == 200) { // 정상 호출
                br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            } else {  // 에러 발생
//                br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
                throw new RuntimeException("getUserInfo fail!!");
            }
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = br.readLine()) != null) {
                response.append(inputLine);
            }
            br.close();

            JsonParser parser = new JsonParser();

            JsonElement userInfo = parser.parse(response.toString());
            userInfo.getAsJsonObject().get("response").getAsJsonObject().get("id").getAsInt();

            return response.toString();
        }

        /*
         * make jwtToken
         * */
        private static String makeJwtToken(String userInfoJson) throws Exception {
            JsonParser parser = new JsonParser();
            JsonElement userInfo = parser.parse(userInfoJson);
            JsonObject userInfoObj = userInfo.getAsJsonObject().get("response").getAsJsonObject();

            String oauthId = userInfoObj.get("id").getAsString();
            String username = userInfoObj.get("name").getAsString();
            String userEmail = userInfoObj.get("email").getAsString();

            Member member = new Member();
            member.setUsername(username);
            member.setUserEmail(userEmail);
            member.setOauthId(oauthId);
            Member newMember = memberService.oauthSignUp(member);

            return jwtService.createMember(newMember.getId(), newMember.getRole().toString());
        }
    }

    public static class Kakao {

        private static final String clientId = "f60abc63b27a004463c7b07c19bc7633";

        private static final String clientSecret = "k0sp4m9qXQCBEZz9f8ltWBAOHoBfM45t";

        private static final String redirectUri = "https://localhost:8442/api/login/kakao";

        private static final String accessTokenUri = "https://kauth.kakao.com/oauth/token";

        private static final String userInfoUri = "https://kapi.kakao.com/v2/user/me";

        /*
         * kakao login
         * */
        public static String kakaoLogin(String code) throws Exception {
            String apiURL = accessTokenUri + "?grant_type=authorization_code&client_id=" + clientId
                    + "&redirect_uri=" + redirectUri
                    + "&code=" + code;

            URL url = new URL(apiURL);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");


            int responseCode = con.getResponseCode();
            BufferedReader br;

            if (responseCode == 200) { // 정상 호출
                br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            } else {  // 에러 발생
//                br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
                throw new RuntimeException("getAccessToken fail!!");
            }
            String inputLine;
            StringBuffer accessTokenRes = new StringBuffer();
            while ((inputLine = br.readLine()) != null) {
                accessTokenRes.append(inputLine);
            }
            br.close();

            Map<String, Object> map = mapper.readValue(accessTokenRes.toString(), new TypeReference<Map<String, String>>(){});

            String userInfo = getKakaoUserInfo((String) map.get("access_token"));

            return makeJwtToken(userInfo);
        }

        /*
         * get UserInfo
         * */
        private static String getKakaoUserInfo(String accessToken) throws Exception {
            String header = "Bearer " + accessToken; // Bearer 다음에 공백 추가

            URL url = new URL(userInfoUri);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
            con.setRequestProperty("Authorization", header);

            int responseCode = con.getResponseCode();
            BufferedReader br;

            if (responseCode == 200) { // 정상 호출
                br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            } else {  // 에러 발생
//                br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
                throw new RuntimeException("getUserInfo fail!!");
            }
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = br.readLine()) != null) {
                response.append(inputLine);
            }
            br.close();

            return response.toString();
        }

        /*
         * make jwtToken
         * */
        private static String makeJwtToken(String userInfoJson) throws Exception {
            JsonParser parser = new JsonParser();
            JsonElement userInfo = parser.parse(userInfoJson);
            String id = userInfo.getAsJsonObject().get("id").getAsString();
            String username = userInfo.getAsJsonObject().get("properties").getAsJsonObject().get("nickname").getAsString();
//            JsonObject userInfoObj = userInfo.getAsJsonObject().get("response").getAsJsonObject();
//            Map<String, String> map = mapper.readValue(userInfoJson, new TypeReference<Map<String, String>>(){});

//            String id = map.get("id");
//            String name = map.get("nickname");

//            String oauthId = userInfoObj.get("id").getAsString();
//            String username = userInfoObj.get("name").getAsString();
//            String userEmail = userInfoObj.get("email").getAsString();

            Member member = new Member();
//            member.setUsername(name);
//            member.setUserEmail(userEmail);
            member.setOauthId(id);
            Member newMember = memberService.oauthSignUp(member);

            return jwtService.createMember(newMember.getId(), newMember.getRole().toString());
        }

    }

}
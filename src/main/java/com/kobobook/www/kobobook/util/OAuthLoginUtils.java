package com.kobobook.www.kobobook.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.kobobook.www.kobobook.domain.Member;
import com.kobobook.www.kobobook.service.JwtService;
import com.kobobook.www.kobobook.service.MemberService;
import org.springframework.beans.factory.annotation.Value;
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

        @Value("${naver.clientId}")
        private static String clientId;

        @Value("${naver.clientSecret}")
        private static String clientSecret;

        @Value("${naver.accessTokenUri}")
        private static String accessTokenUri;

        @Value("${naver.userInfoUri}")
        private static String userInfoUri;

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

}
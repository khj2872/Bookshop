package com.kobobook.www.kobobook.api;

import com.kobobook.www.kobobook.domain.Member;
import com.kobobook.www.kobobook.service.JwtService;
import com.kobobook.www.kobobook.service.MemberService;
import com.kobobook.www.kobobook.util.OAuthLoginUtils;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@Controller
@RequestMapping("/api")
@AllArgsConstructor
public class MemberController {

    private MemberService memberService;

    private JwtService jwtService;

    @GetMapping("/login/naver")
    public String naverLogin(@RequestParam(value = "code") String code,
                             @RequestParam(value = "state") String state) {
        String access_token = null;

        try {
            access_token = OAuthLoginUtils.Naver.naverLogin(code, state);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
//            return "redirect:http://localhost:3000/agreement?token=" + access_token; //dev
            return "redirect:http://kobobook-client.s3-website.ap-northeast-2.amazonaws.com/agreement?token=" + access_token; //prod
        }

    }

    /*
     * Google 회원가입 및 로그인
     * */
    @ResponseBody
    @PostMapping("/login/google")
    public ResponseEntity<String> createOauth(@RequestBody Member member) {
        Member loginMember = memberService.oauthSignUp(member);
        String token = jwtService.createMember(loginMember.getId(), loginMember.getRole().toString());

        return new ResponseEntity<>(token, HttpStatus.OK);
    }

}

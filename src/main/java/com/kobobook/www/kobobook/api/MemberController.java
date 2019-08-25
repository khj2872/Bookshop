package com.kobobook.www.kobobook.api;

import com.kobobook.www.kobobook.domain.Member;
import com.kobobook.www.kobobook.service.JwtService;
import com.kobobook.www.kobobook.service.MemberService;
import com.kobobook.www.kobobook.util.OAuthLoginUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api")
@Slf4j
public class MemberController {

    private MemberService memberService;

    private JwtService jwtService;

    private OAuthLoginUtils oAuthLoginUtils;

    public MemberController(MemberService memberService, JwtService jwtService, OAuthLoginUtils oAuthLoginUtils) {
        this.memberService = memberService;
        this.jwtService = jwtService;
        this.oAuthLoginUtils = oAuthLoginUtils;
    }

    @Value("${login.redirectUri")
    private String redirectUri;

    @GetMapping("/login/naver")
    public String naverLogin(@RequestParam(value = "code") String code,
                             @RequestParam(value = "state") String state) {
        String access_token = null;

        try {
            access_token = oAuthLoginUtils.naverLogin(code, state);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return "redirect:" + redirectUri + access_token;
        }

    }

    /*
     * Google 회원가입 및 로그인
     * */
    @CrossOrigin(origins = "*")
    @ResponseBody
    @PostMapping("/login/google")
    public ResponseEntity<String> createOauth(@RequestBody Member member) {
        Member loginMember = memberService.oauthSignUp(member);
        String token = jwtService.createMember(loginMember.getId(), loginMember.getRole().toString());

        return new ResponseEntity<>(token, HttpStatus.OK);
    }

}

package com.kobobook.www.kobobook.api;

import com.kobobook.www.kobobook.domain.Member;
import com.kobobook.www.kobobook.dto.MemberDTO;
import com.kobobook.www.kobobook.exception.AlreadyExistingMemberException;
import com.kobobook.www.kobobook.repository.MemberRepository;
import com.kobobook.www.kobobook.service.JwtService;
import com.kobobook.www.kobobook.service.MemberService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/members")
@AllArgsConstructor
public class MemberApiController {

    private MemberService memberService;

    private JwtService jwtService;

    /*
    * SNS 로그인
    * */
    @PostMapping("/oauth_login")
    public ResponseEntity<String> createOauth(@RequestBody Member member) {
        Member loginMember = memberService.save(member);
        String token = jwtService.createMember("member", loginMember, "user");
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", token);

        return new ResponseEntity<>(token, headers, HttpStatus.OK);
    }

    /*
    * 로그인 성공 후 username 호출
    * */
    @GetMapping("/username")
    public ResponseEntity<String> readMemberInfo() {
        String userName = memberService.findUserName((Integer)jwtService.get("member").get("id"));

        return new ResponseEntity<>(userName, HttpStatus.OK);
    }

    /*
    * 회원가입
    * */
    @PostMapping("/register")
    public ResponseEntity<Void> create(@RequestBody Member member) {
        try {
            memberService.create(member);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (AlreadyExistingMemberException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    /*
    * 일반 로그인
    * */
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Member member) {
//        System.out.println(member);
        Member loginMember = memberService.login(member.getUserEmail(), member.getPassword());
        if(loginMember == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        String token = jwtService.createMember("member", loginMember, "user");

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", token);
//        System.out.println("token : " + token);
        return new ResponseEntity<>(token, headers, HttpStatus.OK);
    }

    /*
    * 회원정보 출력
    * */
    @GetMapping("/")
    public ResponseEntity<MemberDTO> readMember() {
        return new ResponseEntity<>(memberService.readMember((Integer) jwtService.get("member").get("id")), HttpStatus.OK);
    }

}

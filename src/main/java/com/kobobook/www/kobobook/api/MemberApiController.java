package com.kobobook.www.kobobook.api;

import com.kobobook.www.kobobook.domain.Member;
import com.kobobook.www.kobobook.dto.MemberDTO;
import com.kobobook.www.kobobook.exception.AlreadyExistingMemberException;
import com.kobobook.www.kobobook.service.JwtService;
import com.kobobook.www.kobobook.service.MemberService;
import lombok.AllArgsConstructor;
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
    * 로그인 성공 후 username, role 호출
    * */
    @GetMapping("/username")
    public ResponseEntity<MemberDTO> readMemberInfo() {
        MemberDTO member = memberService.readMember((Integer) jwtService.getString("userId"));

        return new ResponseEntity<>(member, HttpStatus.OK);
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
        System.out.println(member);
        Member loginMember = memberService.login(member.getUserEmail(), member.getPassword());
        if(loginMember == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        String token = jwtService.createMember(loginMember.getId(), loginMember.getRole().toString());

        HttpHeaders headers = new HttpHeaders();
        headers.add(JwtService.jwtHeader, token);
        return new ResponseEntity<>(token, headers, HttpStatus.OK);
    }

    /*
    * 회원정보 출력
    * */
    @GetMapping("/")
    public ResponseEntity<MemberDTO> readMember() {
        return new ResponseEntity<>(memberService.readMember((Integer) jwtService.getString("userId")), HttpStatus.OK);
    }

}

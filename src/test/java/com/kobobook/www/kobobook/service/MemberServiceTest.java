package com.kobobook.www.kobobook.service;

import com.kobobook.www.kobobook.domain.Member;
import com.kobobook.www.kobobook.repository.MemberRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class MemberServiceTest {

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberRepository memberRepository;

    @Test
    public void testOauthSignUp() {
        //given
        Member member = Member.builder()
                .userEmail("test@email.com")
                .username("kang")
                .build();


        //when
        Member savedMember = memberService.oauthSignUp(member);

        //then
        assertThat(savedMember.getUsername()).isEqualTo("kang");
        assertThat(savedMember.getUserEmail()).isEqualTo("test@email.com");

    }

}

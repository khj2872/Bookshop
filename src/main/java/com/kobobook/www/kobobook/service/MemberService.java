package com.kobobook.www.kobobook.service;

import com.kobobook.www.kobobook.domain.Member;
import com.kobobook.www.kobobook.exception.AlreadyExistingMemberException;
import com.kobobook.www.kobobook.exception.IdPasswordNotMatchingException;
import com.kobobook.www.kobobook.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class MemberService {

    @Autowired
    private MemberRepository memberRepository;

    /*
    * OAuth 회원가입 및 로그인
    * */
    @Transactional
    public Member save(Member member) {
        member.setRole("USER");
        member.setRegDate(new Date());
        Member loginMember = memberRepository.findByOauthId(member.getOauthId());
        if(loginMember == null) {
            System.out.println("loginMember1 : " + loginMember);
            return memberRepository.save(member);
        }
        System.out.println("loginMember2 : " + loginMember);
        return loginMember;
    }

    /*
    * 로그인 성공 후 회원이름 반환
    * */
    @Transactional
    public String findUserName(int id) {
        return memberRepository.findUserNameById(id).getUsername();
    }

    /*
    * 로그인
    * */
    @Transactional
    public Member login(String userEmail, String password) throws IdPasswordNotMatchingException {
        Member member = memberRepository.findByUserEmail(userEmail);
        if(member == null) {
            return null;
        }
        if(!member.matchPassword(password)) {
            return null;
        }
        return member;
    }

    /*
    * 회원가입
    * */
    @Transactional
    public void create(Member member) throws AlreadyExistingMemberException {
        Member chkMember = memberRepository.findByUserEmail(member.getUserEmail());
        if(chkMember != null) {
            throw new AlreadyExistingMemberException("dup id " + member.getUserEmail());
        } else {
            member.setRole("USER");
            member.setRegDate(new Date());
            memberRepository.save(member);
        }
    }

}

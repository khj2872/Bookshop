package com.kobobook.www.kobobook.service;

import com.kobobook.www.kobobook.domain.Member;
import com.kobobook.www.kobobook.domain.Role;
import com.kobobook.www.kobobook.dto.MemberDTO;
import com.kobobook.www.kobobook.exception.AlreadyExistingMemberException;
import com.kobobook.www.kobobook.repository.MemberRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Date;

@Service
@AllArgsConstructor
public class MemberService {

    private MemberRepository memberRepository;

    private ModelMapper modelMapper;

    /*
    * OAuth 회원가입 및 로그인
    * */
    @Transactional
    public Member oauthSignUp(Member member) {
        member.setRole(Role.ROLE_USER);
        member.setRegDate(LocalDateTime.now());
        Member loginMember = memberRepository.findByOauthId(member.getOauthId());
        if(loginMember == null) {
            return memberRepository.save(member);
        }
        return loginMember;
    }

    /*
    * 로그인
    * */
    @Transactional
    public Member login(String userEmail, String password) {
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
            member.setPassword(member.getPassword());
            member.setRole(Role.ROLE_USER);
            member.setRegDate(LocalDateTime.now());
            memberRepository.save(member);
        }
    }

    /*
    * 회원정보
    * */
    @Transactional
    public MemberDTO readMember(Integer memberId) {
        Member member = memberRepository.findById(memberId).orElse(null);
        return modelMapper.map(member, MemberDTO.class);
    }

}

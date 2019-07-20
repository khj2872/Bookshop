package com.kobobook.www.kobobook.repository;

import com.kobobook.www.kobobook.domain.Member;
import com.kobobook.www.kobobook.dto.MemberDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Integer> {

    Member findUserNameById(int id);

    Member findByOauthId(String oauthId);

    Member findByUserEmail(String userEmail);
}

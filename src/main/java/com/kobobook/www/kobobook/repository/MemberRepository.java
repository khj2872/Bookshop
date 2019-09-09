package com.kobobook.www.kobobook.repository;

import com.kobobook.www.kobobook.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Integer> {

    Member findByOauthId(String oauthId);

    Member findByUserEmail(String userEmail);
}

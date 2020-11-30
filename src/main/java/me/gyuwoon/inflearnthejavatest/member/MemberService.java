package me.gyuwoon.inflearnthejavatest.member;

import java.util.Optional;

import me.gyuwoon.inflearnthejavatest.domain.Member;

public interface MemberService {
    void validate(Long memberId);
    
    Optional<Member> findById(Long memberId);
}

package me.gyuwoon.inflearnthejavatest.member;

import java.util.Optional;

import me.gyuwoon.inflearnthejavatest.domain.Member;
import me.gyuwoon.inflearnthejavatest.domain.Study;

public interface MemberService {
    void validate(Long memberId);
    
    Optional<Member> findById(Long memberId);

    // 새로운 스터디가 나왔다고 알려주는 서비스라 가정한다
    void notify(Study newStudy);

    // member한테 직접적으로 뭔가 알림을 줘야하는 경우
    void notify(Member member);
}

package me.gyuwoon.inflearnthejavatest.study;

import java.util.Optional;

import me.gyuwoon.inflearnthejavatest.domain.Member;
import me.gyuwoon.inflearnthejavatest.domain.Study;
import me.gyuwoon.inflearnthejavatest.member.MemberService;

public class StudyService {

    private final MemberService memberService;

    private final StudyRepository repository;

    public StudyService(MemberService memberService, StudyRepository repository) {
        // memberservice와 repository는 null이면 안된다. assert 키워드를 사용해서 확인하자.
        assert memberService != null;
        assert repository != null;
        this.memberService = memberService;
        this.repository = repository;
    }

    public Study createNewStudy(Long memberId, Study study) {
        Optional<Member> member = memberService.findById(memberId);
        study.setOwner(member.orElseThrow(
                () -> new IllegalArgumentException("Member doesn't exist for id : '" + memberId)));
        
        Study newStudy = repository.save(study);
        memberService.notify(newStudy);
        memberService.notify(member.get());
        return newStudy;
    }

}

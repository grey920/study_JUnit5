package me.gyuwoon.inflearnthejavatest.study;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import me.gyuwoon.inflearnthejavatest.domain.Member;
import me.gyuwoon.inflearnthejavatest.domain.Study;
import me.gyuwoon.inflearnthejavatest.member.MemberService;

/*@ExtendWith(MockitoExtension.class)를 해줘야 Mock 애노테이션을 쓸 수 있다.
 * */
@ExtendWith(MockitoExtension.class)
class StudyServiceTest {
    /* Mock 객체 만들기 2 - 애노테이션 */
   // @Mock MemberService memberService;
    
   // @Mock StudyRepository studyRepository;

    // 1. StudyService 인스턴스를 만드는 테스트
    @Test   /* Mock객체 만들기 - 생성자를 사용해서 주입 3*/
    void createStudyService( @Mock MemberService memberService, @Mock StudyRepository studyRepository) {
        /* Mock 객체 만들기 1 */
        // static import로 사용할 수도 있다.
        //MemberService memberService = mock(MemberService.class);
        // 내가 만들고자하는 인터페이스 혹은 클래스 타입을 주면 가짜로 mock객체를 만들어줌
        //StudyRepository studyRepository = Mockito.mock(StudyRepository.class);
        
        // MemberService와 StudyRepository가 있어야 StudyService 인스턴스를 만들 수 있는데 둘 다 구현체없이 인터페이스만 있기 때문에 만들 수 없다 -> Mock 쓰기 좋은 상황
        // 구현체 없이 인터페이스만 있고, 나는 인터페이스를 기반으로 코드를 작성하고 있을 때 제대로 동작하는지 확인하려면 Mocking을 해야한다.
        StudyService studyService = new StudyService(memberService, studyRepository);
        
        assertNotNull(studyService);
    }

}

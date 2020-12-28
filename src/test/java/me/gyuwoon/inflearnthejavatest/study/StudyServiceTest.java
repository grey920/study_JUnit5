package me.gyuwoon.inflearnthejavatest.study;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import me.gyuwoon.inflearnthejavatest.domain.Member;
import me.gyuwoon.inflearnthejavatest.domain.Study;
import me.gyuwoon.inflearnthejavatest.member.MemberService;

/*2020.12.29 
 * 23. 연습문제
 */
@ExtendWith(MockitoExtension.class)
class StudyServiceTest {
    @Mock
    MemberService memberService;
    @Mock
    StudyRepository studyRepository;


    @Test
    void createNewStudy() {
        // Given - 어떠한 상황이 주어졌을 때
        StudyService studyService = new StudyService(memberService,
                studyRepository);
        assertNotNull(studyService);

        Member member = new Member();
        member.setId(1L);
        member.setEmail("kyewoon@email.com");

        Study study = new Study(10, "테스트");

        //BDD에 따르면 Given에 해당하는데 API가 When이라서 안어울림 -> 바꿀 수 있다.
//        when(memberService.findById(1L)).thenReturn(Optional.of(member));
//        when(studyRepository.save(study)).thenReturn(study);

        given(memberService.findById(1L)).willReturn(Optional.of(member));
        given(studyRepository.save(study)).willReturn(study);
        
        // When - 뭔가를 하면
        studyService.createNewStudy(1L, study); // 새로운 스터디를 만들면

        // Then - 그러면 ~ 이럴것이다.
        // assertEquals(member, study.getOwner());
        //verify(memberService, times(1)).notify(study);
        then(memberService).should(times(1)).notify(study);
        
        // verifyNoInteractions(memberService);
        then(memberService).shouldHaveNoMoreInteractions();
    }



 

}

package me.gyuwoon.inflearnthejavatest.study;

import static org.mockito.ArgumentMatchers.any;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import me.gyuwoon.inflearnthejavatest.domain.Member;
import me.gyuwoon.inflearnthejavatest.domain.Study;
import me.gyuwoon.inflearnthejavatest.member.MemberService;

/*
 * Mock 객체 Stubbing - Stubbing : mock객체 행동을 조작하는 것 모든 Mock객체의 행동 - Null을 리턴한다
 * (Optional 타입은 Optional.empty) - Primitive 타입은 기본 Primitive값 - 콜렉션은 비어있는 콜렉션 -
 * Void 메소드는 예외를 던지지 않고 아무런 일도 발생하지 않는다. Mock 객체를 조작해서 - 특정한 매개변수를 받은 경우 특정한 값을
 * 리턴하거나 예외를 던지도록 만들 수 있다. - Void 메소드 특정 매개변수를 받거나 호출된 경우 예외를 발생시킬 수 있다. - 메소드가
 * 동일한 매개변수로 여러번 호출될 때 각기 다르게 행동하도록 조작할 수도 있다.
 */
@ExtendWith(MockitoExtension.class)
class StudyServiceTest {

    @Test
    void createNewStudy(@Mock MemberService memberService, @Mock StudyRepository studyRepository) {
        StudyService studyService = new StudyService(memberService, studyRepository);
        assertNotNull(studyService);

        /* Stubbing */
        Member member = new Member();
        member.setId(1L);
        member.setEmail("kyewoon@email.com");

        /* 특정한 매개변수를 받은 경우 특정한 값을 리턴하거나 예외를 던지도록 할 수 있다.*/
        when(memberService.findById(1L)).thenReturn(Optional.of(member));// 1이라는 값으로 호출되면 ->Optional타입의 member객체를 리턴하라
        // memberService.validate(2L); // 아무일도 일어나지 않는다 (에러X)

        assertEquals("kyewoon@email.com", memberService.findById(1L).get().getEmail());

        /*void 메소드 특정 매개변수를 받거나 호출된 경우 예외를 발생시킬 수 있다.*/
        doThrow(new IllegalArgumentException()).when(memberService).validate(1L);// 1값으로 validate 메소드가 호출되면 예외를 던지도록
                                                                                 // stubbing

        assertThrows(IllegalArgumentException.class, () -> {
            memberService.validate(1L);
        });

        memberService.validate(2L);

        /* 메소드가 동일한 매개변수로 여러번 호출될 때 각기 ㄱ다르게 행동하도록 조작할 수 있다*/
        when(memberService.findById(any())).thenReturn(Optional.of(member)).thenThrow(new RuntimeException())
                .thenReturn(Optional.empty());

        Optional<Member> byId = memberService.findById(1L);
        assertEquals("kyewoon@email.com", byId.get().getEmail());

        assertThrows(RuntimeException.class, () -> {
            memberService.findById(2L);
        });

        assertEquals(Optional.empty(), memberService.findById(3L));
    }

}

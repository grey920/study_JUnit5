package me.gyuwoon.inflearnthejavatest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.RepetitionInfo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.AggregateWith;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.aggregator.ArgumentsAggregationException;
import org.junit.jupiter.params.aggregator.ArgumentsAggregator;
import org.junit.jupiter.params.converter.ArgumentConversionException;
import org.junit.jupiter.params.converter.SimpleArgumentConverter;
import org.junit.jupiter.params.provider.CsvSource;

import me.gyuwoon.inflearnthejavatest.domain.Study;

/* JUnit 5 : 확장모델
 * - Junit 4의 확장 모델은 @RunWith(Runner), TestRule, MethodRule.
 * - JUnit 5의 확장 모델은 단 하나, Extension.
 * 
 * 확장팩 등록 방법
- 선언적인 등록 @ExtendWith
- 프로그래밍 등록 @RegisterExtension
- 자동 등록 자바 ServiceLoader 이용

 * 확장팩 만드는 방법
- 테스트 실행 조건
- 테스트 인스턴스 팩토리
- 테스트 인스턴스 후-처리기
- 테스트 매개변수 리졸버
- 테스트 라이프사이클 콜백
- 예외 처리

 */
/* 1. 선언적으로 Extension 사용하기 @ExtendWith -> FindSlowTestExtension 이 인스턴스를 커스텀 할 수 없다.
 *  만약 걸린시간 조건(THRESHOLD) 각자 다르게 값을 넣고 싶을때 선언적으로느 할 수 없다. 
 *  => 2. 필드에 static으로 정의 - @RegisterExtension
 * */
//@ExtendWith(FindSlowTestExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class) // Orderer를 넘겨준다. 
class StudyTest {  
    
    
    int value = 1;
    
    /* 2. 프로그래밍 등록 
     * - FindSlowTestExtension 인스턴스를 만들어서 등록하는 방법 */
    @RegisterExtension
    static FindSlowTestExtension findSlowTestExtension = new FindSlowTestExtension(1000L);

    @Order(2)
    @FastTest
    //@DisplayName("스터디 만들기 fast")
    void create_new_study() {
        System.out.println(this);
        System.out.println(value++);
        Study actual = new Study(1);
        assertThat(actual.getLimit()).isGreaterThan(0);
    }

    @Order(1) // spring이 제공하는 것 쓰지말고!
    //@SlowTest
    @Test
    //@DisplayName("스터디 만들기 slow ")
    void create_new_study_again() throws InterruptedException {
        Thread.sleep(1005L);
        System.out.println(this);
        System.out.println("create1 " + value++);
    }
    
    @Order(3)
    @DisplayName("스터디 만들기")
    @RepeatedTest(value = 10, name = "{displayName}, {currentRepetition}/{totalRepetitions}") // value: 반복횟수, 
                                                                                              // name : 개별 테스트 이름
    void repeatedTest(RepetitionInfo repetitionInfo) { // RepetitionInfo 타입의 인자를 받으면
                                                       // 이를 통해 현재 몇번째 반복하고 있는지,
                                                       // 총 몇번을 반복해야 하는지 알 수 있다.
        System.out.println(this);
        System.out
                .println("test " + repetitionInfo.getCurrentRepetition() + "/" + repetitionInfo.getTotalRepetitions());
    }
    
    /* ParameterizedTest다른 값들을 가지고 반복 테스트를 하고싶을 때 */
    @Order(4)
    @DisplayName("스터디 만들기")
    @ParameterizedTest(name="{index} {displayName} message={0}")
    @CsvSource({"10, '자바 스터디'", "20, 스프링"}) 
    void ParameterizedTest(@AggregateWith(StudyAggregator.class) Study study) { // 여러 인자값들을 받아서 조합해 Study인스턴스로 만든 테스트 
        // 2) void ParameterizedTest(ArgumentsAccessor argumentsAccessor ) // ArgumentsAccessor : 인자값을 조합
        // 1) void ParameterizedTest(Integer limit, String name) - 이렇게 따로 넘겨도 되고, ArgumentsAccessor로 합쳐서 Study로 넘길 수도 있다. 
        
       // 2) Study study = new Study(argumentsAccessor.getInteger(0), argumentsAccessor.getString(1));
       // 1) Study study = new Study(limit, name);
        System.out.println(study);
        System.out.println(this);
    }
    
    /* 커스텀 Accessor 
     * 제약조건
     * - 반드시 static 이너 클래스이거나 public 클래스여야 한다*/
    static class StudyAggregator implements ArgumentsAggregator {

        @Override
        public Object aggregateArguments(ArgumentsAccessor accessor, ParameterContext context)
                throws ArgumentsAggregationException {
            return new Study(accessor.getInteger(0), accessor.getString(1));
        }
        
    }
    
    
    /* 커스텀한 구현체 만들기 
     * - 하나의 Argument를 다른 타입으로 변환할 때에만 적용되는 컨버터 
     * -> 두 개라면 @CsvSource */
    static class StudyConverter extends SimpleArgumentConverter{

        @Override
        protected Object convert(Object source, Class<?> targetType) throws ArgumentConversionException {
            assertEquals(Study.class, targetType, "Can only convert to Study");
            return new Study(Integer.parseInt(source.toString())); // Study는 limit을 받는 생성자가 있기 때문에 그대로 쓴다 (문자열로 변환 후 Integer로 변환)
        }
        
    }
    
    @BeforeAll
    static void beforeAll() { // @TestInstance(Lifecycle.PER_CLASS)이면 메서드들간 공유하기 때문에 static일 필요가 없다.
        System.out.println("before all");
    }

    @AfterAll
    static void afterAll() {
        System.out.println("after all");
    }

    @BeforeEach
    void beforeEach() {
        System.out.println("before each");
    }

    @AfterEach
    void afterEach() {
        System.out.println("after each");
    }
}

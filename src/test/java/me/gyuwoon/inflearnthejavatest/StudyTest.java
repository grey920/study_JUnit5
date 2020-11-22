package me.gyuwoon.inflearnthejavatest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.RepetitionInfo;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.AggregateWith;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.aggregator.ArgumentsAggregationException;
import org.junit.jupiter.params.aggregator.ArgumentsAggregator;
import org.junit.jupiter.params.converter.ArgumentConversionException;
import org.junit.jupiter.params.converter.SimpleArgumentConverter;
import org.junit.jupiter.params.provider.CsvSource;


@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@TestInstance(Lifecycle.PER_CLASS) // 클래스마다 인스턴스를 생성(클래스당 하나의 인스턴스) - 모든 테스트가 하나의 인스턴스를 공유 -> 성능상의 장점, 제약이 느슨해짐
class StudyTest { // 기본은 테스트간의 의존성을 없애기 위해 메소드마다(테스트마다) 인스턴스가 새롭게 생성된다. 
    
    
    int value = 1;

    @FastTest
    @DisplayName("스터디 만들기 fast")
    void create_new_study() {
        System.out.println(this);
        System.out.println(value++);
        Study actual = new Study(1);
        assertThat(actual.getLimit()).isGreaterThan(0);
    }

    @SlowTest
    @DisplayName("스터디 만들기 slow ")
    void create_new_study_again() {
        System.out.println(this);
        System.out.println("create1 " + value++);
    }

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
     void beforeAll() { // @TestInstance(Lifecycle.PER_CLASS)이면 메서드들간 공유하기 때문에 static일 필요가 없다.
        System.out.println("before all");
    }

    @AfterAll
     void afterAll() {
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

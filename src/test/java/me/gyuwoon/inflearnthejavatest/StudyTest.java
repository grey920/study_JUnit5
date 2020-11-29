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
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.RepetitionInfo;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
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

/* 태스트 순서
 * 내부적으로 정해진 순서가 있긴 하다. 
 * 하지만 이 순서에 의존해서는 안된다. 
 * 내부 구현 로직에 따라 언제라도 바뀔 수 있다. 
 *  "제대로 작성된 유닛테스트라면(하나의 단위라면), 다른 단위테스트와는 독립적으로 실행되어야 한다."
 *  하지만! 경우에 따라 순서대로 테스트를 작성해야 할 때도 있다. 
 *   - 통합테스트, 기능테스트, 시나리오 테스트 (유즈케이스를 테스트하는 경우)
 *
 * @Order()
 *  - 인스턴스를 독립적으로 만들지만 실행 순서를 정해줄 수 있다. 
 *  - @TestInstance(Lifecycle.PER_CLASS)를 쓰지 않아도 괜찮다. 하지만 같이 쓰면 상태 정보도 공유하면서 순차적으로 실행하는 테스트를 만들 수 있다.
 *  - 단위테스트의 경우는 굳이 순서를 정할 필요는 없을 것 같고, 유즈케이스 기반으로 시나리오 테스트를 만들 때 이용하면 유용.
 */
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
//@TestInstance(Lifecycle.PER_CLASS) 
@TestMethodOrder(MethodOrderer.OrderAnnotation.class) // Orderer를 넘겨준다. 
class StudyTest {  
    
    
    int value = 1;

    @Order(2)
    @FastTest
    @DisplayName("스터디 만들기 fast")
    void create_new_study() {
        System.out.println(this);
        System.out.println(value++);
        Study actual = new Study(1);
        assertThat(actual.getLimit()).isGreaterThan(0);
    }

    @Order(1) // spring이 제공하는 것 쓰지말고!
    @SlowTest
    @DisplayName("스터디 만들기 slow ")
    void create_new_study_again() {
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

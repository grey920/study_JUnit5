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
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledOnJre;
import org.junit.jupiter.api.condition.JRE;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.converter.ArgumentConversionException;
import org.junit.jupiter.params.converter.ConvertWith;
import org.junit.jupiter.params.converter.SimpleArgumentConverter;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.ValueSource;

/* 테스트 반복하기
 * 
 * */
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class StudyTest {

    @FastTest
    @DisplayName("스터디 만들기 fast")
    void create_new_study() {
        Study actual = new Study(100);
        assertThat(actual.getLimit()).isGreaterThan(0);
    }

    @SlowTest
    @DisplayName("스터디 만들기 slow ")
    void create_new_study_again() {
        System.out.println("create1");
    }

    @DisplayName("스터디 만들기")
    @RepeatedTest(value = 10, name = "{displayName}, {currentRepetition}/{totalRepetitions}") // value: 반복횟수, 
                                                                                              // name : 개별 테스트 이름
    void repeatedTest(RepetitionInfo repetitionInfo) { // RepetitionInfo 타입의 인자를 받으면
                                                       // 이를 통해 현재 몇번째 반복하고 있는지,
                                                       // 총 몇번을 반복해야 하는지 알 수 있다.
        System.out
                .println("test " + repetitionInfo.getCurrentRepetition() + "/" + repetitionInfo.getTotalRepetitions());
    }
    
    /* ParameterizedTest다른 값들을 가지고 반복 테스트를 하고싶을 때 */
    @DisplayName("스터디 만들기")
    @ParameterizedTest(name="{index} {displayName} message={0}")
    @ValueSource(ints = {10, 20, 40}) // 숫자를 study 타입으로 변환하고자 함
    void ParameterizedTest(@ConvertWith(StudyConverter.class) Study study ) { //@ConvertWith로 커스텀한 converter가 어떤것인지 알려두어야 한다 (괄호 안에는 커스텀한 클래스)
        // SimpleArgumentConverter : 커스텀한 인자값의 타입을 변환해주는 인터페이스 구현체
        System.out.println(study.getLimit());
    }
    
    // 커스텀한 구현체 만들기
    static class StudyConverter extends SimpleArgumentConverter{

        @Override
        protected Object convert(Object source, Class<?> targetType) throws ArgumentConversionException {
            assertEquals(Study.class, targetType, "Can only convert to Study");
            return new Study(Integer.parseInt(source.toString())); // Study는 limit을 받는 생성자가 있기 때문에 그대로 쓴다 (문자열로 변환 후 Integer로 변환)
        }
        
    }
    
    @BeforeAll
    static void beforeAll() {
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

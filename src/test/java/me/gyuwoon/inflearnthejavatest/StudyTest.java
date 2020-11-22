package me.gyuwoon.inflearnthejavatest;

import static org.assertj.core.api.Assertions.assertThat;

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
    @ParameterizedTest(name="{index} {displayName} message={0}") // 파라미터를 인덱스로 참조 가능하다.
    @ValueSource(strings = {"날씨가", "많이", "추워지고", "있네요"}) //파라미터 정의
    void ParameterizedTest(String message ) { // 파라미터 갯수만큼 테스트 실행
        System.out.println(message);
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

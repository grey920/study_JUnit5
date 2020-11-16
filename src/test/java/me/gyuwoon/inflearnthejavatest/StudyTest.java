package me.gyuwoon.inflearnthejavatest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assumptions.assumeTrue;
import static org.junit.jupiter.api.Assumptions.assumingThat;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

/* 조건에 따라 테스트 수행하기  - OS, 자바버전, 환경변수 등등 : assume*/
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class StudyTest {

	@Test
	@DisplayName("스터디 만들기 😎")
	void create_new_study() {
		// 시스템에서 환경변수를 꺼낸다 -> 테스트 환경이 로컬인 경우에만 다음에 있는 테스트를 수행한다.
		String test_env = (System.getenv("TEST_ENV"));
		System.out.println(test_env);
		assumeTrue("LOCAL".equalsIgnoreCase(test_env));

		assumingThat("LOCAL".equalsIgnoreCase(test_env), () -> {
			// 환경이 LOCAL일때 실행
			Study actual = new Study(100);
			assertThat(actual.getLimit()).isGreaterThan(0);
		});

		assumingThat("gyuwoon".equalsIgnoreCase(test_env), () -> {
			// 환경이 gyuwoon일때 실행
			Study actual = new Study(10);
			assertThat(actual.getLimit()).isGreaterThan(0);
		});

		Study actual = new Study(10);
		assertThat(actual.getLimit()).isGreaterThan(0);
	}

	@Test
	@DisplayName("스터디 만들기 😃✨ ")
	void create_new_study_again() {
		System.out.println("create1");
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

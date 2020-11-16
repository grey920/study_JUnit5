package me.gyuwoon.inflearnthejavatest;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledOnOs;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;
import org.junit.jupiter.api.condition.EnabledOnJre;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.JRE;
import org.junit.jupiter.api.condition.OS;

/* 조건에 따라 테스트 수행하기  - OS, 자바버전, 환경변수 등등 : assume*/
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class StudyTest {

	@Test
	@DisplayName("스터디 만들기 😎")
	// 테스트환경에 어떤 환경변수를 쓸 것인지 이름, 어떤것과 매치가 되는지
	@EnabledIfEnvironmentVariable(named = "TEST_ENV", matches = "local")
	void create_new_study() {
		// 시스템에서 환경변수를 꺼낸다 -> 테스트 환경이 로컬인 경우에만 다음에 있는 테스트를 수행한다.
		String test_env = (System.getenv("TEST_ENV"));
		System.out.println("local");
		Study actual = new Study(100);
		assertThat(actual.getLimit()).isGreaterThan(0);
	}

	@Test
	@DisplayName("스터디 만들기 😃✨ ")
	@EnabledOnJre(JRE.OTHER)
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

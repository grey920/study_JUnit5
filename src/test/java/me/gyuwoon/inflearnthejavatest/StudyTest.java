package me.gyuwoon.inflearnthejavatest;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledOnJre;
import org.junit.jupiter.api.condition.JRE;

/* 커스텀 태그
 * 메타 애노테이션으로 사용할 수 있다. == 우리가 만든 애노테이션을 사용해도 동일한 기능
 * */
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class StudyTest {

	@FastTest // @Tag("fast") 생략 가능 - ""안에 오타 가능성이 있다.
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

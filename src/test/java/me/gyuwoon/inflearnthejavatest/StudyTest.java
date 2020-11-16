package me.gyuwoon.inflearnthejavatest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class StudyTest {

	@Test
	@DisplayName("스터디 만들기 😎")
	void create_new_study() {
		// Exception이 발생하는 것을 확인하는 방법 => assertThrows
		// assertThrows(어떤 익셉션이 발생하는지, 어떤 코드를 실행할 때)
		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new Study(-10));
		// 발생하는 에러메시지를 받아서 내가 기대했던 메시지와 같은지 확인
		String message = exception.getMessage();
		assertEquals("limit은 0보다 커야 한다.", message);
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

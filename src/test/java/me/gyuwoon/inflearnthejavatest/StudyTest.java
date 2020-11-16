package me.gyuwoon.inflearnthejavatest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTimeout;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;

import java.time.Duration;

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
		// 실행하는 코드가 특정한 시간 이내에 반드시 끝나야 하는 경우를 테스트. 
		// assertTimeout의 단점 : 코드블럭 안이 다 끝날때까지 기다리고 시간을 비교한다. 
		// 만약 조건의 시간이 되면 바로 테스트를 종료했으면 좋겠다! => assertTimeoutPreemptively
		assertTimeoutPreemptively(Duration.ofMillis(100), () -> {
			new Study(10);
			Thread.sleep(300);
		});
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

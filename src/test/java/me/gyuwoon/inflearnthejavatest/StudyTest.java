package me.gyuwoon.inflearnthejavatest;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
		Study study = new Study(-10);
		
		// 세 개의 assert문을 assertAll 안에 람다식으로 묶은 것. (Executable 3개를 파라미터로 전달)
		// 세 개를 한 번에 실행해서 뭐가 깨지는지 한 번에 알 수 있다. 
		assertAll(
				() -> assertNotNull(study),
				() ->  assertEquals(StudyStatus.DRAFT, study.getStatus(),
						() -> "스터디를 처음 만들면 "+ StudyStatus.DRAFT+" 상태다"),
				() -> assertTrue(study.getLimit() > 0, "스터디 최대 참석 가능 인원은 0보다 커야 한다.")
				);
		
		// 두 assert가 다 깨져있어도 실행 시 첫번째 assert만 깨지고 종료된다 -> 두번째 assert문은 첫번째를 고쳐야 확인 가능
		// => 한 번에 실행하는 방법 ?? assertAll
//		assertEquals(StudyStatus.DRAFT, study.getStatus(),
//				()->  "스터디를 처음 만들면 "+ StudyStatus.DRAFT+" 상태다");
//		// limit이 0이 아니라고 가정.
//		assertTrue(study.getLimit() > 0, "스터디 최대 참석 가능 인원은 0보다 커야 한다.");
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

package me.gyuwoon.inflearnthejavatest;

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
		Study study = new Study();
		assertNotNull(study);
		// Study의 상태값이 DRAFT이어야 한다고 가정.
		// assertEquals(기대하는 값, 실제 나오는 값, 메시지(String)) 
		// 메시지를 람다식으로 만들면 문자열 연산을 최대한 필요한 시점(실패했을때에만)에만 한다. 
		// 그러나 메시지를 String으로 바로 넣으면 테스트시마다 무조건 문자열 연산을 한다. ㅏ따라서 성능상 람다식이 유리하다.
		assertEquals(StudyStatus.DRAFT, study.getStatus(), ()->  "스터디를 처음 만들면 "+ StudyStatus.DRAFT+" 상태다");
		
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

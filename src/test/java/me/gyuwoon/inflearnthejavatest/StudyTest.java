package me.gyuwoon.inflearnthejavatest;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

class StudyTest {

	// Junit5에서 리플렉션을 사용하기 때문에 public을 굳이 붙여주지 않아도 된다
	@Test
	void create() {
		Study study = new Study();
		assertNotNull(study);
		System.out.println("create");
	}

	@Test
	@Disabled // 테스트 수행하고 싶지 않을때 (깨지는 테스트인데 아직 고치지 못한 경우)
	void create1() {
		System.out.println("create1");
	}

	// 반드시 static으로 만들고 void여야 한다. (private은 안되고 default는 됨)
	// 모든 테스트를 실행하기 전 단 한 번만 실행된다.
	@BeforeAll
	static void beforeAll() {
		System.out.println("before all");
	}

	// 모든 테스트가 실행된 후 단 한 번만 실행된다. static void여야 한다.
	@AfterAll
	static void afterAll() {
		System.out.println("after all");
	}

	// 각각의 테스트가 실행되기 전에 실행됨. static일 필요는 없다.
	@BeforeEach
	void beforeEach() {
		System.out.println("before each");
	}

	@AfterEach
	void afterEach() {
		System.out.println("after each");
	}
}

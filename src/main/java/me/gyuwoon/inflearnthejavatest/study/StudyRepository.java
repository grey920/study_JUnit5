package me.gyuwoon.inflearnthejavatest.study;

import org.springframework.data.jpa.repository.JpaRepository;

import me.gyuwoon.inflearnthejavatest.domain.Study;

public interface StudyRepository extends JpaRepository<Study, Long>{

}

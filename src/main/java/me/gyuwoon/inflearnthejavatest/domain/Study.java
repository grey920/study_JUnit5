package me.gyuwoon.inflearnthejavatest.domain;

import java.time.LocalDateTime;

import me.gyuwoon.inflearnthejavatest.study.StudyStatus;

public class Study {

	private StudyStatus status = StudyStatus.DRAFT;
	
	private int limitCount;
	
	private String name;
	
	private LocalDateTime openedDateTime;
	//@ManyToOne
	private Member owner;
	
	
	
	public Study(int limit, String name) {
        this.limitCount = limit;
        this.name = name;
    }

    public Study(int limit) {
		if(limit < 0) {
			throw new IllegalArgumentException("limit은 0보다 커야 한다.");
		}
		this.limitCount = limit;
	}
	
    // 스터디를 공개하면 스터디의 상태가 공개로 바뀐다.
    public void open() {
        this.openedDateTime = LocalDateTime.now();
        this.status = StudyStatus.OPENED;
        
    }

  
	
	
}

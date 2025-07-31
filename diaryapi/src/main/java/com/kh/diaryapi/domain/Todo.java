package com.kh.diaryapi.domain;


import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tbl_todo")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Todo {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TODO_SEQ_GEN")
	private Long tno; // TODO 번호
	private String ttitle; // TODO 제목
	private String twriter; // TODO 글쓴이 
	private String tcontent; // TODO 내용
	private LocalDate dueDate; // TODO 마감일
	private boolean done; // TODO 완료 여부

	public void changeTtitle(String ttitle) {
		this.ttitle = ttitle;
	}

	public void changeTcontent(String tcontent) {
		this.tcontent = tcontent;
	}

	public void changeDueDate(LocalDate dueDate) {
		this.dueDate = dueDate;
	}

	public void changeDone(boolean done) {
		this.done = done;
	}

}
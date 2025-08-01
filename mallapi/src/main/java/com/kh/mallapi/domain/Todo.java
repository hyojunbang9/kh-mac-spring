package com.kh.mallapi.domain;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

//도메인으로 만들어지는 부분 => 테이블

@Entity
@Table(name = "tbl_todo")
@SequenceGenerator(name = "TODO_SEQ_GEN", sequenceName = "TODO_SEQ", initialValue = 1, allocationSize = 1)
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Todo {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TODO_SEQ_GEN")
	private Long tno;
	private String title;
	private String writer;
	private boolean complete;
	private LocalDate dueDate;

	public void changeTitle(String title) {
		this.title = title;
	}
	
	public void changeWriter(String writer) {
		this.writer = writer;
	}

	public void changeComplete(boolean complete) {
		this.complete = complete;
	}

	public void changeDueDate(LocalDate dueDate) {
		this.dueDate = dueDate;
	}
}

package com.kh.diaryapi.domain;

import java.time.LocalDate;

import jakarta.persistence.*;
import lombok.*;

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
	private String tcontent; // TODO 내용
	private LocalDate tdate; // TODO 날짜

	public void changeTtitlle(String ttitle) {
		this.ttitle = ttitle;
	}

	public void changeTdate(LocalDate tdate) {
		this.tdate = tdate;
	}

	public void changeTcontent(String tcontent) {
		this.tcontent = tcontent;
	}

}
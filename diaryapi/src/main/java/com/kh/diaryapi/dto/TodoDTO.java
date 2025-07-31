package com.kh.diaryapi.dto;

import lombok.*;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TodoDTO {
	private Long tno; // TODO 번호
	private String ttitle; // TODO 제목
	private String twriter; // TODO 글쓴이 
	private String tcontent; // TODO 내용
	private boolean done; // TODO 완료 여부
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private LocalDate dueDate; // TODO 날짜
}
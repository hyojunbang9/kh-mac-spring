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
	private String tcontent; // TODO 내용
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private LocalDate tdate; // TODO 날짜
}
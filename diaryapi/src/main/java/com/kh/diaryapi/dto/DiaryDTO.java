package com.kh.diaryapi.dto;

import java.time.LocalDate;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DiaryDTO {
	private Long dno; 			// DIARY 일기번호 (PK)
	private String dtitle; 		// DIARY 제목
	private String dcontent; 	// DIARY 내용
	private String dwriter; 	// DIARY 글쓴이
	private String dweather; 	// DIARY 날씨
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private LocalDate ddate; 	// DIARY 날짜
}

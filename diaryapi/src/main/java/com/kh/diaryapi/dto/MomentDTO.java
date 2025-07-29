package com.kh.diaryapi.dto;

import lombok.*;

import java.time.LocalDate;
import java.util.*;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonFormat;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MomentDTO {
	private Long mno; // MOMENT 번호
	private String mtitle; // MOMENT 제목
	private String mcontent; // MOMENT 내용
	private String mlocation; // MOMENT 위치

	@Builder.Default
	private List<MultipartFile> files = new ArrayList<>();

	@Builder.Default
	private List<String> uploadFileNames = new ArrayList<>();

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private LocalDate mdate; // MOMENT 날짜
}
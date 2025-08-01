package com.kh.mallapi.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TodoDTO {
	private Long tno;
	private String title;
	private String writer;
	private boolean complete;
	
	private LocalDate dueDate;
}

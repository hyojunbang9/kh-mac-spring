package com.kh.diaryapi.domain;

import java.time.LocalDate;
import jakarta.persistence.*;
import lombok.*;

@Entity
@SequenceGenerator(name = "DIARY_SEQ_GEN", // 시퀀스 제너레이터 이름
		sequenceName = "DIARY_SEQ", // 시퀀스 이름
		initialValue = 1, // 시작값
		allocationSize = 1 // 메모리를 통해 할당할 범위 사이즈
)
@Table(name = "TBL_DIARY")
@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Diary {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DIARY_SEQ_GEN")
	// 사용할 전략을 시퀀스로 선택, 식별자 생성기를 설정해 놓은 DIARY_SEQ_GEN으로 설정
	private Long dno; // DIARY 일기번호 (PK)
	private String dtitle; // DIARY 제목
	private String dwriter; // DIARY 글쓴이
	private String dcontent; // DIARY 내용
	private String dweather; // DIARY 날씨
	private LocalDate ddate; // DIARY 날짜

	public void changeDtitle(String dtitle) {
		this.dtitle = dtitle;
	}

	public void changeDcontent(String dcontent) {
		this.dcontent = dcontent;
	}

	public void changeDdate(LocalDate ddate) {
		this.ddate = ddate;
	}

	public void changeDweather(String dweather) {
		this.dweather = dweather;
	}
}

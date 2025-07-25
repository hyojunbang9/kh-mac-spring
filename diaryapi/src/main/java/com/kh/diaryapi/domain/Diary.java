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
	private Long dno;
	private String title;
	private String writer;
	private boolean complete;
	private LocalDate dueDate;

	public void changeTitle(String title) {
		this.title = title;
	}

	public void changeComplete(boolean complete) {
		this.complete = complete;
	}

	public void changeDueDate(LocalDate dueDate) {
		this.dueDate = dueDate;
	}
}

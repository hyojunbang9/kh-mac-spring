package com.kh.diaryapi.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Table(name = "tbl_moment")
@Getter
@ToString(exclude = "imageList")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Moment {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MOMENT_SEQ_GEN")
	private Long mno; // MOMENT 번호
	private String mtitle; // MOMENT 제목
	private String mcontent; // MOMENT 내용
	private String mlocation; // MOMENT 위치
	private LocalDate mdate; // MOMENT 날짜

	@ElementCollection
	@Builder.Default

	private List<MomentImage> imageList = new ArrayList<>();

	public void changeMtitle(String mtitle) {
		this.mtitle = mtitle;
	}

	public void changeMcontent(String mcontent) {
		this.mcontent = mcontent;

	}

	public void changeMdate(LocalDate mdate) {
		this.mdate = mdate;
	}

	public void changeMlocation(String mlocation) {
		this.mlocation = mlocation;
	}

	public void addImage(MomentImage image) {
// 이미지 추가시 순서(ord) 자동 설정 (0, 1, 2, ...)
		image.setOrd(this.imageList.size());
		imageList.add(image);
	}

	public void addImageString(String fileName) {
		MomentImage momentImage = MomentImage.builder().fileName(fileName).build();
		addImage(momentImage);
	}

	public void clearList() {
		this.imageList.clear();
	}
}
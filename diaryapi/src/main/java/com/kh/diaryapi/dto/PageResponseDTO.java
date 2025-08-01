package com.kh.diaryapi.dto;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import lombok.Builder;
import lombok.Data;

@Data
public class PageResponseDTO<E> {
	private List<E> dtoList;
	private List<Integer> pageNumList;
	private PageRequestDTO pageRequestDTO;
	private boolean prev;
	private boolean next;
	private int totalCount;
	private int prevPage;
	private int nextPage;
	private int totalPage;
	private int current;

	@Builder(builderMethodName = "withAll")
	public PageResponseDTO(List<E> dtoList, PageRequestDTO pageRequestDTO, long totalCount) {
		this.dtoList = dtoList;
		this.pageRequestDTO = pageRequestDTO;
		this.totalCount = (int) totalCount;
		int end = (int) (Math.ceil(pageRequestDTO.getPage() / 10.0)) * 10;
		int start = end - 9;
		int last = (int) (Math.ceil((totalCount / (double) pageRequestDTO.getSize())));
		end = end > last ? last : end;
		this.prev = start > 1;
		this.next = totalCount > end * pageRequestDTO.getSize();
		// start =1, end= 10, [1, 2, 3, 4, 5, 6, 7, 8, 9, 10] List<Integer> 만들어낸다.
		this.pageNumList = IntStream.rangeClosed(start, end).boxed().collect(Collectors.toList());
		if (prev) {
			this.prevPage = start - 1;
		}
		if (next) {
		}
		this.nextPage = end + 1;
		this.totalPage = this.pageNumList.size();
		this.current = pageRequestDTO.getPage();
	}
}

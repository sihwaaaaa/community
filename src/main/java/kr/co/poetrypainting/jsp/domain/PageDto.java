package kr.co.poetrypainting.jsp.domain;

import lombok.Data;

@Data
public class PageDto {
	//하단에 출력 될 페이지 사이즈
	 private int pageCount = 10;
	
	//시작 시작페이지 
	 private int startPage;

	//종료 시작페이지 
	 private int endPage;
	
	//게시글 총 갯수
	 private int total;

	//next, prev
	 private boolean prev;
	 private boolean next;
	 private boolean doublePrev;
	 private boolean doubleNext;
	//Criteria
	 private Criteria cri;

	 
	 public PageDto(int total, Criteria cri) {
		this(10,total,cri);
	}


	public PageDto(int pageCount, int total, Criteria cri) {
		this.pageCount = pageCount;
		this.total = total;
		this.cri = cri;
		
		endPage = (cri.getPageNum() + (pageCount -1 )) / pageCount * pageCount;
		startPage = endPage - (pageCount-1);
		int realEnd = (total + (cri.getAmount() -1 )) / cri.getAmount();
		if(endPage > realEnd) {
			endPage = realEnd;
		}
		prev = cri.getPageNum() > 1;
		next = cri.getPageNum() < realEnd;
		
		doublePrev = startPage > 1;
		doubleNext = endPage < realEnd;
	}
	public static void main(String[] args) {
		Criteria cri = new Criteria(11,10);
		PageDto page = new PageDto(223, cri);
	}
	 
	 
	 
	//예정 <<,>>
}

package kr.co.poetrypainting.jsp.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Board {
	private long bno;
	private String title;
	private String content;
	private String writer;
	private Date updatedate;
	private String regdate;
	private int hitcount;
	private Integer category;
	
	private List<Attach> attachs = new ArrayList<Attach>();
	private int replyCnt; 
}

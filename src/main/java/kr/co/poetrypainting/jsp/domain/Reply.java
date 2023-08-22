package kr.co.poetrypainting.jsp.domain;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Reply {
	private Long rno;
	private String content;
	private Date regDate;
	private String writer;
	private Long bno;
}

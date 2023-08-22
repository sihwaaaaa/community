package kr.co.poetrypainting.jsp.service;

import java.util.List;

import kr.co.poetrypainting.jsp.domain.Reply;

public interface ReplyService {
	
	Long register(Reply reply);
	List<Reply> list(Long bno);
	Reply get(Long rno);
	int remove(Long bno);
	

}

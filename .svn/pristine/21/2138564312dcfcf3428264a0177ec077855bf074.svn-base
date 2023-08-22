package kr.co.poetrypainting.jsp.service;

import java.util.List;

import kr.co.poetrypainting.jsp.dao.ReplyDao;
import kr.co.poetrypainting.jsp.domain.Reply;

public class ReplyServiceImpl implements ReplyService{
	private ReplyDao dao = new ReplyDao();
	public Long register(Reply reply) {
		return (long) dao.insert(reply);
	};
	@Override
	public List<Reply> list(Long bno) {
		return dao.selectList(bno);
	}
	@Override
	public int remove(Long rno) {
		return dao.delete(rno);
	}
	@Override
	public Reply get(Long rno) {
		return dao.selectOne(rno);
	}

}

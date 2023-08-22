package kr.co.poetrypainting.jsp.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import kr.co.poetrypainting.jsp.dao.AttachDao;
import kr.co.poetrypainting.jsp.dao.BoardDao;
import kr.co.poetrypainting.jsp.dao.ReplyDao;
import kr.co.poetrypainting.jsp.domain.Attach;
import kr.co.poetrypainting.jsp.domain.Board;
import kr.co.poetrypainting.jsp.domain.Criteria;

public class BoardServiceImpl implements BoardService {
	private BoardDao dao = new BoardDao();
	private AttachDao attachDao =new AttachDao();
	private ReplyDao replyDao = new ReplyDao();
	@Override
	public Long register(Board board) {
		//글 작성 후 글번호 지정
		Long bno = (long)dao.insert(board);
		System.out.println("boardService.register() :: " + bno);
		//
		for(Attach attach : board.getAttachs()) {
			attach.setBno(bno);
			attachDao.insert(attach);
		}
		return bno;
	}

	@Override
	public Board get(Long bno) {
		dao.increaseHitCount(bno);
		Board board = dao.selectOne(bno);
		board.setAttachs(attachDao.selectList(bno));
		return board;
	}

	@Override
	public List<Board> list(Criteria cri) {
		
		List<Board> list = dao.selectList(cri);
		if(cri.getCategory() == 4){
			list = dao.selectListGallery(cri).stream().map(a -> {
				Board board = new Board();
				board.setBno(Long.valueOf(a.get("bno")));
				board.setTitle(a.get("title"));
				board.setWriter(a.get("writer"));
				
				String fullPath = a.get("fullpath");
				
				if(fullPath != null){
					board.setContent(fullPath);
					
				}
				return board;
			}).collect(Collectors.toList());
		}
		return list;
	}

	@Override
	public void modify(Board board) {
		dao.update(board);
	}

	@Override
	public void remove(Long bno) {
		//파일 시스템에 존재하는 파일 삭제
		attachDao.selectList(bno).forEach(attach -> {
			attach.getFile().delete();
			if(attach.isImage()) {
				attach.getFile(true).delete();
			}
		});
		
		//첨부 목록 삭제
		attachDao.delete(bno);
		
		//댓글 삭제
		replyDao.deleteByBno(bno);
			
		
		//tbl_board삭제
		dao.delete(bno);
	}
	@Override
	public int listCount(Criteria cri) {
		return dao.selectListCount(cri);
	}
}

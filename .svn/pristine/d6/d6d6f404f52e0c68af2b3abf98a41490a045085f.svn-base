package kr.co.poetrypainting.jsp.service;

import java.util.List;

import org.mindrot.bcrypt.BCrypt;

import kr.co.poetrypainting.jsp.dao.BoardDao;
import kr.co.poetrypainting.jsp.dao.MemberDao;
import kr.co.poetrypainting.jsp.dao.ReplyDao;
import kr.co.poetrypainting.jsp.domain.Member;
import test.BcryptTest;

public class MemberServiceImpl implements MemberService {
	private MemberDao memberDao = new MemberDao();
	private BoardDao boardDao = new BoardDao();
	private ReplyDao replyDao = new ReplyDao();
	@Override
	public List<Member> list() {
		// TODO Auto-generated method stub
		return memberDao.selectList();
	}
	@Override
	public void modify(Member member) {
		// TODO Auto-generated method stub
		memberDao.update(member);
	}
	@Override
	public void modifyPw(Member member) {
		// TODO Auto-generated method stub
		member.setPw(BCrypt.hashpw(member.getPw(), BCrypt.gensalt()));
		memberDao.updatePw(member);
	}
	@Override
	public void register(Member member) {
		member.setPw(BCrypt.hashpw(member.getPw(), BCrypt.gensalt()));
		memberDao.insert(member);
	}

	@Override
	public int login(String id, String pw) {
		Member member = memberDao.selectOne(id);
		if(member == null) {
			//아이디 없음
			return 2;
		}
		else if(!BCrypt.checkpw(pw, member.getPw())) {
			//비밀번호 틀림
			return 3;
			
		}else {
			//로그인성공
			return 1;
		}
	}

	@Override
	public Member get(String id) {
		return memberDao.selectOne(id);
	}
	
	public void remove(Member member) {
		//작성한 게시글 아이디 변경
		boardDao.updateWriterToNull(member.getId());
		//작성한 댓글 아이디 변경
		replyDao.updateWriterToNull(member.getId());
		//회원 테이블 내에서 삭제 
		memberDao.delete(member);
	}

}

package kr.co.poetrypainting.jsp.service;

import java.util.List;

import kr.co.poetrypainting.jsp.domain.Member;

public interface MemberService {
	// 회원가입
	void register(Member member);

	// 로그인
	int login(String id, String pw);

	// 회원단일 조회

	Member get(String id);
	List<Member> list();
	// 수정
	void modify(Member member);
	void modifyPw(Member member);
	
	// 탈퇴
	void remove(Member membe);
}

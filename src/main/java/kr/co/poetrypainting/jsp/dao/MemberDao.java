package kr.co.poetrypainting.jsp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.mindrot.bcrypt.BCrypt;

import kr.co.poetrypainting.jsp.domain.Member;
import kr.co.poetrypainting.jsp.util.DBConn;

public class MemberDao {
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;

	public void insert(Member vo) {
		conn = DBConn.getConnection();
		int result = 0;
		// 처리할 sql구문
		String sql = "insert into tbl_member(id, pw, name, email, addr, addrDetail) values (?,?,?,?,?,?)";
		try {
			// 문장 생성
			pstmt = conn.prepareStatement(sql);
			int idx = 1;
			pstmt.setString(idx++, vo.getId());
			pstmt.setString(idx++, vo.getPw());
			pstmt.setString(idx++, vo.getName());
			pstmt.setString(idx++, vo.getEmail());
			pstmt.setString(idx++, vo.getAddr());
			pstmt.setString(idx++, vo.getAddrDetail());
			// 문장 처리
			pstmt.executeUpdate();
			close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	

	public int updatePw(Member vo) {
		conn = DBConn.getConnection();
		int result = 0;
		// 처리할 sql구문
		String sql = "update tbl_member set pw = ? where id = ?";
		try {
			// 문장 생성
			pstmt = conn.prepareStatement(sql);
			int idx = 1;
			pstmt.setString(idx++, vo.getPw());
			pstmt.setString(idx++, vo.getId());
			// 문장 처리
			result = pstmt.executeUpdate();
			
			close();
		} catch (SQLException e) {
			e.printStackTrace();
		}return result;
	}
	
	
	public int delete(Member vo) {
		conn = DBConn.getConnection();
		int result = 0;
		// 처리할 sql구문
		String sql = "delete from tbl_member where id = ?";
		try {
			// 문장 생성
			pstmt = conn.prepareStatement(sql);
			int idx = 1;
			pstmt.setString(idx++, vo.getId());
			// 문장 처리
			result = pstmt.executeUpdate();
			
			close();
		} catch (SQLException e) {
			e.printStackTrace();
		}return result;
	}
	
	public int update(Member vo) {
		conn = DBConn.getConnection();
		int result = 0;
		// 처리할 sql구문
		String sql = "update tbl_member set name = ?, email = ?, addr = ?, addrDetail = ? where id = ?";
		try {
			// 문장 생성
			pstmt = conn.prepareStatement(sql);
			int idx = 1;
			pstmt.setString(idx++, vo.getName());
			pstmt.setString(idx++, vo.getEmail());
			pstmt.setString(idx++, vo.getAddr());
			pstmt.setString(idx++, vo.getAddrDetail());
			pstmt.setString(idx++, vo.getId());
			// 문장 처리
			result = pstmt.executeUpdate();
			
			close();
		} catch (SQLException e) {
			e.printStackTrace();
		}return result;
	}

	public Member selectOne(String id) {
		conn = DBConn.getConnection();
		// 반환 예정 객체
		Member vo = null;
		// 처리할 sql구문
		String sql = "select * from tbl_member where id = ?";
		try {
			// 문장생성
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			// 결과집합 반환
			rs = pstmt.executeQuery( );

			// 결과집합 >> 자바 객체
			if (rs.next()) {
				int idx = 1;

				// 객체생성 후 값 바인딩
				vo = new Member(
						rs.getString(idx++), 
						rs.getString(idx++), 
						rs.getString(idx++), 
						rs.getDate(idx++),
						rs.getString(idx++),
						rs.getString(idx++),
						rs.getString(idx++)
						);
			}
			close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		// 결과 반환
		return vo;

	}

	
	
	public List<Member> selectList() {
		conn = DBConn.getConnection();
		// 반환 예정 객체
		List<Member> vo = new ArrayList<>();
		// 처리할 sql구문
		String sql = "select * from tbl_member";
		try {
			// 문장생성
			pstmt = conn.prepareStatement(sql);
			// 결과집합 반환
			rs = pstmt.executeQuery( );
			
			// 결과집합 >> 자바 객체
			while (rs.next()) {
				int idx = 1;
				
				// 객체생성 후 값 바인딩
				Member member = new Member(
						rs.getString(idx++), 
						rs.getString(idx++), 
						rs.getString(idx++), 
						rs.getDate(idx++),
						rs.getString(idx++),
						rs.getString(idx++),
						rs.getString(idx++)
						);
				vo.add(member);
			}
			close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		// 결과 반환
		return vo;
		
	}
	
	
	
	// 자원 반환
	public void close() {
		conn = DBConn.getConnection();
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
			}
		}
		if (pstmt != null) {
			try {
				pstmt.close();
			} catch (SQLException e) {
			}

		}
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
			}

		}

	}

	public static void main(String[] args) {
		MemberDao dao = new MemberDao();
		dao.selectList().forEach(member -> {
			member.setPw(BCrypt.hashpw("1234", BCrypt.gensalt()));
			dao.updatePw(member);
		});
	}
}

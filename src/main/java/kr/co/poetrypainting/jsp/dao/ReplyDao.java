package kr.co.poetrypainting.jsp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import kr.co.poetrypainting.jsp.domain.Board;
import kr.co.poetrypainting.jsp.domain.Criteria;
import kr.co.poetrypainting.jsp.domain.Member;
import kr.co.poetrypainting.jsp.domain.Reply;
import kr.co.poetrypainting.jsp.util.DBConn;

public class ReplyDao {
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;

	public int insert(Reply reply) {
		conn = DBConn.getConnection();
		int result = 0;
		// 처리할 sql구문
		String sql = "insert into tbl_reply (content, writer, bno) values (?, ?, ?)";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, reply.getContent());
			pstmt.setString(2, reply.getWriter());
			pstmt.setLong(3, reply.getBno());
			// 문장 생성

			// 문장 처리
			result = pstmt.executeUpdate();
			close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	

	public List<Reply> selectList(Long bno) {
		conn = DBConn.getConnection();
		// 반환 예정 객체
		List<Reply> replies = new ArrayList<Reply>();
		// 처리할 sql구문
		String sql = "";
		sql += "select * from tbl_reply where bno = ?";
		//검색
		
		try {
			// 문장생성
			pstmt = conn.prepareStatement(sql);
			int idx = 1;
			pstmt.setLong(idx++, bno);
			// 결과집합 반환
			rs = pstmt.executeQuery();

			// 결과집합 >> 자바 객체
			while (rs.next()) {
				idx = 1;

				// 객체생성 후 값 바인딩
				Reply reply = new Reply(
						rs.getLong(idx++),
						rs.getString(idx++), 
						rs.getDate(idx++), 
						rs.getString(idx++), 
						rs.getLong(idx++));
					
				replies.add(reply);
			}
			close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		// 결과 반환
		return replies;

	}
	
	public void updateWriterToNull(String id) {
		conn = DBConn.getConnection();
		// 처리할 sql구문
		String sql = "update tbl_reply set\r\n"
				+ "writer = NULL\r\n"
				+ "where writer = ?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			// 문장 생성
			
			// 문장 처리
			pstmt.executeUpdate();
			close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	public Reply selectOne(Long rno) {
		conn = DBConn.getConnection();
		// 반환 예정 객체
		Reply reply = null;
		// 처리할 sql구문
		String sql = "";
		sql += "select * from tbl_reply where rno = ?";
		//검색
		
		try {
			// 문장생성
			pstmt = conn.prepareStatement(sql);
			int idx = 1;
			pstmt.setLong(idx++, rno);
			// 결과집합 반환
			rs = pstmt.executeQuery();
			
			// 결과집합 >> 자바 객체
			while (rs.next()) {
				idx = 1;
				
				// 객체생성 후 값 바인딩
				reply = new Reply(
						rs.getLong(idx++),
						rs.getString(idx++), 
						rs.getDate(idx++), 
						rs.getString(idx++), 
						rs.getLong(idx++));
				
			}
			close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		// 결과 반환
		return reply;
		
	}

	public int delete(Long rno) {
		int ret = 0;
		conn = DBConn.getConnection();
		
		// 처리할 sql구문
		String sql = "delete from tbl_reply where rno = ?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, rno);
			// 문장 생성

			// 문장 처리
			ret = pstmt.executeUpdate();
			close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ret;
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
	private String getSearchSqlBy(Criteria cri) {
		String sql ="";
		if(cri.getType() != null && cri.getKeyword() != null && String.join("", cri.getType()).length() > 0) {
			sql += " and (";
			List<String> list = Arrays.asList(cri.getType());
			List<String> typeList = list.stream().map(s -> " " + s + " like '%" + cri.getKeyword() + "%' ").collect(Collectors.toList());
			sql += String.join(" or ", typeList);
			sql += ")";
		}
		return sql;
	}

	public static void main(String[] args) {
//		new replyDao().selectList(1).forEach(System.out::println);
//		replyDao dao = new replyDao();
//		System.out.println(dao.selectListCount(1));
//		reply reply = dao.selectOne(5L);
//		System.out.println(reply);
//		dao.delete(5L);
//		System.out.println(dao.selectOne(5L));
	}


	public void deleteByBno(Long bno) {
		// TODO Auto-generated method stub
		conn = DBConn.getConnection();
		
		// 처리할 sql구문
		String sql = "delete from tbl_reply where bno = ?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, bno);
			// 문장 생성

			// 문장 처리
			pstmt.executeUpdate();
			close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}


}
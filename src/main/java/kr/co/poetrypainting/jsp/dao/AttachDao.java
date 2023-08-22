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

import kr.co.poetrypainting.jsp.domain.Attach;
import kr.co.poetrypainting.jsp.domain.Criteria;
import kr.co.poetrypainting.jsp.domain.Member;
import kr.co.poetrypainting.jsp.util.DBConn;

public class AttachDao {
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;

	public void insert(Attach attach) {
		conn = DBConn.getConnection();
		// 처리할 sql구문
		String sql = "insert into tbl_attach values (?, ?, ?, ?, ?)";
		try {
			// 문장 생성
			pstmt = conn.prepareStatement(sql);
			int idx = 1;
			pstmt.setString(idx++, attach.getUuid());
			pstmt.setString(idx++, attach.getOrigin());
			pstmt.setBoolean(idx++, attach.isImage());
			pstmt.setString(idx++, attach.getPath());
			pstmt.setLong(idx++, attach.getBno());

			// 문장 처리
			pstmt.executeUpdate();
			close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public Attach selectOne(String uuid) {
		conn = DBConn.getConnection();
		// 반환 예정 객체
		Attach attach = null;
		// 처리할 sql구문
		String sql = "select * from tbl_attach where uuid = ?";
		try {
			// 문장생성
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, uuid);

			// 결과집합 반환
			rs = pstmt.executeQuery();

			// 결과집합 >> 자바 객체
			if (rs.next()) {
				int idx = 1;

				// 객체생성 후 값 바인딩
				attach = new Attach(rs.getString(idx++), rs.getString(idx++), rs.getBoolean(idx++), rs.getString(idx++),
						rs.getLong(idx++));
			}
			close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		// 결과 반환
		return attach;

	}

	public List<Attach> selectList(Long bno) {
		conn = DBConn.getConnection();
		// 반환 예정 객체
		List<Attach> attachs = new ArrayList<Attach>();
		// 처리할 sql구문
		String sql = "select * from tbl_attach where bno = ?;";
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
				Attach attach = new Attach(
						rs.getString(idx++), 
						rs.getString(idx++), 
						rs.getBoolean(idx++), 
						rs.getString(idx++),
						rs.getLong(idx++));
				attachs.add(attach);
			}
			close();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
	// 결과 반환
		return attachs;

	}


	public int selectListCount(Criteria cri) {
		conn = DBConn.getConnection();
		// 반환 예정 객체
		int count = 0;
		// 처리할 sql구문
		String sql = "select count(*) from tbl_Attach where category = ?";
		sql += getSearchSqlBy(cri);
		try {
			// 문장생성
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, cri.getCategory());

			// 결과집합 반환
			rs = pstmt.executeQuery();

			// 결과집합 >> 자바 객체
			while (rs.next()) {
				int idx = 1;

				// 객체생성 후 값 바인딩
				count = rs.getInt(1);
			}
			close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		// 결과 반환
		return count;

	}

	public void delete(Long bno) {
		conn = DBConn.getConnection();

		// 처리할 sql구문
		String sql = "delete from tbl_attach where bno = ?";
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
		String sql = "";
		if (cri.getType() != null && cri.getKeyword() != null && String.join("", cri.getType()).length() > 0) {
			sql += " and (";
			List<String> list = Arrays.asList(cri.getType());
			List<String> typeList = list.stream().map(s -> " " + s + " like '%" + cri.getKeyword() + "%' ")
					.collect(Collectors.toList());
			sql += String.join(" or ", typeList);
			sql += ")";
		}
		return sql;
	}

	public static void main(String[] args) {
//		new AttachDao().selectList(1).forEach(System.out::println);
//		AttachDao dao = new AttachDao();
//		System.out.println(dao.selectListCount(1));
//		Attach Attach = dao.selectOne(5L);
//		System.out.println(Attach);
//		dao.delete(5L);
//		System.out.println(dao.selectOne(5L));
	}

}
package com.exam.myweb.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.exam.myweb.model.MemberVO;

public class MemberDAO {
	static DataSource dataSource;

	static {
		try {
			Context context = new InitialContext();
			dataSource = (DataSource) context.lookup("java:comp/env/jdbc/Oracle");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	public void insert(MemberVO member) {
		Connection con = null;
		try {
			con = dataSource.getConnection();
			String sql = "INSERT INTO member (userid, name, password, email, address) " + "VALUES (?, ?, ?, ?, ?)";
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setString(1, member.getUserid());
			stmt.setString(2, member.getName());
			stmt.setString(3, member.getPassword());
			stmt.setString(4, member.getEmail());
			stmt.setString(5, member.getAddress());
			int rowCount = stmt.executeUpdate();
			System.out.println(rowCount + "개 행이 변경되었습니다.");
			if (rowCount <= 0) {
				throw new SQLException("저장된 행이 없습니다.");
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			throw new RuntimeException(e); // 예외의 원인이 insert문에 없다면 insert문을 호출하는 곳에 다시 알려줘야 하기 때문에
		} finally {
			closeConnection(con);
		}
	}

	public String getPassword(String userid) {
		String pw = "";
		Connection con = null;
		try {
			con = dataSource.getConnection();
			String sql = "SELECT password FROM member WHERE userid=?";
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setString(1, userid);

			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				pw = rs.getString("password");
			} else {
				throw new SQLException("아이디가 없습니다");
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			throw new RuntimeException(e);
		} finally {
			closeConnection(con);
		}

		return pw;
	}

	private void closeConnection(Connection con) {
		if (con != null)
			try {
				con.close();
			} catch (Exception e) {
			}
	}

	public MemberVO getMember(String userid) {
		MemberVO member = new MemberVO();
		Connection conn = null;
		try {
			conn = dataSource.getConnection();
			String sql = "SELECT userid, name, password, email, address " + "FROM member WHERE userid=?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, userid);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				member.setUserid(rs.getString("userid"));
				member.setName(rs.getString("name"));
				member.setPassword(rs.getString("password"));
				member.setEmail(rs.getString("email"));
				member.setAddress(rs.getString("address"));
			} else {
				throw new RuntimeException("사용자가 없습니다.");
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw new RuntimeException(e);
		} finally {
			closeConnection(conn);
		}
		return member;
	}

	public void updateMember(MemberVO member) {
		Connection con = null;
		try {
			con = dataSource.getConnection();
			String sql = "UPDATE member SET name=?,password=?,email=?,address=? " + "WHERE userid=?";
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setString(1, member.getName());
			stmt.setString(2, member.getPassword());
			stmt.setString(3, member.getEmail());
			stmt.setString(4, member.getAddress());
			stmt.setString(5, member.getUserid());
			int rowCount = stmt.executeUpdate();
			System.out.println(rowCount + "개 행이 변경되었습니다.");
			if (rowCount <= 0) {
				throw new RuntimeException("변경된 행이 없습니다.");
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			throw new RuntimeException(e); // 예외의 원인이 insert문에 없다면 insert문을 호출하는 곳에 다시 알려줘야 하기 때문에
		} finally {
			closeConnection(con);
		}
	}

	public void deleteMember(String userid) {
		Connection conn = null;
		try {
			conn = dataSource.getConnection();
			String sql = "DELETE FROM member WHERE userid=?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, userid);
			int rs = stmt.executeUpdate();
			System.out.println("delete 결과 rs: " + rs);
			if (rs < 1) {
				throw new RuntimeException("삭제 실패");
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			throw new RuntimeException(e);
		} finally {
			closeConnection(conn);
		}
	}
}

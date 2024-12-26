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
}

package com.exam.myweb.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.exam.myweb.dao.MemberDAO;
import com.exam.myweb.model.MemberVO;


@WebServlet("/member/Member.do")
public class MemberServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	MemberDAO dao;
	
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		dao = new MemberDAO();
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String userid = request.getParameter("userid");
		String name = request.getParameter("name");
		String password = request.getParameter("password");
		String email = request.getParameter("email");
		String address = request.getParameter("address");
		
		MemberVO mvo = new MemberVO(userid, name, password, email, address);
		
 		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		try {
			dao.insert(mvo);
			out.println("저장되었습니다.");
		}catch(Exception e) {
			out.println("에러! : "+e.getMessage());
		}
	}

	

}

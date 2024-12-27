package com.exam.myweb.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.exam.myweb.dao.MemberDAO;

@WebServlet("/Login.do")
public class loginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
 
	MemberDAO dao = new MemberDAO();

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("servlet동작");
		String userid = request.getParameter("userid");
		String password = request.getParameter("password");
		
		//서블릿에서는 session을 만들어야됨 -> session(true)하면 항상 새로운세션 만든다.
		HttpSession session = request.getSession();
		String view = "loginok.jsp";
		try {
			String dbpw = dao.getPassword(userid);
			if(dbpw.contentEquals(password)) {
				session.setAttribute("userid", userid);
				request.setAttribute("message", userid+"님 환영합니다.");
				response.sendRedirect("loginform.jsp");
				return;
			}else {
				throw new RuntimeException("비밀번호가 다릅니다.");
//				session.invalidate(); //비번 안맞으면 세션 무효화
//				request.setAttribute("message", "비밀번호가 다릅니다.");
//				view = "loginerror.jsp";
			}
		}catch(RuntimeException e) {
			System.out.println(e.getMessage());
			session.invalidate();
			request.setAttribute("message", e.getMessage());
			view = "loginerror.jsp";
		}
		
		RequestDispatcher rdisp = request.getRequestDispatcher(view);
		rdisp.forward(request, response);
		
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String action = req.getParameter("action");
		if("loginok".equals(action)) {
			//로그인한 사용자
		}else {
			//로그아웃
			req.getSession().invalidate();
		}
		RequestDispatcher disp = req.getRequestDispatcher("/loginform.jsp");
		disp.forward(req, resp);
		
	}
	
	

}

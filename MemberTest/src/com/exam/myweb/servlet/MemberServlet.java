package com.exam.myweb.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String action = req.getParameter("action");
		HttpSession session = req.getSession();
		String view = "/index.jsp";
		if ("insert".equals(action) || action == null) {
			req.setAttribute("action", "insert");
			view = "/member/memberform.jsp";
		} else if ("select".equals(action)) {
			String userid = (String) session.getAttribute("userid");
			if (userid != null) {
				MemberVO member = dao.getMember(userid);
				req.setAttribute("member", member);
				req.setAttribute("action", "update");
				view = "/member/memberform.jsp";
			} else {
				req.setAttribute("message", "로그인하지 않은 사용자입니다.");
				view = "/loginform.jsp";
			}
		} else if("delete".equals(action)) {
			String userid = (String) session.getAttribute("userid");
			System.out.println(userid);
			session.setAttribute("userid", userid);
			req.setAttribute("action", "delete");
			view = "/member/deleteform.jsp";
		}
		RequestDispatcher disp = req.getRequestDispatcher(view);
		disp.forward(req, resp);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String action = request.getParameter("action");
		if("insert".equals(action)) {
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
				response.sendRedirect("/Login.do");
				return;
			} catch (Exception e) {
				throw new RuntimeException("회원가입 실패");
			}
		}else if("update".equals(action)) {
			String userid = (String) request.getSession().getAttribute("userid");
			String name = request.getParameter("name");
			String password = request.getParameter("password");
			String email = request.getParameter("email");
			String address = request.getParameter("address");	
			if(userid==null) { //session만료 되서 로그아웃됬을경우
				throw new RuntimeException("로그인한 사용자가 없습니다.");
			}else {
				MemberVO member = new MemberVO(userid, name, password, email, address);
				dao.updateMember(member);
				response.sendRedirect("/member/Member.do?action=select");
			}
		}else if("delete".equals(action)) {
			String userid = (String) request.getSession().getAttribute("userid");
			String inputPW = request.getParameter("password");
			if(dao.getPassword(userid).equals(inputPW)) {
				//delete
				dao.deleteMember(userid);
				response.sendRedirect("/Login.do");
				return;
			}else {
				throw new RuntimeException("비밀번호가 일치하지 않습니다.");
			}
		}

	}

}

package com.exam.myweb.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AuthFilter implements Filter {
	
	String encoding = "utf-8";
	
	public void init(FilterConfig fConfig) throws ServletException {
		// 초기화 파라미터 얻어올때 주로 사용
		encoding= fConfig.getInitParameter("encoding");
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		// servlet 실행되기 전
		request.setCharacterEncoding(encoding);
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		HttpSession session = req.getSession();
		String userid = (String) session.getAttribute("userid");
		
		if(userid==null) {
			resp.sendRedirect("/Login.do");
			return;
		}
		
		CharUpperWrapper requestWrapper = new CharUpperWrapper(req);
		chain.doFilter(requestWrapper, response);
//		chain.doFilter(request, response);
		// servlet 실행된 후
	}

	public void destroy() {
		// TODO Auto-generated method stub
	}

}

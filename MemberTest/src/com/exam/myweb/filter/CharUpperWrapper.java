package com.exam.myweb.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public class CharUpperWrapper extends HttpServletRequestWrapper {
	
	HttpServletRequest request;
	
	public CharUpperWrapper(HttpServletRequest request) {
		super(request);
		this.request = request;
	}
	
	public String getParameter(String name) {
		String value = request.getParameter(name);
		return value.toUpperCase();
	}
}

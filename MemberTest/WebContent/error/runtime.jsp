<%@page import="java.io.PrintWriter"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    isErrorPage="true"%>
   
<%
	response.setStatus(200); //지금 이 페이지는 정상이라고 인식시킨다음에, 내가 설정한 에러페이지를 띄운다.
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<h1>런타임 예외발생</h2>
<h2><%=exception.getMessage() %></h2>
<!-- <pre>
<%
exception.printStackTrace(new java.io.PrintWriter(out));
%>
</pre> --!>
<h2>${exception.message}</h2><!--EL로는 출력안됨  -->
</body>
</html>
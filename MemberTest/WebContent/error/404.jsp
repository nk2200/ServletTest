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
<h2>페이지를 찾을 수 없습니다.</h2>
<h3>주소를 확인하세요.</h3>
</body>
</html>
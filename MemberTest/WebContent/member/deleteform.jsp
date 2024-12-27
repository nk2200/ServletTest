<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<h2>회원 탈퇴</h2>
탈퇴 아이디 : ${userid }<br>
<form method="post" action="/member/Member.do">
비밀번호를 입력하세요 : <input type="password" name="password"><br>
<input type="hidden" name="action" value=${action }>
<input type="submit" value="삭제">
</form>
</body>
</html>
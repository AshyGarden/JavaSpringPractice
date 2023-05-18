<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h2>request컨트롤러를 이용한 요청처리 작업중</h2>
	<form action="/basic/request/basic01">
	<input type="submit" value="get요청!">
	</form>
	<br>
	<form action="/basic/request/basic01" method="post">
	<input type="submit" value="post요청!">
	</form>
</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
<head>
	<title>Home</title>
</head>
<body>
<h1>
	student 등록  
</h1>
<form name="create" action="insertok" method="post">
	<label>이름</label>
	<input type="text" name="name" />
	<br>
	<label>이메일</label>
	<input type="text" name="email" />
	<br>
	<label>수강과목</label>
	<input type="text" name="course" />
	<br>
	<button type="reset">취소</button>
	<button type="submit">전송</button>
</form>
</body>
</html>

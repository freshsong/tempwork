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
<form name="create" action="updateok" method="post">
	<label>이름</label>
	<input type="text" name="name" value="${dto.stu_name }" />
	<br>
	<label>이메일</label>
	<input type="text" name="email" value="${dto.stu_email }"/>
	<br>
	<label>수강과목</label>
	<input type="text" name="course" value="${dto.stu_course }"/>
	<br>
	<input type="hidden" name="id" value="${dto.stu_id }" />
	<button type="reset">취소</button>
	<button type="submit">전송</button>
</form>
</body>
</html>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
<head>
	<title>Home</title>
	<style>
		.container{
			width: 1000px;
			margin: auto;
			padding: 50px;
		}
		th,td,.text-center{
			text-align: center;
		}	
	</style>
</head>
<body>
<div class="container">
<h1 class="text-center">
	Hello Student!  
</h1>
<p> ${msg } </p>
<p> The time on the server is ${serverTime }.</p>
<P>  <a href="insert">새로운 학생 등록</a> </P>
<table border="1">
	<tr>
		<th>번호</th>
		<th>이름</th>
		<th>이메일</th>
		<th>수강과목</th>
		<!-- update -->
		<th>수정, 삭제</th>
	</tr>
	
	<!-- 루프돌릴때 아래처럼씀, var변수, items받은값 -->
	<c:forEach var="st" items="${listStudent }">
		<tr>
			<td>${st.stu_id }</td>
			<td><a href="content?id=${st.stu_id}">${st.stu_name }</a></td>
			<td>${st.stu_email }</td>
			<td>${st.stu_course }</td>
			<!-- update -->		
			<td>
				<a href="update?id=${st.stu_id }">수정</a>
				<a href="delete?id=${st.stu_id }" class="del">삭제</a>
			</td>
		</tr>
	</c:forEach>
</table>
</div>
<script>
	//jquery 안쓰고 하려면 이렇게해야
	const deltarget = document.querySelectorAll(".del");
	deltarget.forEach((target, index) => {
		target.addEventListener("click", (e)=>{
			e.preventDefault(); //삭제막는구간
			const hrefVal = target.getAttribute("href");
			if(confirm("정말로 삭제하시겠습니까?")){
				location.href=hrefVal;
			}
		});
	});
	  
</script>
</body>
</html>

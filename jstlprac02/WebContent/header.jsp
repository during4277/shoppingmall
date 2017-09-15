<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
<script>
console.log("${login==0}");
console.log("${login==1}");
</script>
</head>
<body>
<c:if test="${login==0}">
<form name="loginForm" action="access" method="post" id="frm">
	아이디:<input type="text" name="id" /><br/>
	비밀번호:<input type="password" name="pw" /><br/>
	<!-- <a href="javascript:Aj('header','#header')">로그인</a> -->
	<!-- <input type="button" onclick="javascript:Aj('header','#header')" value="로그인" /> -->
		${msgAccess}
	<button>로그인</button>
	<a href="joinForm">회원가입</a>
</form>

</c:if>
<c:if test="${login==1}">
<a href="logout">로그아웃</a>
<!-- <input type="button" onclick="javascript:Aj('header','#header')" value="로그아웃" /> -->
<a href="proUpForm">상품 등록</a>
<a href="viewBasket">장바구니 보기</a>
</c:if>
<script>
/* $(function(){
$('#btn').click(function(){
	$.ajax({
		url:"header.jsp",
		type: "get",
		success: function(html){
			$(#).html(html);
		},
		error: function(error){
			console.log(error);
		}	
	});
})
}) */
</script>
</body>
</html>
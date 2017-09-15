<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script>
</script>
<style>
	table, th, td{
		/* text-align: center; */
		border: 1px solid black;
		border-collapse: collapse;
		width: 500px;
		height: 70px;
	}
	th, td{
		text-align: center;
	}
	/* h1{
		text-align: center;
	} */
</style>
</head>
<body>
	<h1>장바구니페이지</h1>
${basketList}
<form action="toOrder" method="post">
	<input type="submit" value="전체 주문하기" />
</form>
</body>
</html>
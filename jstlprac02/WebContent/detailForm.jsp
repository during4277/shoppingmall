<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
<script>
</script>
<style>
	a{
		color:black;
		text-decoration: none;
		
	}
</style>
</head>
<body>
<h1>상세 페이지</h1>
<img src="upload/${p_filename}"/>
<h2>${htmlStr}</h2>
	<div id="area">
	</div>
<form action="basket" method="post">
	 구매수량 : <input type="text" id="count" name="count"/><br/>
	<hr/> 
	<button onclick="cal()">장바구니 담기</button>
	<!-- onclick="AJAX('basket','#msg')" -->
</form>
<span id="msg">${cntMsg}</span>
<script>
/* AJAX("basketBtn","#area"); */
/*  function cal(){
	alert('안녕하세요');
} */
 
 function AJAX(url, position){
		$.ajax({
			url:url,
			type:"get",
			success: function(html){
				$(position).html(html);
			},
			error: function(error){
				console.log(error);
			}
			
		});
	} 
</script>
</body>
</html>
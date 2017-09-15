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
</head>
<body>
<h2>${htmlStr}</h2>
<form action="basket" method="post">
구매수량 : <input type="text" id="count" name="count"/><br/>
	<hr/>
<button>장바구니 담기</button>
<!-- <a href="javascript:AJAX('basketBtn','#area')"> -->
<!-- formaction="javascript:AJAX('basketBtn','#area')" -->
</form>
<span id="msg">${cntMsg}</span>
<!-- <script>
 AJAX("basket","#area");
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
</script> -->
</body>
</html>
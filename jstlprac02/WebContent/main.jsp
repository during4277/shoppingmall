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
	body{
		width:800px;
		height:600px;
		border:1px solid black;
		float:left;
	}	
	#header{
		width:800px;
		height:100px;
		border:1px solid black;
	}
	#middle{
		width:800px;
		height:400px;
		/* position: relative;
		overflow:hidden; */
		
		/* float:left; */
		display: inline-flex;
		border:1px solid black;
	}
	#menu{
		/* position: absolute; */
		float:left;
		width:100px;
		border:1px solid black;
	}
	#main{
		/* position: absolute; */
		float:left;
		width:500px;
	}
	#bottom{
		width:800px;
		height:100px;
		border:1px solid black;
		text-align: center;
	}
	
</style>
</head>
<body>
	<div id="total">
		<div id="header">
			<%-- <jsp:include page="login.jsp"></jsp:include> --%>
		</div>
		<div id="middle">
			<div id="menu">
				<%-- <jsp:include page="left.jsp"></jsp:include> --%>
			</div>
			<div id="main">
				<%-- <jsp:include page="${page}"></jsp:include> --%>
			</div>
		</div>
		<div id="bottom">
			<%-- <jsp:include page="bottom.jsp"></jsp:include> --%>
		</div>
	</div>
<script>
//서블릿에 4개를 만든다
Aj("header",'#header');
Aj("menu",'#menu');
Aj("${page}",'#main');//newItem, bestItem 페이지를 반환
Aj("bottom",'#bottom');
/* Aj("newitem",'#main');
Aj("bestitem",'#main');
Aj("outwear",'#main'); */

function Aj(url, position){
	console.log('확인');
	$.ajax({
		url: url,
		type: "get",
		success: function(html){
			$(position).html(html);
		},
		error: function(error){
			console.log(error);
		}
	});//ajax End
}
</script>
</body>
</html>
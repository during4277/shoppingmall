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
	#group{
		width:100%; overflow:hidden;
	}
	#product{
		width:30%; float:left; margin:5px;
	}
	#detail{
		display:none; width:30%; border:solid black 2px;
	}	
	#detail.open{display:block; color:red}
</style>
</head>
<body>
<h1>${kind} 상품리스트</h1>
<form name="detailForm">
	<div id="detail">상세내용</div>
	<input type="hidden" name="code" value="" />
</form>
${pList}
</body>
<script>
		function detail(code){
			document.detailForm.action = "ajaxDetail";
			document.detailForm.method = "post";
			document.detailForm.code.value=code;
			document.detailForm.submit();
		}
	 /* function detail(code){
		$('#detail').addClass('open'); //함수를 호출하는 순간 class를 open으로 바꿔줌
		$('#product').show();
		$('#product').hide();
		$.ajax({
			type:'get',
			url:'ajaxDetail',
			data:{p_code:code},
			success:function(data){
				alert(data);
				$('#detail').html(data);
				},
			error:function(error){
				console.log(error);		
				}
		});//ajax end
	}//detail end  */
</script>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script>
</script>
</head>
<body>
<h3>상품 등록 페이지</h3>
<form action="insertProduct" method="post"
enctype="multipart/form-data"><!-- enctype을 작성해야 이미지의 binary까지 서버에 저장된다.로 넘어간다. request.getParameter로 빼지 못한다  -->
<table border="1">
	<tr>
		<td colspan="2" align="center"><h3>상품 업로드</h3></td>
	</tr>
	<tr>
		<td colspan="2">
			<input type="radio" name="p_kind" value="new"/>신상품
			<input type="radio" name="p_kind" value="best"/>인기상품 
		</td>
	</tr>
	<tr>
		<td>상품명</td><td><input type="text" name="p_name" /></td>
	</tr>
	<tr>
		<td>가격</td><td><input type="text" name="p_price" /></td>
	</tr>
	<tr>
		<td>재고량</td><td><input type="text" name="p_qty" /></td>
	</tr>
	<tr>
		<td>설명</td><td><input type="textarea" name="p_content" rows="10" cols="70" /></td>
	</tr>
	<tr>
		<td>이미지</td><td><input type="file" name="p_fileName" /></td>
	</tr>
	<tr align="center">
		<td colspan="2"><button>상품등록</button></td>
	</tr>
</table>
</form>
</body>
</html>
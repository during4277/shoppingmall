<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script>
function toMain(){
	alert("test");
	//history.back(); //브라우져 캐시 파일 로딩
	location.href="./index";//서버에서 새롭게 로딩
}
</script>
<style>
#txt{
	color:red;
}
</style>
</head>
<body>
<h1>회원가입 페이지</h1>
<form name="joinform" method="post" action="join">
	아이디:<input type="text" name="id"/>
	<input type="button" onclick="confirm()" value="중복체크"/><span id="txt">${txt}</span>
	<br/>
	비밀번호:<input type="password" name="pw1"/><br/>
	비밀번호확인:<input type="password" name="pw2"/><br/>
	이름:<input type="text" name="name"/><br/>
	닉네임:<input type="text" name="nickname"/><br/>
	<input type="submit" value="회원가입"/>
	<input type="reset"  value="취소"/>
	<input type="button" value="돌아가기"
	  onclick="toMain()" />
 </form>
 <script>
 	function confirm(){
 		alert('확인2');
 		document.joinform.action="conFirm"
 		document.joinform.submit()
 	}
 	
 </script>
</body>
</html>
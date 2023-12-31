<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@ taglib prefix ="c" uri="http://java.sun.com/jsp/jstl/core" %>
	<%@ taglib prefix ="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="cpath" value="${pageContext.request.contextPath}" />	
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.4/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
</head>
<body>
	<div class="container">
		<h2>Spring MVC07</h2>
		<div class="panel panel-default">
			<div class="panel-heading">Board</div>
			<div class="panel-body">
				<form action="${cpath}/board/reply" method="post">
					<input type="hidden" name="memID" value="${mvo.memID}">
					<!-- 부모글의 게시글 번호 -->
					<input type="hidden" name="idx" value="${vo.idx}">
						
					<div class="form-group">
						<label>제목</label>
						<input value="<c:out value='${vo.title}'/>" type="text" name="title" class="form-control">
					</div>
					
					<div class="form-group">
						<label>답변</label>
						<textarea rows="10" cols="" name="content" class="form-control"></textarea>
					</div>
					
					<div class="form-group">
						<label>작성자</label>
						<input value="${mvo.memName}" readonly="readonly" type="text" name="writer" class="form-control">
					</div>
					<button type="submit" class="btn btn-default btn-sm">등록</button>
					<button type="reset"  class="btn btn-default btn-sm">취소</button>
					<button data-btn="list"  type="button" class="btn btn-default btn-sm">목록</button>
					
				</form>
				
				<form id="frm" method="get" action="">
					<input id="idx" type="hidden" name="idx" value="${vo.idx}">
				</form>
				
				
			</div>
			<div class="panel-footer">스프링게시판-뇽뇽이</div>
		</div>
	</div>

	<script type="text/javascript">
	$(document).ready(function(){
		$("button").on("click",function(e){ 
			var formData=$("#frm");
			var btn =$(this).data("btn");
			
			//버튼이 reply이면 action을 아래 경로로 바꿔줌.
			if(btn=="list"){
				formData.attr("action","${cpath}/board/list");
				formData.find("#idx").remove();
				//list는 idx가 필요 없으니까 찾아서 없애줌.
			}
			formData.submit();
		}); //버튼 눌렀을 떄
		
	});
	
	</script>
</body>
</html>
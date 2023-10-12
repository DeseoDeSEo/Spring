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
		<h2>Spring MVC08</h2>
		<div class="panel panel-default">
			<div class="panel-heading">Board</div>
			<div class="panel-body">
			<!--이건 수정해서 post방식으로 보내는 거임.   -->
				<form action="${cpath}/board/modify" method="post">   
					<table class="table table-bordered table-hover">
							<tr>
								<td>번호</td>
								<td><input readonly="readonly" value="${vo.idx}" name="idx" type="text" class="form-control"></td>
							</tr>
							<tr>
								<td>제목</td>
								<td><input value="<c:out value='${vo.title}'/>" name="title" type="text" class="form-control"></td>
							</tr>
							<tr>
								<td>내용</td>
								<td>
									<textarea class="form-control" name="content" rows="10" cols=""><c:out value="${vo.content}"/></textarea>
								</td>
							</tr>
							<tr>
								<td>작성자</td>
								<td><input readonly="readonly" value="${vo.writer}" name="writer" type="text" class="form-control"></td>
							</tr>
							
							<tr>
								<td colspan="2" style="text-align:center;" >
									<c:if test="${not empty mvo && mvo.memID eq vo.memID }">
										<button type="submit" class="btn btn-sm btn-primary">수정완료</button>
										<button data-btn="remove" type="button" class="btn btn-sm btn-success">삭제</button>
									</c:if>
									<c:if test="${empty mvo or mvo.memID ne vo.memID }">
										<button disabled="disabled" type="submit" class="btn btn-sm btn-primary">수정완료</button>
										<button disabled="disabled" type="button" class="btn btn-sm btn-success">삭제</button>
									</c:if>
										<button data-btn="list" type="button"  class="btn btn-sm btn-warning">목록</button>
										<!-- cpath는 절대경로임. -->
								</td>
							</tr>					
					</table>
				</form>
				
				<!--10/11 button에 location href없앰. -->
				<form id="frm" method="get" action="">
					<input id="idx" type="hidden" name="idx" value="${vo.idx}">
				</form>
				
				
			</div>
			<div class="panel-footer">스프링게시판-뇽뇽이</div>
		</div>
	</div>

	<script type="text/javascript">
	//10.11 링크처리(가독성, 보안(url노출 안됨.),유지보수 편리)//e:클릭했을 때, 요소를 감지하겠다.
	$(document).ready(function(){
		$("button").on("click",function(e){ 
			var formData=$("#frm");
			var btn =$(this).data("btn");
			
			//버튼이 reply이면 action을 아래 경로로 바꿔줌.
			if(btn=="list"){
				formData.attr("action","${cpath}/board/list");
				formData.find("#idx").remove();
				//list는 idx가 필요 없으니까 찾아서 없애줌.
			}else if(btn=="remove"){
				formData.attr("action","${cpath}/board/remove");

			}
			
			formData.submit();
		}); //버튼 눌렀을 떄
		
	});
	
	
	</script>


</body>
</html>
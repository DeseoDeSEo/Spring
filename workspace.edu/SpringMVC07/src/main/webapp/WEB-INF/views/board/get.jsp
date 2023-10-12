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
				<table class="table table-bordered table-hover">
						<tr>
							<td>번호</td>
							<td>${vo.idx}</td>
						</tr>
						<tr>
							<td>제목</td>
							<td><c:out value="${vo.title}"/></td>
						</tr>
						<tr>
							<td>내용</td>
							<td>
								<textarea class="form-control" readonly = "readonly" rows="10" cols=""><c:out value="${vo.content}"/></textarea>
							</td>
						</tr>
						<tr>
							<td>작성자</td>
							<td>${vo.writer}</td>
						</tr>
						
						<tr>
							<td colspan="2" style="text-align:center;" >
								<c:if test="${not empty mvo}">   
									<button data-btn="reply" class="btn btn-sm btn-primary">답글</button>
									<button data-btn="modify" class="btn btn-sm btn-success">수정</button>
								</c:if>
								<!--  -->
								<c:if test="${empty mvo}">   
									<button disabled="disabled" class="btn btn-sm btn-primary">답글</button>
									<button disabled="disabled" class="btn btn-sm btn-success">수정</button>
								</c:if>
								
								<button data-btn="list" class="btn btn-sm btn-warning">목록</button>
							</td>
						</tr>
									
				</table>
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
				if(btn=="reply"){
					formData.attr("action","${cpath}/board/reply");
				}else if(btn=="modify"){
					formData.attr("action","${cpath}/board/modify");
				}else if(btn=="list"){
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
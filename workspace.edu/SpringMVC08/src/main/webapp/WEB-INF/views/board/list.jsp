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
		<!-- 10/11 교재 p.302!!!!!!!!!!   -->
		<div class="panel panel-default">
			<div class="panel-heading">
			 
			 <c:if test="${empty mvo}"> 
			 
					<form class="form-inline" action="${cpath}/login/loginProcess" method="post">
						<div class="form-group">
	 	         				<!-- 라벨태그를 사용하면 글자를 클릭하면 활성화됨, -->
							<label for="id">ID: </label>
							<input type="text" class="form-control" id="id" name="memID">
						</div>
						<div class="form-group">
	 	         				<!-- 라벨태그를 사용하면 글자를 클릭하면 활성화됨, -->
							<label for="pwd">Password: </label>
							<input type="password" class="form-control" id="pwd" name="memPwd">
						</div>
						<button type="submit" class="btn btn-default">로그인</button>
					</form>
					
				</c:if>
		    <c:if test="${not empty mvo}">
		    								      	<!-- login: loginController로 감. 그리고 logoutProcess 는 @requestmapping으로 함. -->
		    	<form  class="form-inline" action="${cpath}/login/logoutProcess" method="post">
		    		<div class="form-group">
		    			<lable> ${mvo.memName}님 방문을 환영합니다. </lable>
		    		
		    		</div>
		    		<button type="submit" class="btn btn-default"> 로그아웃 </button>
		    	</form>
		    
			</c:if>
				
			</div>
			<div class="panel-body">
				<table class ="table table-bordered table-hover">
					<thead> <!-- 10/05 그냥 영역을 나누는 태그임. -->
					  	<tr>
					  		<th>번호</th>
					  		<th>제목</th>
					  		<th>작성자</th>
					  		<th>작성일</th>
					  		<th>조회수</th>
					  	</tr>
					</thead>
					<tbody>
					<!-- 10/05 꺼내서 vo라는 이름에 담겠다. -->
						<c:forEach items="${list}" var="vo" varStatus="i">
							<tr>
								<td>${i.count}</td>
								<!-- 댓글 표현 하는 곳 -->
								<!-- 10/11 삭제 글/댓글 표현 (xml에도 update로 변경함.-->
								<td>
								<c:if test="${vo.boardAvailable ==0}">
									<a href="javascript:alert('삭제된 게시글 입니다. ')"> 
									<c:if test="${vo.boardLevel > 0 }">
										<c:forEach begin="0" end="${vo.boardLevel}" step="1">
											<span style="padding-left:15px"></span>
										</c:forEach>
										ㄴ[RE]
									</c:if>
									
									삭제된 게시물 입니다.
									</a>
								</c:if>
								<c:if test="${vo.boardAvailable > 0}">
									<a class="move" href="${vo.idx}"><!-- idx는 게시글번호임 -->
									<c:if test="${vo.boardLevel > 0 }">
										<c:forEach begin="0" end="${vo.boardLevel}" step="1">
											<span style="padding-left:15px"></span>
										</c:forEach>
										ㄴ[RE]
										<!-- el(= ${vo.title} )식을 바로 사용하면 xss에 취약함. -->
									</c:if>
									<c:out value="${vo.title}"/>
									</a>
								</c:if>
								
								</td>
								<td>${vo.writer}</td>
								<td>
									<fmt:formatDate value="${vo.indate}" pattern="yyyy-MM-dd"/>
								</td>
								<td>${vo.count}</td>
							</tr>
						</c:forEach>
						
					</tbody>
					<!-- .. -->
					<c:if test="${not empty mvo}">
						<tr>
							<td colspan="5">
								<button id="regBtn" class="btn btn-xs btn-info pull-right">글쓰기</button>
							</td>
						</tr>
					</c:if>	
					
				</table>
				
				<!-- 10/12 페이징 버튼  -->
				<div style="text-align:center;">
				  <ul class="pagination">
				  <!-- 이전 버튼 처리, true면 사용 가능 -->
				  	<c:if test="${pageMaker.prev}">
				  		<li class="paginate_button previous">
				  			<a href="${pageMaker.startPage-1}"> ◀</a>
				  		</li>
				  	</c:if>
				 	<!-- 페이지 번호 처리 -->
				 	<!-- boardcontroller에서 model로 pageMaker 보냄 -->
				 	
				 	<c:forEach var="pageNum" begin="${pageMaker.startPage}" end="${pageMaker.endPage}">
				 		<c:if test="${pageMaker.cri.page ==pageNum}">
				 			<li class="paginate_button active"><a href="${pageNum}">${pageNum}</a></li>
				 		</c:if>
				 		
				 		<c:if test="${pageMaker.cri.page != pageNum}">
				 			<li class="paginate_button"><a href="${pageNum}">${pageNum}</a></li>
				 		</c:if>
				 	
				 	</c:forEach>
				 	<!-- 다음 버튼  -->
				 	<c:if test="${pageMaker.next}">
				  	  <li class="paginate_button previous">
				  		 <a href="${pageMaker.endPage+1}"> ▶ </a>  <!-- *************1번여기서 값을 가져와서 -->
				  	  </li>
				  	</c:if>
				 	
				  </ul>
				  <form action="${cpath}/board/list" id="pageFrm">
				                                     <!--  value : 초기값 -->
				  	<input type="hidden" id="page" name="page" value="${pageMaker.cri.page}"> <!-- ************2번 여기로 가져옴. 그리고 아래script로 내려감 -->
				  	<input type="hidden" id="perPageNum" name="perPageNum" value="${pageMaker.cri.perPageNum}">
				  	
				  </form>
				  
				  
				</div>			
			</div>
			<div class="panel-footer">스프링게시판-뇽뇽이</div>
		</div>
	</div>

<!-- 모달 창!  -->
	 <div class="modal fade" id="myMessage" role="dialog">
		    <div class="modal-dialog">
		    
		      <!-- Modal content-->
		      <div id="messageType" class="modal-content">
		        <div class="modal-header panel-heading">
		          <button type="button" class="close" data-dismiss="modal">&times;</button>
		          <h4 class="modal-title">Modal Header</h4>
		        </div>
		        <div class="modal-body">
		          
		        </div>
		        <div class="modal-footer">
		          <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
		        </div>
		      </div>
		      
		    </div>
		  </div>




	<script type="text/javascript">
		$(document).ready(function(){
			
			//10/12 페이지 번호 클릭 시 이동하기
			//form태그 정보 가져오기
			var pageFrm = $("#pageFrm");
			//li태그 안에 a태그 값 가져와서 form태그에 적용시켜 페이지 이동
			//이 버튼이 눌렸을 때 아래의 function을 실행시키겠다.
			$(".paginate_button a").on("click", function(e){
				// e: 현재 클릭한 a태그 요소 자체
				e.preventDefault(); //a태그의 href속성 자동 막기
				var page =$(this).attr("href"); //클릭한 a태그의 href값 가져오기
				pageFrm.find("#page").val(page);
				pageFrm.submit();
			});
			
			//상세보기 클릭 시 이동
			$(".move").on("click",function(e){
				e.preventDefault(); //a태그의 href속성 자동 막기
				var idx = $(this).attr("href");
				/* 위에서 가져온 idx를 아래의 idx에 넣어줌. */
				var tag = "<input type='hidden' name ='idx' value='"+idx+"'>";
				pageFrm.append(tag);
				/*action값을 바꿔줌.  */
				pageFrm.attr("action", "${cpath}/board/get");
				pageFrm.submit();
			});
			
			
			var result="${result}";
			checkModal(result);
			
			$("#regBtn").click(function(){
				//버튼을 눌렀을 때, 함수를 작동시키겠다.
				//location.href는 페이지를 이동시킴.
				location.href="${cpath}/board/register";
			});
		});
		
		function checkModal(result){
			if(result == ''){
				return;
			}
			if(parseInt(result)> 0){
				$(".modal-body").text("게시글 " + result+"번이 등록되었습니다.");
				$("#myMessage").modal("show");
			}
		}
	
	</script>


</body>
</html>
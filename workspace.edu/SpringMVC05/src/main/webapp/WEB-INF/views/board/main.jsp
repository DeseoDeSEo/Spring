<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@ taglib prefix ="c" uri="http://java.sun.com/jsp/jstl/core" %>
	<%@ taglib prefix ="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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
		<jsp:include page="../common/header.jsp"></jsp:include>
		<h2>Spring MVC05</h2>
		<div class="panel panel-default">
			<div class="panel-heading">Board</div>
			<div class="panel-body">
				<table id="boardList" class="table table-bordered table-hover">
				<!-- tr class = success , info , danger, warning, active -->
					<tr class="danger">  
						<td>번호</td>
						<td>제목</td>
						<td>작성자</td>
						<td>작성일</td>
						<td>조회수</td>
					</tr>	
					<tbody id="view">
						<!-- 비동기 방식으로 가져온 게시글을 나오게 할 부분.  -->
						
					</tbody>	
					<c:if test="${not empty mvo}">  <!-- 로그인을 했는지 안 했는지. -->
					<tr>
						<td colspan="5">
							<button onclick="goForm()" class="btn btn-primary btn-sm"> 글쓰기 </button>
						</td>
					</tr>	
					</c:if>	
				</table>

			</div>
			
			<!-- 글쓰기 폼 -->
			<div class ="panel-body" id="wform" style="display:none;">
				<form id="frm">        <!--action ="boardInsert.do" method="post"> form 태그는 동기 방식임. --> 
				<input type="hidden" name="memID" value="${mvo.memID}">
				<table class="table" >
					<tr>
						<td>제목</td>
						<!-- 반드시 name의 이름은 vo의 필드명과 똑같이 해야한다. -->
						<!-- class=form-control사용하면 알아서 크기에 맞춰짐. -->
						<td><input type ="text" name ="title" class ="form-control"></td>
				   </tr>
				   <tr>
					     <td>내용</td>
						<td><textarea class ="form-control" name ="content" rows="7" cols=""></textarea></td>
					</tr>
					<tr>
					     <td>작성자</td>
						<td><input readonly="readonly" value="${mvo.memName}" type="text"  name ="writer" class ="form-control"></td>
					</tr>
					
					<tr>
						<td colspan="2" align="center">
							<button class="btn btn-success btn-sm" type="button" onclick="goInsert()">등록</button>
							<button class="btn btn-warning btn-sm" type="reset" id="fclear" >취소</button>
							<button onclick = "goList()" class="btn btn-info btn-sm">목록</button>
							<!-- a태그는 동기방식임. -->
						</td>
					</tr>
				</table>
				</form>
			</div>
			<div class="panel-footer">스프링게시판-쿨쿨이</div>
		</div>
	</div>
	
	<script type="text/javascript">
	//ajax에서도 post방식으로 데이터를 보내기 위해서는 csrf token값을 전달해야한다.
	
	
	//token의 이름과 값을 가져오기.
	//ajax에서 csrf의 이름을 사용할떄는 parameterNAme이 아니라 headerNAme 사용
	var csrfHeaderName = "${_csrf.headerName}";
	var csrfTokenValue = "${_csrf.token}";
	
	
	
	
		$(document).ready(function(){
			//html이 다 로딩되고나서 아래의 코드를 실행함.
			loadList();
			
		});

	//함수 정의
		function loadList(){
			//비동기방식으로 게시글 리스트 가져오기 기능
			//ajax는 jquery라이브러리안에 있는 라이브러리.
			//jquery => $()
			//ajax안에는 요청url, 어떻게 데이터 받을지, 요청방식 등등 => 객체 형태로 넣어줌.
			//json=> java script object 
			
			$.ajax({ //객체 표현
				url:"board/all",
				type : "get",
				dataType:"json",
				success: makeView, 
				//ajax를 요청한 다음에 성공하면 makeView를 실행함.(콜백함수)요청하고 나중에 끝나고 실행됨.
				error:function(){alert("error");}
				//실패하면 실행할 것..!
			});
		}
	function makeView(data){
		
		var listHtml = "";
		$.each(data, function(index, obj){
			// index는 회차 반복을 위해서 사용. obj는 하나씩 돌면서 데이터를 담음.
			listHtml += "<tr>";
			listHtml += "<td>" + (index+1) + "</td>";
			listHtml += "<td id='t"+ obj.idx +"'>";
			listHtml += "<a href='javascript:goContent(" + obj.idx+ ")'>";
			listHtml += obj.title;			
			listHtml += "</a>";
			listHtml += "</td>";
			
			listHtml += "<td id='w"+ obj.idx +"'>" + obj.writer + "</td>";
			listHtml += "<td>" + obj.indate + "</td>";
			listHtml += "<td>" + obj.count + "</td>";
			listHtml += "</tr>";
			
			// 상세보기 화면
			listHtml += "<tr id='c"+ obj.idx +"' style='display:none'>";
			listHtml += "<td>내용</td>";
			listHtml += "<td colspan='4'>";
			listHtml += "<textarea id='ta" +obj.idx+ "' readonly rows='7' class='form-control'>"; //readonly는 읽기전용.
			/* listHtml += obj.content; */
			listHtml += "</textarea>";
			 
			//수정, 삭제 화면
			//조건 문안에서 EL식을 사용하고 싶다면 문자열로 감싸줘야한다.
			if("${mvo.memID}" == obj.memID){
				listHtml += "<br>";
				listHtml += "<span id='ub"+ obj.idx +"'>";
				listHtml +="<button onclick='goUpdateForm("+obj.idx+ ")' class='btn btn-sm btn-success'>수정화면</button></span> &nbsp;" // non break space 
				listHtml +="<button onclick='goDelete("+ obj.idx +")' class='btn btn-sm btn-warning'>삭제</button> &nbsp;"
				listHtml += "</td>";
				listHtml +="</tr>";
			}else{
				listHtml += "<br>";
				listHtml += "<span id='ub"+ obj.idx +"'>";
				listHtml +="<button disabled onclick='goUpdateForm("+obj.idx+ ")' class='btn btn-sm btn-success'>수정화면</button></span> &nbsp;" // non break space 
				listHtml +="<button disabled onclick='goDelete("+ obj.idx +")' class='btn btn-sm btn-warning'>삭제</button> &nbsp;"
				/* button에 disabled 넣으면 버튼은 나오지만 작동하지 않는다. */
				listHtml += "</td>";
				listHtml +="</tr>";
			}
			listHtml += "</td>";
			listHtml +="</tr>";
		});
		
		$("#view").html(listHtml);
		//서버의 역할은 데이터를 분배하는 역할임. 각 클라이언트의 환경에 맞게 꾸며준다. 
		goList();
	}
	//goForm 함수를 만들어서 view는 감추고 wform은 보이게 하시오.
	function goForm(){
		/*  css바꿈 */
			$("#boardList").css("display","none");
			$("#wform").css("display","block");
	}
	function goList(){
		/*  css바꿈 */
			$("#wform").css("display","none");
			$("#boardList").css("display","table");
	}
	function goInsert(){
		/*  게시글 등록기능-비동기 */
		var fData = $("#frm").serialize();
		/* goInsert를 눌렀을 때 frm 안에 있는 input 태그 값의 title="안녕"&content="반가워" 이런식으로 직렬화 해서 가져오는거임. */
		$.ajax({
			url : "board/new",
			type : "post",
			data : fData,
			beforeSend:function(xhr){
				xhr.setRequestHeader(csrfHeaderName, csrfTokenValue);
			},
			success: loadList,
			error: function(){ alert("error") }
		});
		$("#fclear").trigger("click");
	}
	function goContent(idx){
		//display속성의 값을 가져옴.
		// 안 보이는 상태면 보이게 해줌.
		if($("#c"+ idx).css("display")=="none"){
			
			$.ajax({
				url:"board/"+ idx,
				type: "get",
				dataType:"json", //받아오는 데이터타입
				success: function(data){
					$("#ta"+idx).val(data.content);
				},
				error:function(){alert("error");}
			});
			$("#c"+ idx).css("display", "table-row");
			
		}else{
			$("#c"+ idx).css("display","none");
			//조회수!!!!!!!!!!!!!!!!!!!!!!!!!
			// boardCount.do 요청해서 조회수를 1 올리고 게시글을 다시 불러와 적용시키시오.
			$.ajax({
				url:"board/count",  //pathvariable방식 : 경로부분에 값(idx)을 보내는 방식, url에 가독성을 올리기 위해서.
				type:"put",
				contentType:"application/json",
				data:JSON.stringify({"idx":idx}),
				beforeSend:function(xhr){
					xhr.setRequestHeader(csrfHeaderName, csrfTokenValue);
				},
				success:loadList,
				error: function(){alert("error");}
			});
		}
	}
	
	function goDelete(idx){
		$.ajax({
			url:"board/"+ idx,
			type:"delete", //내 서버에서만 삭제 가능함.,
			data:{"idx": idx},
			beforeSend:function(xhr){
				xhr.setRequestHeader(csrfHeaderName, csrfTokenValue);
			},
			success:loadList,
			error: function(){alert("error");}
		});
	}
	
	function goUpdateForm(idx){
		
		$("#ta"+ idx).attr("readonly", false);
		
		var title =$("#t"+ idx).text();
		var newTitle  ="<input id='nt"+ idx +"' value='"+ title +"' type='text' class='form-control'>";
		$("#t"+ idx).html(newTitle);
		
		var writer =$("#w"+ idx).text();
		var newWriter  ="<input id='nw"+idx+"' value='"+ writer +"' type='text' class='form-control'>";
		$("#w"+ idx).html(newWriter);
		
		var newBtn ="<button onclick='goUpdate("+idx+")' class='btn btn-primary btn-sm'>수정</button>";
		$("#ub"+idx).html(newBtn);
		
	}
	function goUpdate(idx){
		var title =$("#nt" + idx).val();
		var content =$("#ta" + idx).val();
		var writer =$("#nw" + idx).val();
		//console.log(title+"/"+ content+"/"+writer);
		//boardUpdate.do로 요청을 통해 게시글을 수정하고 
		// 수정된 게시글 다시 불러와서 적용시키시오.
		$.ajax({
			//09/14일 목요일
			url:"board/update",
			type:"put", //put방식 도 delete도 post방식에 속해이씀.
			contentType: "application/json; charset=utf-8", 
			data:JSON.stringify({"idx":idx,"title":title, "content":content,"writer":writer}), //json객체의 stringfy함수를 사용. 문자열형태로 바꿔줌.
			beforeSend:function(xhr){
				xhr.setRequestHeader(csrfHeaderName, csrfTokenValue);
			},
			success:loadList,
			error: function(){alert("error");}
		});
		
	}
	
	
	
	
	
	</script>

</body>
</html>
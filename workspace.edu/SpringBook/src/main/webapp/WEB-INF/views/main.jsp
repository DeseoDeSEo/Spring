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
		<h2>Spring Book</h2>
		<div class="panel panel-default">
			<div class="panel-heading">Book</div>
			<div class="panel-body">
				<table id="boardList" class="table table-bordered table-hover">
				<!-- tr class = success , info , danger, warning, active -->
					<tr class="light">  
						<td>번호</td>
						<td>제목</td>
						<td>작가</td>
						<td>출판사</td>
						<td>isbn</td>
						<td>보유도서수</td>
						<td>삭제</td>
						<td>수정</td>
						
					</tr>	
					<tbody id="view">
						<!-- 비동기 방식으로 가져온 게시글을 나오게 할 부분.  -->
						
					</tbody>	
					
					<tr>
						<td colspan="5">
							<button onclick="goForm()" class="btn btn-primary btn-sm"> 도서등록 </button>
						</td>
					</tr>		
				</table>

			</div>
			
			<!-- 글쓰기 폼 -->
			<div class ="panel-body" id="wform" style="display:none;">
				<form id="frm">         <!--action ="boardInsert.do" method="post"> form 태그는 동기 방식임. --> 
				<table class="table" >
					<tr>
						<td>제목</td>
						<!-- 반드시 name의 이름은 vo의 필드명과 똑같이 해야한다. -->
						<!-- class=form-control사용하면 알아서 크기에 맞춰짐. -->
						<td><input type ="text" name ="title" class ="form-control"></td>
				   </tr>
				   <tr>
					     <td>작가</td>
						<td><input type="text" class ="form-control" name ="author" ></input></td>
					</tr>
					<tr>
					     <td>출판사</td>
						<td><input type="text"  name ="company" class ="form-control"></td>
					</tr>
					<tr>
					     <td>isbn</td>
						<td><input type="text"  name ="isbn" class ="form-control"></td>
					</tr>
					<tr>
					     <td>보유도서 수</td>
						<td><input type="text"  name ="count" class ="form-control"></td>
					</tr>
					
					<tr>
						<td colspan="2" align="center">
							<button class="btn btn-success btn-sm" type="button" onclick="goInsert()">등록</button>
							<button class="btn btn-warning btn-sm" type="reset" id="fclear" >취소</button>
							<button onclick = "goList()" class="btn btn-info btn-sm">리스트로가기</button>
							<!-- a태그는 동기방식임. -->
						</td>
					</tr>
				</table>
				</form>
			
			</div>
			
			
			<!-- 수정 폼 -->
			<div class ="panel-body" id="eform" style="display:none;">
				<form id="frm">        
				<table class="table">
					<tr>
						<td>제목</td>
						<td><input readonly ="readonly" value="${title}" type="text" name ="title" id="title" class ="form-control"></td>
				   </tr>
				   <tr>
					     <td>작가</td>
						<td><input type="text" readonly ="readonly" class ="form-control" name ="author" id="author"></input></td>
					</tr>
					<tr>
					     <td>출판사</td>
						<td><input readonly ="readonly"  type="text"  name ="company" class ="form-control" id="company"></td>
					</tr>
					<tr>
					     <td>isbn</td>
						<td><input readonly ="readonly"  type="text"  name ="isbn" class ="form-control" id="isbn" ></td>
					</tr>
					<tr>
					     <td>보유도서 수</td>
						<td><input type="text" name ="count" class ="form-control" id"count" ></td>
					</tr>
					
					<tr>
						<td colspan="2" align="center">
							<button class="btn btn-success btn-sm" type="button" onclick="goUpdate()">등록</button>
							<button class="btn btn-warning btn-sm" type="reset" id="fclear" >취소</button>
							<button onclick = "goList()" class="btn btn-info btn-sm">리스트로가기</button>
							<!-- a태그는 동기방식임. -->
						</td>
					</tr>
				</table>
				</form>
			
			</div>
			

			<div class="panel-footer">스프링도서관-박병관</div>
		</div>
	</div>
	
	<script type="text/javascript">
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
			listHtml += "<td id='t"+ obj.num +"'>";
			listHtml += obj.title;			
			listHtml += "</a>";
			listHtml += "</td>";
			
			listHtml += "<td id='w"+ obj.num +"'>" + obj.author + "</td>";
			listHtml += "<td>" + obj.company + "</td>";
			listHtml += "<td>" + obj.isbn + "</td>";
			listHtml += "<td>" + obj.count + "</td>";
			listHtml += "<td><button onclick='goDelete("+ obj.num +")' class='btn btn-sm btn-primary'>삭제</button> &nbsp</td>;"
			listHtml +="<td><button onclick='goEform("+obj.num+")' class='btn btn-sm btn-warning'>수정</button>&nbsp</td>;"
			listHtml += "</tr>";
			
 			// 상세보기 화면
			listHtml += "<tr id='c"+ obj.num +"' style='display:none'>";
			listHtml += "<td>내용</td>";
			listHtml += "<td colspan='4'>";
			listHtml += "<textarea id='ta" +obj.num+ "' readonly rows='7' class='form-control'>"; //readonly는 읽기전용.
			/* listHtml += obj.content; */
			listHtml += "</textarea>";
			  
/* 			//수정, 삭제 화면
			listHtml += "<br>";
			listHtml += "<span id='ub"+ obj.num +"'>";
			listHtml +="<button onclick='goUpdateForm("+obj.num+ ")' class='btn btn-sm btn-success'>수정화면</button></span> &nbsp;" // non break space 
			listHtml +="<button onclick='goDelete("+ obj.num +")' class='btn btn-sm btn-warning'>삭제</button> &nbsp;"
			listHtml += "</td>";
			listHtml +="</tr>"; */
			
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
			success: loadList,
			error: function(){ alert("error") }
		});
		$("#fclear").trigger("click");
	}
	/* function goContent(num){
		//display속성의 값을 가져옴.
		// 안 보이는 상태면 보이게 해줌.
		if($("#c"+ num).css("display")=="none"){
			
			$.ajax({
				url:"board/"+ num,
				type: "get",
				dataType:"json", //받아오는 데이터타입
				success: function(data){
					$("#ta"+num).val(data.content);
				},
				error:function(){alert("error");}
			});
			
			$("#c"+ num).css("display", "table-row");
			
			
		}else{
			$("#c"+ num).css("display","none");
			// boardCount.do 요청해서 조회수를 1 올리고 게시글을 다시 불러와 적용시키시오.
			$.ajax({
				url:"board/count",  //pathvariable방식 : 경로부분에 값(idx)을 보내는 방식, url에 가독성을 올리기 위해서.
				type:"put",
				contentType:"application/json",
				data:JSON.stringify({"num":num}),
				success:loadList,
				error: function(){alert("error");}
			});
		}
	} */
	
	function goDelete(num){
		$.ajax({
			url:"board/"+ num,
			type:"delete", //내 서버에서만 삭제 가능함.,
			data:{"num": num},
			success:loadList,
			error: function(){alert("error");}
		});
	}
	
	/* function goUpdateForm(num){
		$("#ta"+ num).attr("readonly", false);
		
		var count =$("#t"+ num).text();
		var newCount  ="<input id='nt"+ num +"' value='"+ count +"' type='text' class='form-control'>";
		$("#t"+ num).html(newCount);
		
		var newBtn ="<button onclick='goUpdate("+num+")' class='btn btn-primary btn-sm'>수정</button>";
		$("#ub"+num).html(newBtn);
		
		
		
	} */
	function goEform(num){
		$("#boardList").css("display","none");
		$("#eform").css("display","block");
		
			$.ajax({
				url:"board/"+ num,
				type: "get",
				dataType:"json", //받아오는 데이터타입
				success: function(data){
					$("#ta"+num).val(data.title);
				},
				error:function(){alert("error");}
			});
			
			$("#c"+ num).css("display", "table-row");

	}
	function goUpdate(num){
		
		var count =$("#nw" + num).val();
	
		$.ajax({
			//09/14일 목요일
			url:"board/update",
			type:"put", //put방식 : 
			contentType: "application/json; charset=utf-8", 
			data:JSON.stringify({"count":count}), //json객체의 stringfy함수를 사용. 문자열형태로 바꿔줌.
			success:loadList,
			error: function(){alert("error");}
		});
		
	}
	
	
	
	
	
	</script>

</body>
</html>
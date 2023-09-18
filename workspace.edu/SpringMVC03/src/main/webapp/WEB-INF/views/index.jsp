<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
  <title>뇽뇽이</title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.4/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
</head>
<body>


  
<div class="container">
  <jsp:include page="common/header.jsp"></jsp:include> <!-- include는 import랑 비슷한 것!  -->
  <h1><b>springMVC03 </b></h1>
  <p> 김볶
  <p style ="color: blue">저녁 뭐 드실거에욤</p>
  <p style ="color: blue;, font-size:300px;">오늘은 PIZZA</p>
</div>

<!-- Modal, 회원 가입 실패시 출력 됨. -->
  <div class="modal fade" id="myMessage" role="dialog">
    <div class="modal-dialog">
    
      <!-- Modal content-->
      <div id="messageType" class="modal-content">
        <div class="modal-header panel-heading">
          <button type="button" class="close" data-dismiss="modal">&times;</button>
          <h4 class="modal-title">${msgType}</h4>
        </div>
        <div class="modal-body">
          <p id="">${msg}</p>
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
        </div>
      </div>
      
    </div>
  </div>
  
  <script type="text/javascript">
  	$(document).ready(function(){
  		if(${not empty msgType}){
			if(${msgType eq "성공메세지"}){
				$("#messageType").attr("class","modal-content panel-warning");
			}
			$("#myMessage").modal("show");
		}
  	});
  </script>
  

</body>
</html>

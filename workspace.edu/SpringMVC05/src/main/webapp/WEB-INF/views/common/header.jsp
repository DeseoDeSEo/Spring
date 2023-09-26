<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
	<%@ taglib prefix ="c" uri="http://java.sun.com/jsp/jstl/core" %>
	<%@ taglib prefix ="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
	<!-- context-path값(=controller)을 내장객체 변수로 저장 -->
	<c:set var="contextPath" value="${pageContext.request.contextPath }" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
 
 <nav class="navbar navbar-default">
  <div class="container-fluid">
    <div class="navbar-header">
      <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#myNavbar">
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>                        
      </button>
      <a class="navbar-brand" href="${contextPath}/">뇽뇽이</a>
    </div>
    <div class="collapse navbar-collapse" id="myNavbar">
      <ul class="nav navbar-nav">
        <li class="active"><a href="${contextPath}/"> 메인 </a></li>
        <li><a href="boardMain.do">게시판</a></li>  <!--앞에 /없으면 /controller가 생략되어있음. 이건 contextPath임.  -->
      </ul>
      
      <!-- mvo가 비어있을 때만  보여줌.-->
      <c:if test="${empty mvo}">
      <ul class="nav navbar-nav navbar-right">
    
        		<li><a href="${contextPath}/loginForm.do"><span class="glyphicon glyphicon-globe"> 로그인</span> </a></li>
       			<li><a href="${contextPath}/joinForm.do"> 회원가입 </a></li>
             
      </ul>
      </c:if>
      
       <c:if test="${not empty mvo}">
      <ul class="nav navbar-nav navbar-right">
                <li>
                <c:if test="${mvo.memProfile ne ''}">
                	<img class="img-circle"style="width:50px; height:50px;"src="${contextPath}/resources/upload/${mvo.memProfile}">    
                </c:if>
                  <c:if test="${mvo.memProfile eq ''}">
                	<img class="img-circle"style="width:50px; height:50px;"src="${contextPath}/resources/images/default.jpg">
                </c:if>
                ${mvo.memName}님 welcome.     
                [
                	<!-- 권한 정보 띄우기 -->
                	<!-- 회원이 가진 권한의 리스트만큼 반복돌면서 꺼내기 -->
                	<c:forEach items="${mvo.authList}" var="auth">
                		<c:choose>
                			<c:when test="${auth.auth eq 'ROLE_USER'}">
                				U
                			</c:when>
                			<c:when test="${auth.auth eq 'ROLE_MANAGER'}">
                				M
                			</c:when>
                			<c:when test="${auth.auth eq 'ROLE_ADMIN'}">
                				A
                			</c:when>
                			
                			
                		</c:choose>
                	</c:forEach>
                
                ]
               
                </li>      
        		<li><a href="${contextPath}/updateForm.do"> <span class="glyphicon glyphicon-heart ">회원정보수정</span> </a></li>
        		<li><a href="${contextPath}/imageform.do"><span class="glyphicon glyphicon-picture">프로필사진등록 </span>  </a></li>
        		<li><a href="${contextPath}/logout.do"> <span class="glyphicon glyphicon-log-out">로그아웃</span> </a></li>
        	</ul>
       
       <!--db에는 파일이름만 저장. 실제로는 resource폴더 안에 진짜 img를 저장.  -->
      </c:if>
      
    </div>
  </div>
</nav>
 
</body>
</html>
<%@page import="java.util.List"%>
<%@page import="egovframework.sample.service.impl.SampleDAOJDBC"%>
<%@page import="egovframework.sample.service.SampleVO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%
	// 1. DB 연동 처리
	SampleVO vo = new SampleVO();
	SampleDAOJDBC sampleDAO = new SampleDAOJDBC();
	List<SampleVO> sampleList = sampleDAO.selectSampleList(vo);
	
	// 2. 응답 화면 구성
%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta http-equiv="X-UA-Compatible" content="ie=edge">
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
	<title>SAMPLE 목록</title>
</head>
<body>
	
	<div class="container">
	  <h2>SAMPLE 목록</h2>
	  <p>등록한 모든 SampleVO 정보를 화면에 목록으로 보여줍니다.</p> <br><br>           
	  <table class="table table-hover">
	    <thead>
	      <tr>
	        <th>아이디</th>
	        <th>제목</th>
	        <th>작성자</th>
	        <th>등록일</th>
	      </tr>
	    </thead>
	    <tbody>
	    <% for(SampleVO sample : sampleList) { %>
	      <tr>
	        <td><a href="selectSample.jsp?id=<%= sample.getId() %>"><%=sample.getId()%></a></td>
	        <td> <%=sample.getTitle() %></td>
	        <td><%= sample.getRegUser() %></td>
	        <td><%= sample.getRegDate() %></td>
	      </tr>
	    <% } %>
	    </tbody>
	  </table>
	<br>
	<a class="btn btn-success" href="insertSample.jsp">샘플 등록</a>	  
	  
	</div>													
</body>
</html>
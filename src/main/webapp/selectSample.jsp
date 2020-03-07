<%@page import="egovframework.sample.service.impl.SampleDAOJDBC"%>
<%@page import="egovframework.sample.service.SampleVO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%

	// 1. 검색할 아이디 추출
	String id = request.getParameter("id");
	
	// 2. DB 연동 처리
	SampleVO vo = new SampleVO();
	vo.setId(id);
	
	SampleDAOJDBC sampleDAO = new SampleDAOJDBC();
	SampleVO sample = sampleDAO.selectSample(vo);
	
	// 3. 응답 화면 구성
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
	<title>SAMPLE 상세</title>
</head>
<body>
	<div class="container">
	  <h2>SAMPLE 상세</h2>
	  <p>SampleVO의 상세한 내용입니다.</p><br>
	  
	  <form action="updateSample_proc.jsp" method="post">
	    <%-- 
	    <div class="form-group">
	      <label for="id">아이디</label>
	      <input type="text" name="id" class="form-control" id="id" disabled="disabled" value="<%= sample.getId() %>">
	    </div>
	     --%>
	    <input type="hidden" name="id" value="<%= sample.getId() %>">
	    
	    <div class="form-group">
	      <label for="title">제목</label>
	      <input type="text" name="title" class="form-control" id="title" value="<%= sample.getTitle()%>">
	    </div>
	    <div class="form-group">
	      <label for="regUser">작성자</label>
	      <input type="text" name="regUser" class="form-control" id="regUser" value="<%= sample.getRegUser() %>" >
	    </div>
	    <div class="form-group">
	      <label for="content">내용</label>
	      <textarea class="form-control" name="content"><%= sample.getContent() %></textarea>
	    </div>
	    <br>
	        등록일 : <%= sample.getRegDate() %>
		<br><br>
	    <button type="submit" class="btn btn-default">UPDATE</button>
	  </form>
	</div>
	
	<div class="container" style="margin-top:2em; text-align:right">
		  <a href="insertSample.jsp" class="btn btn-success" role="button">INSERT</a>
		  <!-- <a href="deleteSample_proc.jsp" class="btn btn-danger" role="button">DELETE</a> -->
		  <a href="deleteSample_proc.jsp?id=<%=sample.getId() %>" class="btn btn-danger" role="button">DELETE</a>
		  <a href="selectSampleList.jsp" class="btn btn-info" role="button">LIST</a>
	</div>
</body>
</html>
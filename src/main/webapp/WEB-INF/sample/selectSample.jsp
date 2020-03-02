<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>SAMPLE 상세</title>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"></script>
</head>
<body>
<div class="container mt-5">
  <h2>SAMPLE 상세</h2>
  <form action="updateSample.do" method="post">
    <input type="hidden" name="id" value="${sample.id }" class="form-control">
    <div class="form-group">
      <label for="title">제목</label>
      <input type="text" name="title" value="${sample.title }" class="form-control" id="title">
    </div>
    <div class="form-group">
     <label for="regUser">작성자</label>
     <input type="text" name="regUser" value="${sample.regUser }" class="form-control" id="regUser" disabled="disabled"> 
              <%-- 작성자: <strong>[ ${sample.regUser } ]</strong> --%>
    </div>
    <div class="form-group">
      <label for="content">내용</label>
      <textarea class="form-control" name="content" id="content" cols="40" rows="5">${sample.content }</textarea>
    </div>
    <div class="form-group">
      <label for="date">등록일</label>
      <input type="text"  value="${sample.regDate }" class="form-control" id="date" disabled="disabled">
    </div>
    <button type="submit" class="btn btn-primary">Update</button>
  </form>
  
  <div class="btn-group mt-5">
  	<a href="insertSample.do" class="btn btn-primary">INSERT</a>
  	<a href="deleteSample.do?id=${sample.id }" class="btn btn-danger">DELETE</a>
  	<a href="selectSampleList.do" class="btn btn-info">LIST</a>
  </div>
</div>
</body>
</html>
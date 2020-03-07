<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta http-equiv="X-UA-Compatible" content="ie=edge">
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
	<title>SAMPLE 등록</title>
</head>
<body>
	<div class="container">
	  <h2>SAMPLE 등록</h2>
	  <p>SampleVO를 등록하는 화면입니다.</p><br>
	  
	  <form action="insertSample_proc.jsp" method="post">
		<div class="form-group">
			<label for="title">제목</label>
			<input type="text" name="title" class="form-control" id="title" placeholder="제목을  입력하세요" required="required">
		</div>
		<div class="form-group">
	      <label for="regUser">작성자</label>
	      <input type="text" name="regUser" class="form-control" id="regUser" placeholder="작성자를 입력하세요" required="required">
	    </div>
	    <div class="form-group">
	      <label for="content">내용</label>
	      <textarea class="form-control" name="content"></textarea>
	    </div>
	    <br><br>
	    <button type="submit" class="btn btn-success">INSERT</button>
	    <a href="selectSampleList.jsp" class="btn btn-info" role="button">LIST</a>
	  </form>
	</div>
		
</body>
</html>
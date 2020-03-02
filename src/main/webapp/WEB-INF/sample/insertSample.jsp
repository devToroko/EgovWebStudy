<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>SAMPLE 등록</title>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"></script>
</head>
<body>
	<div class="container">
		<h2>SAMPLE 등록</h2>
		<form action="insertSample.do" method="post">
			<div class="form-group">
		      <label for="id">제목</label>
		      <input class="form-control" type="text" name="title">
		    </div>
		    <div class="form-group">
		      <label for="id">작성자</label>
		      <input class="form-control" type="text" name="regUser">
		    </div>
		    <div class="form-group">
		      <label for="content">내용</label>
		      <textarea class="form-control" name="content" id="content" cols="40" rows="5"></textarea>
		    </div>
		    <input class="btn btn-success" type="submit" class="btn btn-primary" value="INSERT">
		    <a href="selectSampleList.do" class="btn btn-primary">LIST</a>	
		</form>
	</div>
</body>
</html>
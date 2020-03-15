<%@page import="java.util.List"%>
<%@page import="egovframework.sample.service.impl.SampleDAOJDBC"%>
<%@page import="egovframework.sample.service.SampleVO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta http-equiv="X-UA-Compatible" content="ie=edge">
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
	<title><spring:message code="list.mainTitle"/></title>
</head>
<body>
	<div class="container">
	  <h2><spring:message code="list.mainTitle"/></h2>
	  <p><spring:message code="list.mainIntro"/></p><br>
	  <div class="row">
		  <div class="col-sm-6">
	  	  	<a class="btn btn-info" href="selectSampleList.do?lang=en"><spring:message code="list.link.locale.en"/></a>
	  	  	<a class="btn btn-info" href="selectSampleList.do?lang=ko"><spring:message code="list.link.locale.ko"/></a>
	  	  </div>
	  	  <div class="col-sm-6" style="text-align:right">
			  <form action="selectSampleList.do" method="post" class="form-inline">
				<select name="searchCondition" class="form-control">
					<option value="TITLE"><spring:message code="list.search.title"/></option>
					<option value="CONTENT"><spring:message code="list.search.condition"/></option>
				</select>
				<div class="form-group">
				   <input name="searchKeyword" type="text" class="form-control" >
				</div>
				<button type="submit" class="btn btn-default"><spring:message code="list.search.button"/></button>
			  </form>       
		  </div>
      </div>
      <br>
      
	  <table class="table table-hover">
	    <thead>
	      <tr>
	        <th><spring:message code="list.list.table.id"/></th>
	        <th><spring:message code="list.list.table.title"/></th>
	        <th><spring:message code="list.list.table.regUser"/></th>
	        <th><spring:message code="list.list.table.regDate"/></th>
	      </tr>
	    </thead>
	    <tbody>
	    <c:forEach var="sample" items="${sampleList}">
	    	<tr>
		        <td><a href="selectSample.do?id=${sample.id}">${sample.id}</a></td>
		        <td>${sample.title }</td>
		        <td>${sample.regUser}</td>
		        <td>${sample.regDate}</td>
	     	</tr>
	    </c:forEach>
	    </tbody>
	  </table>
	<br>
	
	<a class="btn btn-success" href="insertSample.do"><spring:message code="list.link.create"/></a>	  
	  
	</div>							
						
</body>
</html>
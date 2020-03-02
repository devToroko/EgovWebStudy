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
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><spring:message code="list.mainTitle"/></title>

<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"></script>
</head>
<body>

<div class="container mt-5">
  <h2><spring:message code="list.mainTitle"/></h2>
  <br>
  <!-- 검색시작 -->
  <div class="row">
  	  <div class="col-6">
  	  	<a class="btn btn-info" href="selectSampleList.do?lang=en"><spring:message code="list.link.locale.en"/></a>
  	  	<a class="btn btn-info" href="selectSampleList.do?lang=ko"><spring:message code="list.link.locale.ko"/></a>
  	  </div>
      <div class="col-6">
      <form action="selectSampleList.do" method="post" class="form-group">
          <div class="input-group">
          	  <div class="input-group-append">
                  <select class="form-control" name="searchCondition">
			        <%-- <c:forEach var="option" items="${conditionMap }">
			        	<option value="${option.value}">${option.key}</option>
			        </c:forEach> --%>
			      	<option value="TITLE"><spring:message code="list.search.title"/></option>
			      	<option value="TITLE"><spring:message code="list.search.condition"/></option>
			      </select>
              </div>
              <input class="form-control border-secondary" name="searchKeyword" type="text" placeholder="search...">
              <div class="input-group-append">
                  <button class="btn btn-outline-secondary" type="submit">
                     	<spring:message code="list.search.button"/>
                  </button>
              </div>
          </div>
      </form>
      </div>
  </div>
  
  
  <table class="table table-dark table-striped text-center mt-3">
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
        <td><a class="btn btn-info" role="button" href="selectSample.do?id=${sample.id}">${sample.id}</a></td>
        <td>${sample.title}</td>
        <td>${sample.regUser}</td>
        <td>${sample.regDate}</td>
      </tr>
    
    </c:forEach>
    </tbody>
  </table>
 
  <br>
  <a class="btn btn-success" href="insertSample.do"><spring:message code="list.link.create"/></a>
  
</div>
<br>

</body>
</html>
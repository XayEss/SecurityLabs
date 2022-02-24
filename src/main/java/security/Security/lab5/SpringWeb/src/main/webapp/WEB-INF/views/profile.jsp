<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@include file="header.jsp"%>
<center>
<c:choose>
<c:when test="${authorized != null}">
<table>
<tr><td>Login: </td><td>${user.login}</td></tr>
<tr><td>Name: </td><td>${user.name}</td></tr>
<tr><td>Gender: </td><td>${user.gender}</td></tr>
<tr><td>Region: </td><td>${user.region}</td></tr>
<tr><td>Comment: </td><td>${user.comment}</td></tr>
</table>
</c:when>
<c:otherwise>
<h1>You need to log in to access this page</h1>
</c:otherwise>
</c:choose>
</center>


<%@include file="footer.jsp"%>
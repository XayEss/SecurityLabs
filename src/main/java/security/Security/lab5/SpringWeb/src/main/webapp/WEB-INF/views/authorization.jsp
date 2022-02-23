<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@include file="header.jsp"%>
<c:if test="${showForm}">
<center>
<table>
<form action="/SpringWeb/authorization" method="post">
    <tr><td>Login:</td><td><input type="text" name="login"/></td></tr>
    <tr><td>Password:</td><td><input type="password" name="password"/></td></tr>
    <tr><td> </td><td><input type="submit" value="SEND"/></td></tr>
</form>
</table>
</center>
</c:if>
<c:if test="${authorized != null}">
<h1> Hello ${authorized} <a href='?logout'>logout</a></h1>
</c:if>
<%@include file="footer.jsp"%>
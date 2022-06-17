<%@ page pageEncoding="UTF-8" %><%@include file="header.jsp"%>
<c:forEach items="${productList}" var="product">
<table>
<tr><td>${product.name}</td><td></td></tr>
<tr><td><a href="product?id=${product.id}"><img src="static/images/${product.id}.jpg" width="150" height="100%"/></a></td><td>${product.description}</td></tr>
<tr><td>${product.price}</td><td><form action="cart" method="post">
    <img onclick="addValue('${product.id}', -1)" src="static/images/minus_sign.png" width="25" height="25"/><input id="amount${product.id}" type="text" size="3" name="amount" value="1"/>
    <img onclick="addValue('${product.id}', 1)" src="static/images/plus_sign.png" width="25" height="25"/>
    <input type="hidden" name="id" value="${product.id}"/>
    <input  type="button" value="Buy" onclick="prepareParameters(${product.id})"/></form></td></tr>
</table><br/><br/>
</c:forEach>
<%@include file="footer.jsp"%>

<%@ page pageEncoding="UTF-8" %><%@include file="header.jsp"%>
<table>
<tr><td>${product.name}</td><td></td></tr>
<tr><td><a href="product?id=${product.id}"><img src="static/images/${product.id}.jpg" width="250" height="100%"/></a></td><td>${product.description}</td></tr>
<tr><td>${product.price}</td><td>Buy</td></tr>
<form action="cart" method="post">
 <input type="text" size="2" name="quantity" value="1"/>
        <input type="hidden" name="id" value="${product.id}"/>
        <input type="submit" value="Купить"/></form></td></tr>
</form></table><br/><br/>
<%@include file="footer.jsp"%>

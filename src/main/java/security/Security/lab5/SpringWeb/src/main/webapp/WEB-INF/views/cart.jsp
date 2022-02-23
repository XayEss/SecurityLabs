<%@ page pageEncoding="UTF-8" %><%@include file="header.jsp"%>
<c:forEach var="product" items="${productMap}">
<div id="div${product.key.id}">
    <table>
        <tr><td>${product.key.name}</td><td></td></tr>
        <tr><td><a href="products?id=${product.key.id}"><img src="static/images/${product.key.id}.jpg" width="150" height="100%"/></a></td><td>${product.key.description}</td></tr>
        <tr><td>${product.key.price}</td><td><form action="cart" method="post">
            <input id = "amount${product.key.id}" type="text" size="2" name="amount" value="${product.value}"/>
            <input type="hidden" name="change" value="${product.key.id}"/>
            <input type="button" value="Изменить" onclick="changeCart(${product.key.id})"/></form></td></tr>
    </table><br/><br/>
    </div>
</c:forEach>
<c:if test="${sessionScope.cartSize == null || sessionScope.cartSize <= 0}">
    В корзине пусто :(
</c:if>
<c:if test="${sessionScope.sumProductPrices != null && sessionScope.sumProductPrices > 0}">
    Общая сумма заказа: ${sessionScope.sumProductPrices}
</c:if>
<%@include file="footer.jsp"%>

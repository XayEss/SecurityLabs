<%@ page pageEncoding="UTF-8"%>
</div>

<div id="sidebar">
	<table border=1>
		<tr>
			<td width="252" align="left"><font color=white> <c:choose>
						<c:when test="${sessionScope.authorized!=null}">Вы авторизировались как ${sessionScope.authorized}</c:when>
						<c:otherwise>
	Вы не авторизованы
</c:otherwise>
					</c:choose><br /> В вашей корзине <span id="cartSize">${(sessionScope.cartSize==null?"0":""+sessionScope.cartSize)}</span>
					товаров.
			</font></td>
		</tr>
	</table>
	<h2>Боковое меню</h2>
	<ul>
		<li><a href="/SpringWeb/products?category=raccoons">Еноты</a></li>
		<li><a href="/SpringWeb/products?category=pandas">Панды</a></li>
		<li><a href="/SpringWeb/products?category=ravens">Вороны</a></li>
		<li><a href="/SpringWeb/products?category=Magical Creatures">Магические
				существа</a></li>
		<li><a href="registration">Регистрация</a></li>
		<li><a href="authorization">Вход</a></li>
		<li><a href="cart">Корзина</a></li>
		<li><a href="profile">Профиль</a></li>
	</ul>
</div>
</div>
</div>
</div>
</div>
<div id="footer">
	<p>Copyright (c) by Селюк Александер & Данил Настарчук</p>
</div>
<script type="text/javascript">
	function prepareParameters(id) {
		var amount = document.getElementById("amount" + id).value;
		console.log("id: " + id + " am: " + amount);
		sendParameters(id, amount);
	}
	
	function addValue(id, num){
		var element = document.getElementById("amount"+id);
		var result = +element.value + +num;
		if(result >= 0){
		element.value = result;
		}
	}

	function sendParameters(id, amount) {
		//var change = document.getElementById("amount").value;
		$.ajax({
			url : 'cart', /* Куда пойдет запрос */
			method : 'post', /* Метод передачи (post или get) */
			data : "id=" + id + "&amount=" + +amount, /* Параметры передаваемые в запросе. */
			success : function(data) { /* функция которая будет выполнена после успешного запроса.  */
				document.getElementById("cartSize").innerHTML = data; /* В переменной data содержится ответ от index.php. */
			}
		})
	}

	function changeCart(id) {
		var amount = document.getElementById("amount" + id).value;
		$.ajax({
			url : 'cart', /* Куда пойдет запрос */
			method : 'post', /* Метод передачи (post или get) */
			data : "change=" + id + "&amount=" + +amount, /* Параметры передаваемые в запросе. */
			success : function(data) { /* функция которая будет выполнена после успешного запроса.  */
				document.getElementById("cartSize").innerHTML = data; /* В переменной data содержится ответ от index.php. */
			}
		})
		if (amount == 0) {
			document.getElementById("div" + id).innerHTML = "";
		}
	}
</script>
</body>
</html>

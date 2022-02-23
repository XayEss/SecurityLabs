<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page isELIgnored="false"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@include file="header.jsp"%>

<center>
	<c:choose>
		<c:when test="${showForm}">
			<table>
				<form action="/SpringWeb/registration" method="post">
					<tr>
						<td>Email:</td>
						<td><input type="email" name="login" value="${user.login}" /></td>
					</tr>
					<tr>
						<td>Password:</td>
						<td><input type="password" name="password"
							value="${user.password}" /></td>
					</tr>
					<tr>
						<td>Repeat password:</td>
						<td><input type="password" name="passwordRepeat"
							value="${user.repeatPassword}" /></td>
					</tr>
					<tr>
						<td>Name:</td>
						<td><input type="text" name="name" value="${user.name}" /></td>
					</tr>
					<tr>
						<td>Gender:</td>
						<td>M<c:choose>
								<c:when test='${user.gender.equals("M")}'>
									<input type="radio" name="gender" value="M" checked="true" />
								</c:when>
								<c:otherwise>
									<input type="radio" name="gender" value="M" />
								</c:otherwise>
							</c:choose>F <c:choose>
								<c:when test='${user.gender.equals("F")}'>
									<input type="radio" name="gender" value="F" checked="true" />
								</c:when>
								<c:otherwise>
									<input type="radio" name="gender" value="F" />
								</c:otherwise>
							</c:choose></td>
					</tr>
					<tr>
						<td>Region:</td>
						<td><select name="region">
								<c:choose>
									<c:when test='${user.region.equals("DNR")}'>
										<option value="DNR" selected="true">ДНР</option>
									</c:when>
									<c:otherwise>
										<option value="DNR">ДНР</option>
									</c:otherwise>
								</c:choose>
								<c:choose>
									<c:when test='${user.region.equals("LNR")}'>
										<option value="LNR" selected="true">ЛНР</option>
									</c:when>
									<c:otherwise>
										<option value="LNR">ЛНР</option>
									</c:otherwise>
								</c:choose>
								<c:choose>
									<c:when test='${user.region.equals("Crimea")}'>
										<option value="Crimea" selected="true">Крым</option>
									</c:when>
									<c:otherwise>
										<option value="Crimea">Крым</option>
									</c:otherwise>
								</c:choose>
						</select></td>
					</tr>
					<tr>
						<td>Comment:</td>
						<td><textarea rows=10 cols=20 name="comment" />${user.comment}</textarea></td>
					</tr>
					<tr>
						<td>I agree to install an Amigo Browser:</td>
						<td><input type="checkbox" name="browser" checked="true" /></td>
					</tr>
					<tr>
						<td></td>
						<td><input type="submit" value="SEND" /></td>
					</tr>
				</form>
			</table>
		</c:when>
		<c:otherwise>
			<h1>Thank you for Registration ${user.name}</h1>
		</c:otherwise>
	</c:choose>
	<c:if test="${errorText != null}">
 ${errorText}
</c:if>
</center>

<%@include file="footer.jsp"%>
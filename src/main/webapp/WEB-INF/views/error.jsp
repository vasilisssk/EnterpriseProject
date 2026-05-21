<%@ page isErrorPage="true" contentType="text/html;charset=UTF-8"%>
<h1>Произошла ошибка</h1>
<p>${pageContext.exception.message}</p>
<a href="${pageContext.request.contextPath}/departments">Вернуться на главную</a>
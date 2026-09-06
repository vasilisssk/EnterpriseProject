<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Отделы</title>
</head>
<body>
    <h1>Отделы предприятия</h1>

    <h2>Добавить отдел</h2>
    <form action="${pageContext.request.contextPath}/departments" method="post">
        <input type="text" name="name" placeholder="Название отдела" required/>
        <button type="submit">Добавить</button>
    </form>

    <h2>Список отделов</h2>
    <table border="1">
        <tr>
            <th>ID</th>
            <th>Название</th>
            <th>Сотрудников</th>
            <th>Перейти к сотрудникам</th>
            <th>Действия</th>
        </tr>
        <c:forEach var="dept" items="${departments}">
            <tr>
                <td>${dept.id}</td>
                <td>${dept.name}</td>
                <td>${dept.employeeCount}</td>
                <td><a href="${pageContext.request.contextPath}/employees?department_id=${dept.id}">Сотрудники предприятия</a></td>
                <td>
                    <form action="${pageContext.request.contextPath}/departments"
                          method="post" style="display:inline">
                        <input type="hidden" name="_method" value="DELETE"/>
                        <input type="hidden" name="id" value="${dept.id}"/>
                        <button type="submit">Удалить</button>
                    </form>

                    <form action="${pageContext.request.contextPath}/departments"
                          method="post" style="display:inline">
                        <input type="hidden" name="_method" value="PUT"/>
                        <input type="hidden" name="id" value="${dept.id}"/>
                        <input type="text" name="name" value="${dept.name}" required/>
                        <button type="submit">Изменить</button>
                    </form>
                </td>
            </tr>
        </c:forEach>
    </table>
</body>
</html>
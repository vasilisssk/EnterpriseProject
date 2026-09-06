<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Сотрудники</title>
</head>
<body>
    <h1>Сотрудники предприятия</h1>

    <h2>Добавить сотрудника</h2>
    <form action="${pageContext.request.contextPath}/employees" method="post">
        <input type="text" name="full_name" placeholder="ФИО" required/>
        <input type="number" name="age" placeholder="Возраст" required/>
        <input type="number" name="salary" placeholder="Зарплата" required/>
        <input type="hidden" name="department_id" value="${departmentId}" required/>
        <button type="submit">Добавить</button>
    </form>

    <h2>Список сотрудников</h2>
    <p>Сумма зарплат: ${totalSalary}</p>
    <table border="1">
        <tr>
            <th>ID</th>
            <th>ФИО</th>
            <th>Возраст</th>
            <th>Зарплата</th>
            <th>ID отдела</th>
            <th>Действия</th>
        </tr>
        <c:forEach var="emp" items="${employees}">
            <tr>
                <td>${emp.id}</td>
                <td>${emp.fullName}</td>
                <td>${emp.age}</td>
                <td>${emp.salary}</td>
                <td>${emp.departmentId}</td>
                <td>
                    <form action="${pageContext.request.contextPath}/employees"
                          method="post" style="display:inline">
                        <input type="hidden" name="_method" value="DELETE"/>
                        <input type="hidden" name="id" value="${emp.id}"/>
                        <button type="submit">Удалить</button>
                    </form>

                    <form action="${pageContext.request.contextPath}/employees"
                          method="post" style="display:inline">
                        <input type="hidden" name="_method" value="PUT"/>
                        <input type="hidden" name="id" value="${emp.id}"/>
                        <input type="text" name="full_name" placeholder="ФИО" value="${emp.fullName}" required/>
                        <input type="number" name="age" placeholder="Возраст" value="${emp.age}" required/>
                        <input type="number" name="salary" placeholder="Зарплата" value="${emp.salary}" required/>
                        <input type="number" name="department_id" placeholder="ID отдела" value="${emp.departmentId}" required/>
                        <button type="submit">Изменить</button>
                    </form>
                </td>
            </tr>
        </c:forEach>
    </table>

    <br/>
    <a href="${pageContext.request.contextPath}/departments">Перейти к отделам</a>
</body>
</html>
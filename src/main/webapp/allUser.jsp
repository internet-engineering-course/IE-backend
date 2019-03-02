<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Users</title>
    <style>
        table {
            text-align: center;
            margin: 0 auto;
        }
        td {
            min-width: 300px;
            margin: 5px 5px 5px 5px;
            text-align: center;
        }
    </style>
</head>
<body>

<table>
    <tbody>
    <tr>
        <th>id</th>
        <th>name</th>
        <th>jobTitle</th>
    </tr>
    <c:if test="${size > 0}">
        <c:forEach var="user" items="${users}">
            <tr>
                <td><c:out value="${user.id}"/></td>
                <td><c:out value="${user.firstname} ${user.lastname}"/></td>
                <td><c:out value="${user.jobTitle}"/></td>
            </tr>
        </c:forEach>
    </c:if>
    </tbody>
</table>
</body>
</html>
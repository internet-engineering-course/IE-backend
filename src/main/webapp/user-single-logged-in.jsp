<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>User</title>
</head>
<body>
<ul>
    <li>id: <c:out value="${requestScope.user.id}"/></li>
    <li>first name: <c:out value="${requestScope.user.firstname}"/></li>
    <li>last name: <c:out value="${requestScope.user.lastname}"/></li>
    <li>jobTitle: <c:out value="${requestScope.user.jobTitle}"/></li>
    <li>bio: <c:out value="${requestScope.user.bio}"/></li>
    <li>
        skills:
        <ul>
            <c:forEach var="skill" items="${requestScope.user.skills}">
                <li>
                    <c:out value="${skill.name}"/>
                    <form action="" method="">
                        <button>Delete</button>
                    </form>
                </li>
            </c:forEach>
        </ul>
    </li>
</ul>
Add Skill:
<form action="" method="">
    <select title="Skill">
        <c:forEach var="skill" items="${requestScope.skills}">
            <option value=<c:out value="${skill.name}"/>><c:out value="${skill.name}"/></option>
        </c:forEach>
    </select>
    <button>Add</button>
</form>
</body>
</html>
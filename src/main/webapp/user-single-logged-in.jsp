<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>User</title>
</head>
<body>
<ul>
    <li>Id: <c:out value="${requestScope.user.id}"/></li>
    <li>First Name: <c:out value="${requestScope.user.firstname}"/></li>
    <li>Last Name: <c:out value="${requestScope.user.lastname}"/></li>
    <li>Job Title: <c:out value="${requestScope.user.jobTitle}"/></li>
    <li>Bio: <c:out value="${requestScope.user.bio}"/></li>
    <li>
        Skills:
        <ul>
            <c:forEach var="skill" items="${requestScope.user.skills}">
                <li>
                    <c:out value="${skill.name}"/>
                    <form action="/skill/delete" method="post">
                        <input type="hidden" name="skillName" value=<c:out value="${skill.name}"/>>
                        <input type="submit" value="Delete">
                    </form>
                </li>
            </c:forEach>
        </ul>
    </li>
</ul>
Add Skill:
<form action="/skill/add" method="post">
    <select title="skillName" name="skillName">
        <c:forEach var="skill" items="${requestScope.skills}">
            <option value=<c:out value="${skill.name}"/>><c:out value="${skill.name}"/></option>
        </c:forEach>
    </select>
    <input type="submit" value="Add">
</form>
</body>
</html>
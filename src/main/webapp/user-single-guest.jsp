<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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
            <c:forEach var="eskill" items="${requestScope.skills}">
                <li>
                    <c:out value="${eskill.skill.name}"/>: <c:out value="${eskill.skill.point}"/>
                    <c:choose>
                        <c:when test="${eskill.endorsable}">
                            <form action="/endorse" method="post">
                                <input type="hidden" name="endorsedId" value=<c:out value="${requestScope.user.id}"/>>
                                <input type="hidden" name="skillName" value=<c:out value="${eskill.skill.name}"/>>
                                <input type="submit" value="Endorse">
                            </form>
                        </c:when>
                        <c:otherwise>
                        </c:otherwise>
                    </c:choose>
                </li>
            </c:forEach>
        </ul>
    </li>
</ul>
</body>
</html>
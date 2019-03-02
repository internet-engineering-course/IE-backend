<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>

<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Project</title>
</head>
<body>

<ul>
    <li><c:out value="${user.id}"/></li>
    <li><c:out value="${user.firstname}"/></li>
    <li><c:out value="${user.lastname}"/></li>
    <li><c:out value="${user.jobTitle}"/></li>
    <li><c:out value="${user.bio}"/></li>
</ul>

</body>
</html>
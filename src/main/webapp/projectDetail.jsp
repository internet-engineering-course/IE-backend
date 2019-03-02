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
    <li><c:out value="${project.id}"/></li>
    <li><c:out value="${project.title}"/></li>
    <li><c:out value="${project.description}"/></li>
    <li>imageUrl: <img src="<c:out value="${project.imageUrl}"/>" style="width: 50px; height: 50px;"></li>
    <li><c:out value="${project.budget}"/></li>
</ul>

<c:if test="${isBidBefore}">
    <li>You're Bid Amount is <c:out value="${amount}"/></li>
</c:if>
<c:if test="${not isBidBefore}">
<form action="/bid" method="post">
    <label title="bid">Bid Amount:</label>
    <input type="number" name="bidAmount">
    <input type="hidden" name="projectId" value= <c:out value="${project.id}"/> />
    <button>Submit</button>
</form>
</c:if>
</body>
</html>
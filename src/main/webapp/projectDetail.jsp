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
<form action="" method="">
    <label for="bidAmount">Bid Amount:</label>
    <input type="number" name="bidAmount">

    <button>Submit</button>
</form>
</body>
</html>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>User</title>
</head>
<body>
<ul>
    <li>id: 1</li>
    <li>first name: ...</li>
    <li>last name: ...</li>
    <li>jobTitle: ...</li>
    <li>bio: ...</li>
    <li>
        Skills:
        <ul>
            <li>
                HTML: 2
                <form action="" method="">
                    <button>Endorse</button>
                </form>
            </li>
            <li>
                Java: 1
                <form action="" method="">
                    <button>Endorse</button>
                </form>
            </li>
            <li>
                CSS: 2
                <!-- no form if already endorsed -->
            </li>
        </ul>
    </li>
</ul>
</body>
</html>
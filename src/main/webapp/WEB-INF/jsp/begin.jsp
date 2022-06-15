
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>WEB Игра "Города"</title>
</head>
<body class="center">
<p>Стартуем!</p>
<p>Я называю город:</p>
<p>${firstCityName}</p>
<div>
    <form method="get" action="<c:url value="/next"/>">
        <label for="name">Назовите город: </label>
        <input id="name" type="text" name="userCityName" maxlength="50" required/>
        <input type="submit" value="Назвать">
        <input type="hidden" name="serverCityNamePrevious" value="${firstCityName}">
    </form>

    <button onclick="location.href='/begin'">Начать игру</button>
</div>
</body>
</html>


<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>WEB Игра "Города"</title>
</head>
<body class="center">

<p>Вы назвали город: </p>
<p>${userCityNamePrevious}</p>

<c:if test="${error == null}">
    <p></p>
    <p>Я называю город:</p>
    <p>${serverCityName}</p>
</c:if>
<c:if test="${error != null}">
    <p>${error}</p>
    <p>ранее я назвал город:</p>
    <p>${serverCityName}</p>
</c:if>
<div>
    <form method="get" action="<c:url value="/next"/>">
        <label for="nam">Назовите город: </label>
        <input id="nam" type="text" name="userCityName" maxlength="50" required/>
        <input type="submit" value="Назвать">
        <input type="hidden" name="serverCityNamePrevious" value="${serverCityName}">
    </form>

    <button onclick="location.href='/begin'">Начать игру</button>
</div>
</body>
</html>

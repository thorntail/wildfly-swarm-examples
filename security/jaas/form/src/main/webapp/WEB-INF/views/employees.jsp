<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta charset="UTF-8">
    <title>JAAS Form Example</title>
</head>
<body>
<h1>Employees</h1>
<c:forEach var="employee" items="${employees}">
    <div>
        <c:out value="${employee.id}: ${employee.name}"/>
    </div>
</c:forEach>
</body>
</html>
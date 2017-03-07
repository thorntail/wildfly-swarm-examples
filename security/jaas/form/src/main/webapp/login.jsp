<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta charset="UTF-8">
    <title>JAAS Form Example</title>
</head>
<body>
<h1>Login</h1>
<form method="post" action='<%= response.encodeURL("j_security_check")%>'>
    <table>
        <tr>
            <td>username</td>
            <td><input name="j_username" type="text"></td>
        </tr>
        <tr>
            <td>password</td>
            <td><input name="j_password" type="password"></td>
        </tr>
    </table>
    <div>
        <input name="submit" type="submit" value="Login">
        <input name="reset" type="reset" value="Reset">
    </div>
</form>

</body>
</html>
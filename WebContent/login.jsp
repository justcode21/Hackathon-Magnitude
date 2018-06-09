<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Login</title>
</head>
<body>
<form action="Login" method = "POST">
LoginId : <input type="text" name="loginId" required="required">
Password : <input type="text" name="password" required="required">
<input type="submit" value ="Login">
</form>

<form action="registration.jsp" method="get">
    <button>New User</button>
</form>
</body>
</html>
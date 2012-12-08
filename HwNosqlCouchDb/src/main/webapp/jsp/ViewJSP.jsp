<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Welcome</title>
</head>

<body>
<form action="view.do">
<div style="width: 400px; font-family: Arial; font-size: 9pt;">
<table>
	<tr>
		<td colspan="2" style="padding-bottom: 10px;">
			<h2>Hello World - Data Base Web Application</h2>
			<h3>Site: <b>View</b></h3>
		</td>
	</tr>		
	<tr>
		<td>Your name is:</td>
		<td><input type="text" id="userName" name="userName" value="${requestScope.userName}" size="10" readonly="readonly"/></td>
	</tr>
	<tr>
		<td colspan="2"><input type="submit" id="submit" name="submit" value="Back"/></td>
	</tr>
</table>
</div>

</form>
</body>
</html>
<%@page import="org.social.facebook.FacebookConnect"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%
	FacebookConnect fbConnection = new FacebookConnect();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Java Facebook Login</title>
</head>
<body style="text-align: center; margin: 0 auto;">
	<div>
		<a href="<%=fbConnection.getFBAuthUrl()%>"> 
		<img
			style="margin-top: 138px;" src="./img/facebookloginbutton.png" />
		</a> 
		<br> <a href="<%=fbConnection.getGithubAuthUrl()%>"> <img
			style="margin-top: 138px;" src="./img/github.png" />
		</a> 
		<br> <a href="<%=fbConnection.getInstagramAuthUrl()%>"> <img
			style="margin-top: 138px;" src="./img/instagram-login-button.png" />
		</a>
	</div>
</body>
</html>

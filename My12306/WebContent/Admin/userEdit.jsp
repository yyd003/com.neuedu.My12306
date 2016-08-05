<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">
<title>用户注册</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<script type="text/javascript" src="js/jquery-1.8.0.js"></script>
<script type="text/javascript" src="js/userEdit.js"></script>
</head>

<body>
	<div id="editWindow"
		style="width: 250;height: 300;border-color: gray;margin: auto">
		<div id="title" align="center"></div>
		<hr>
		<input type="hidden" name="uId" id="uId" value="${userData.user.uId}">
		用户名： <input type="text" name="username" id="username"
			disabled="disabled" value="${userData.user.username}"> <span
			id="usernameCheck"></span> <br> 中文名： <input type="text"
			name="realname" id="realname" value="${userData.user.realname}">
		<span id="realnameCheck"></span> <br> 密码： <input type="password"
			name="password" id="password" maxlength="20"
			value="${userData.user.password}"> <span id="passwordCheck"></span>
		<br> 确认密码： <input type="password" name="rePassword"
			id="rePassword" maxlength="20" value="${userData.user.password}">
		<span id="rePasswordCheck"></span> <br> 手机号码： <input type="text"
			maxlength="11" name="mobile" id="mobile"
			value="${userData.user.mobile}"> <span id="mobilCheck"></span>
		<br> Email: <input type="text" name="email" id="email"
			value="${userData.user.email}"> <span id="emailCheck"></span>
		<br>
		<div align="center">
			<input type="button" value="提交" id="register"> <input
				type="button" value="取消" id="cancel"
				onclick="javascript:history.go(-1);">
		</div>
	</div>
</body>
</html>

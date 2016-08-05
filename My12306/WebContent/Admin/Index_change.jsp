<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>管理员管理界面</title>
<link rel="stylesheet" href="../css/div+css.css">
<script src="../js/jquery.min.js"></script>
<script src="../js/adminIndex.js"></script>
</head>
<body>
	<!-- header -->
	<div id="header" class="box">header message</div>
	<!-- leftmenu -->
	<div id="menu" class="box fl">
		<a href="admin?action=show" target="frame">用户管理</a>
	</div>
	<!-- content -->
	<div id="content" class="box fr">
		<iframe width=100% height=100% name=frame frameborder=0
			src="main.jsp" scrolling="auto"></iframe>
	</div>
	<!-- footer -->
	<div id="footer" class="box cl">copyright</div>
</body>
</html>

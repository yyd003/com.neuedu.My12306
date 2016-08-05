<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
<title>用户管理</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<script type="text/javascript" src="../js/jquery-1.8.0.js"></script>
<script type="text/javascript" src="../js/adminIndex.js"></script>

<link rel="stylesheet" type="text/css" href="css/manage.css">

</head>

<body>
	<div id="userManage" style="margin-left: 20">
		<div id=title align="center">
			<h2>用户管理</h2>
		</div>
		<div id="content">
			<div id="fnArea">
				<input type="button" value="添加" name="add" id="add" /> <input
					type="button" value="批量删除" name="delete" id="delete" /> <input
					type="button" value="修改" name="modify" id="modify" /><input
					type="text" id="txtFind" placeholder="真实名（支持模糊查询）" /> <input
					type="button" value="查询" id="find" /> <input type="button"
					value="刷新" id="refresh" /><input type="button" value="导出到Excel..."
					id="toExcel" /><input type="button" value="从Excel导入..."
					id="fromExcel" />
			</div>
			<table width=100%>
				<tr id="head">
					<td align="center"><span id="selAll" class="sel">全选 </span><span
						id="selOther" class="sel">反选 </span><span id="selNo" class="sel">不选</span>
					</td>
					<td>用户ID</td>
					<td>用户名</td>
					<td>真实名</td>
					<td>密码</td>
					<td>联系电话</td>
					<td>电子邮箱</td>
				</tr>
				<c:forEach items="${data.users}" var="s" varStatus="s1">
					<tr id="content">

						<td><c:if test='${s.uId ne "2"}'>
								<input type="checkbox" name="sUid" id="cUid" value="${s.uId}">
							</c:if></td>
						<td><c:out value="${s.id}" /></td>
						<td><c:out value="${s.username}" /></td>
						<td><c:out value="${s.realname}" /></td>
						<td><c:out value="${s.password}" /></td>
						<td><c:out value="${s.mobile}" /></td>
						<td><c:out value="${s.email}" /></td>
					</tr>
				</c:forEach>
			</table>
		</div>
	</div>
</body>
</html>

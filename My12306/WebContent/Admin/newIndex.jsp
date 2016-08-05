<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8" />
<title>我的12306 ｜ 系统管理</title>
<link rel="stylesheet" href="../bootstrap/css/bootstrap.min.css">
<script src="../bootstrap/js/jquery.min.js"></script>
<script src="../bootstrap/js/bootstrap.min.js"></script>
<script src="../js/admin.js"></script>
</head>
<body>
	<div class="container">
		<div class="row clearfix">
			<div class="col-md-12 column">
				<img alt="140x140" src="../images/ny_top_img.gif" />
			</div>
		</div>
		<div class="row clearfix">
			<div class="col-md-4 column">
				<h5 class="text-error text-center">
					欢迎您：<strong style="color: red">${user.realname}</strong>
				</h5>
			</div>
			<div class="col-md-4 column">
				<h5 class="text-error text-center">今天是：2016年8月1日</h5>
			</div>
			<div class="col-md-4 column">
				<h5 class="text-error text-center">退出</h5>
			</div>
		</div>
		<div class="row clearfix">
			<div class="col-md-2 column">
				<div class="panel-group" id="panel-169798">
					<div class="panel panel-default">
						<div class="panel-heading">
							<a class="panel-title collapsed" data-toggle="collapse"
								data-parent="#panel-169798" href="#panel-element-464002">用户管理</a>
						</div>
						<div id="panel-element-464002" class="panel-collapse collapse">
							<div class="panel-body">用户数据维护</div>
							<div class="panel-body">ＩＰ统计</div>
						</div>
					</div>
					<div class="panel panel-default">
						<div class="panel-heading">
							<a class="panel-title collapsed" data-toggle="collapse"
								data-parent="#panel-169798" href="#panel-element-464001">车次管理</a>
						</div>
						<div id="panel-element-464001" class="panel-collapse collapse">
							<div class="panel-body">车次数据维护</div>
							<div class="panel-body">车站信息维护</div>
						</div>
					</div>
					<div class="panel panel-default">
						<div class="panel-heading">
							<a class="panel-title collapsed" data-toggle="collapse"
								data-parent="#panel-169798" href="#panel-element-773034">订单管理</a>
						</div>
						<div id="panel-element-773034" class="panel-collapse collapse">
							<div class="panel-body">订单数据维护</div>
							<div class="panel-body">订单图表分析</div>
						</div>
					</div>
				</div>
			</div>
			<div class="col-md-10 column" id="mainContent">
				<!--查询条件区 -->
				<div class="col-md-12 column">
					<div class="col-md-3 column">
						<input type="text" class="form-control" id="fusername"
							placeholder="用户名">
					</div>
					<div class="col-md-3 column">
						<input type="text" class="form-control" id="frealname"
							placeholder="真实名">
					</div>
					<div class="col-md-3 column">
						<input type="text" class="form-control" id="fcert"
							placeholder="证件号码">
					</div>
					<div class="col-md-3 column">
						<input type="text" class="form-control" id="fcontent"
							placeholder="备注">
					</div>
				</div>
				<div class="col-md-12 column">
					<div class="col-md-3 column">
						<select name="fsex" id="fsex" class="form-control">
							<option value="">请选择性别</option>
							<option value="1">男</option>
							<option value="0">女</option>
						</select>
					</div>
					<div class="col-md-3 column">
						<select name="fcerttype" id="fcerttype" class="form-control">
							<option value="">请选择证件类型</option>
							<c:forEach items="${certTypeData.data}" var="s" varStatus="s1">
								<option value="<c:out value="${s.id}" />"><c:out
										value="${s.content}" />
								</option>
							</c:forEach>
						</select>
					</div>
					<div class="col-md-3 column">
						<select name="fusertype" id="fusertype" class="form-control">
							<option value="">请选择用户类型</option>
							<c:forEach items="${userTypeData.data}" var="s" varStatus="s1">
								<option value="<c:out value="${s.id}" />"><c:out
										value="${s.content}" />
								</option>
							</c:forEach>
						</select>
					</div>
					<div class="col-md-3 column">
						<select name="fstatus" id="fstatus" class="form-control">
							<option value="">请选择用户状态</option>
							<option value="1">正常</option>
							<option value="0">禁用</option>
						</select>
					</div>
				</div>
				<div class="col-md-12 column">
					<div class="col-md-3 column">
						<select name="fprovince" id="fprovince" class="form-control">
							<option value="">请选择省份</option>
						</select>
					</div>
					<div class="col-md-3 column">
						<select name="fcity" id="fcity" class="form-control">
						</select>
					</div>
					<div class="col-md-3 column">
						<button type="button" class="btn btn-warning" id="umFindBtn">查询</button>
					</div>
				</div>
				<!--功能操作区 -->
				<div class="btn-group">
					<button type="button" class="btn btn-info" id="umAddBtn">添加</button>
					<button type="button" class="btn btn-success" id="umModiBtn">修改</button>
					<button type="button" class="btn btn-danger" id="umDelBtn">删除</button>
				</div>
				<!--数据显示区 -->
				<table class="table table-hover table-condensed">
					<thead>
						<tr>
							<th>选择</th>
							<th>编号</th>
							<th>用户名</th>
							<th>状态</th>
							<th>真实名</th>
							<th>性别</th>
							<!--<th>出生日期</th> -->
							<th>证件类型</th>
							<th>证件号</th>
							<th>旅客类型</th>
							<th>所在城市</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${data.users}" var="s" varStatus="s1">
							<tr>
								<td><c:if test='${s.username ne "admin111"}'>
										<input type="checkbox" name="sUid" id="cUid" value="${s.id}">
									</c:if></td>
								<td><c:out value="${s.id}" /></td>
								<td><c:out value="${s.username}" /></td>
								<td><c:if test="${s.status=='1'}">
										<span style="color: blue">正常</span>
									</c:if> <c:if test="${s.status=='0'}">
										<span style="color: red">禁用</span>
									</c:if></td>
								<td><c:out value="${s.realname}" /></td>
								<td><c:if test="${s.sex=='1'}">男</c:if> <c:if
										test="${s.sex=='0'}">女</c:if></td>
								<%--<td class="text-sm"><c:out value="${s.birthday}" /></td> --%>
								<td><c:out value="${s.certType.content}" /></td>
								<td><c:out value="${s.cert}" /></td>
								<td><c:out value="${s.userType.content}" /></td>
								<td><c:out value="${s.city.city}" /></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
				<ul class="pagination">
					<c:forEach items="${data.pages}" var="s" varStatus="s1">
						<li><a href="admin?action=show&page=${s}">${s}</a></li>
					</c:forEach>
					每页显示
					<select id="npp">
						<option value="5">5</option>
						<option value="10">10</option>
					</select>
				</ul>
			</div>
		</div>
		<!--版权广告区 -->
		<div class="row clearfix">
			<div class="col-md-12 column">
				<div class="row clearfix">
					<div class="col-md-4 column">
						<ol>
							<li>广告合作</li>
							<li>地区代理</li>
						</ol>
					</div>
					<div class="col-md-4 column">
						<ul>
							<li>公司介绍</li>
							<li>新闻公告</li>
							<li>人才培训计划</li>
							<li>京ICP备15003716号-3</li>
						</ul>
					</div>
					<div class="col-md-4 column">
						关注我们 <img alt="140x140" src="../images/photo/12306.jpg" />
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
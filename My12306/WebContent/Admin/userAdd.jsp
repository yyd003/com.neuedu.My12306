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
<script>
	var headpic = document.getElementById("headpic");
	//阻止事件蔓延
	headpic.ondragover = handleDrag;
	function handleDrag(e) {
		e.preventDefault();
	}
	//图片在框中放下时触发
	headpic.ondrop = function(e) {
		var files = e.dataTransfer.files;
		//显示这个图片为div的背景
		var r = new FileReader();
		r.onload = function(re) {
			headpic.innerHTML = "";
			headpic.style.backgroundImage = "url(" + re.target.result + ")";
		}
		r.readAsDataURL(files[0]);
		e.preventDefault();
	}
</script>
</head>
<body>
	<!-- 这是一个表单，保证其每个要传送的组件都有明确的name,id　 -->
	<form class="form-horizontal">
		<div id="title" align="center"></div>
		<hr>
		<div class="form-group">
			<label for="username" class="col-sm-2 control-label">用户名</label>
			<div class="col-sm-6">
				<input type="hidden" name="uId" id="uId" value="${data.userData.id}">
				<input type="text" class="form-control" name="username"
					id="username" value="${data.userData.username}" placeholder="请输入名字"><span
					id="uCheck"></span>
			</div>
		</div>
		<div class="form-group">
			<label for="realname" class="col-sm-2 control-label">真实名</label>
			<div class="col-sm-6">
				<input type="text" class="form-control" name="realname"
					id="realname" value="${data.userData.realname}"
					placeholder="请输入真实名">
			</div>
		</div>
		<div class="form-group">
			<label class="col-sm-2 control-label">请选择角色</label>
			<div class="col-sm-6">
				<select class="form-control" name="rule" id="rule">
					<option value="1" ${data.userData.rule == '1' ? 'selected' : '' }>管理员</option>
					<option value="2" ${data.userData.rule == '2' ? 'selected' : '' }>普通用户</option>
				</select>
			</div>
		</div>
		<div class="form-group">
			<label for="lastname" class="col-sm-2 control-label">性别</label>
			<div class="col-sm-6">
				<select class="form-control" name="sex" id="sex">
					<option value="1" ${data.userData.sex == '1' ? 'selected' : '' }>男</option>
					<option value="0" ${data.userData.sex == '0' ? 'selected' : '' }>女</option>
				</select>
			</div>
		</div>

		<div class="form-group">
			<label for="lastname" class="col-sm-2 control-label">省</label>
			<div class="col-sm-2">
				<select class="form-control" id="province" name="province">
					<%-- 					<c:forEach items="${provinceData.data}" var="s" varStatus="s1"> --%>
					<option
						value="<c:out value="${data.userData.city.province.provinceId}" />"><c:out
							value="${data.userData.city.province.province}" />
					</option>
					<%-- 					</c:forEach> --%>
				</select>
				<button type="button" class="btn　btn-danger" id="changeProvince">更改省份</button>
			</div>
			<label for="lastname" class="col-sm-2 control-label">市</label>
			<div class="col-sm-2">
				<select class="form-control" id="city" name="city">
					<%-- 					<c:forEach items="${provinceData.data}" var="s" varStatus="s1"> --%>
					<option value="<c:out value="${data.userData.city.id}" />"><c:out
							value="${data.userData.city.city}" />
					</option>
					<%-- 					</c:forEach> --%>
				</select>
			</div>
		</div>

		<div class="form-group">
			<label class="col-sm-2 control-label">证件类型</label>
			<div class="col-sm-6">
				<select class="form-control" id="certType" name="certType">
					<c:forEach items="${certTypeData.data}" var="s" varStatus="s1">
						<option value="<c:out value="${s.id}" />"><c:out
								value="${s.content}" />
						</option>
					</c:forEach>
				</select>
			</div>
		</div>

		<div class="form-group">
			<label for="lastname" class="col-sm-2 control-label">证件号码</label>
			<div class="col-sm-6">
				<input type="text" class="form-control" id="cert"
					value="${data.userData.cert}" name="cert" placeholder="请输入证件号码">
			</div>
		</div>

		<div class="form-group">
			<label for="lastname" class="col-sm-2 control-label">出生日期</label>
			<div class="col-sm-6">
				<input type="date" class="form-control" id="birthday"
					value="${data.userData.birthday}" name="birthday">
			</div>
		</div>

		<div class="form-group">
			<label class="col-sm-2 control-label">旅客类型</label>
			<div class="col-sm-6">
				<select class="form-control" id="userType" name="userType">
					<c:forEach items="${userTypeData.data}" var="s" varStatus="s1">
						<option value="<c:out value="${s.id}" />"><c:out
								value="${s.content}" />
						</option>
					</c:forEach>
				</select>
			</div>
		</div>

		<div class="form-group">
			<label class="col-sm-2 control-label">备注</label>
			<div class="col-sm-6">
				<textarea class="form-control" rows="3" id="content"
					value="${data.userData.content}" name="content"></textarea>
			</div>
		</div>

		<div class="form-group">
			<label for="lastname" class="col-sm-2 control-label">头像</label>
			<div class="col-sm-6">
				<div id="headpic" class="form-control"
					style="width: 100px; height: 100px; background-size: 100px 100px;">
					<p>将您本地的头像图片文件拖拽到这里</p>
				</div>
			</div>
		</div>

		<div class="form-group">
			<div class="col-sm-offset-1 col-sm-7">
				<button type="button" class="btn btn-danger btn-large btn-block"
					id="uSaveBtn">保存</button>
			</div>
		</div>
	</form>

</body>
</html>
//登录界面各输入框的验证代码
$(function() {
	// 取得各对象 用户管理界面的三个按钮
	var umAddBtn = $("#umAddBtn");
	var umModiBtn = $("#umModiBtn");
	var umDelBtn = $("#umDelBtn");
	var umFindBtn = $("#umFindBtn");

	var uCheck = $("#uCheck");

	var uId = $("#uId");
	var title = $("#title");

	// 用户编辑页面的“保存”按钮
	var uSaveBtn = $("#uSaveBtn");
	// 更改省份按钮
	var changeProvinceObj = $("#changeProvince");

	// 用户名输入框，要异步检测用户名是否重名
	var usernameObj = $("#username");
	var provinceObj = $("#province");
	var cityObj = $("#city");

	// 拖动显示图片
	 var headpic = $("#headpic");
//	 headpic.draggable("enable");
	// headpic.droppable({
	// over : function(e) {
	// alert("drop");
	// },
	// drop : function(e) {
	// alert("drop");
	// }
	// })
	// var headpic = document.getElementById("headpic");
	// // 阻止事件蔓延
	// headpic.ondragover = handleDrag;
	// function handleDrag(e) {
	// e.preventDefault();
	// }
	// // 图片在框中放下时触发
	// headpic.ondrop = function(e) {
	// var files = e.dataTransfer.files;
	// // 显示这个图片为div的背景
	// var r = new FileReader();
	// r.onload = function(re) {
	// headpic.innerHTML = "";
	// headpic.style.backgroundImage = "url(" + re.target.result + ")";
	// }
	// r.readAsDataURL(files[0]);
	// e.preventDefault();
	//	}
	// 编辑页面的标题，新增或修改
	if (uId.val() != 0) {
		usernameObj.attr("disabled", true);
		title.html("编     辑");
	} else {
		usernameObj.attr("disabled", false);
		title.html("新    增");
	}

	$("#npp").change(function() {
		location.href = "admin?action=show&pageSize=" + $(this).val();
	});

	// 查询按钮功能
	umFindBtn.click(function() {
		var findStr = '';
		var username = $("#fusername").val();
		var realname = $("#frealname").val();

		var cert = $("#fcert").val();
		var content = $("#fcontent").val();

		var sex = $("#fsex").val();
		var userType = $("#fusertype").val();
		var certType = $("#fcerttype").val();

		var status = $("#fstatus").val();

		if (username != "") {
			findStr += '&username=' + username;
		}

		if (realname != "") {
			findStr += '&realname=' + realname;
		}

		if (cert != "") {
			findStr += '&cert=' + cert;
		}

		if (content != "") {
			findStr += '&content=' + content;
		}

		if (sex != "") {
			findStr += '&sex=' + sex;
		}

		if (userType != "") {
			findStr += '&userType=' + userType;
		}

		if (certType != "") {
			findStr += '&certType=' + certType;
		}
		if (status != "") {
			findStr += '&status=' + status;
		}

		location.href = 'admin?action=show' + findStr;
	});

	// 异步请求省份信息
	changeProvinceObj.click(function() {
		$.ajax({// 异步调用
			type : 'post',
			url : '../register?action=findProvince',// 传参１ 请求的servlet名；动作参数(
			// Ｋ：Ｖ)
			data : {
			// 传参２ 你要传送给servlet的数据(Ｋ：Ｖ)，如果多个，有，分隔，最后一个参数不要加逗号
			// 尤其注意Ｋ是servlet将要获取的 ，如 request.getParameter("provinceId")，如果请
			// 收双方这个名称不一致，将导致
			// servlet不能正确收到你要传送的这个驮轿
			// ，而Ｖ是本请求页面中对象的取值
			// provinceId : provinceObj.val()

			},
			dataType : 'json',
			// 正确请求后的回调函数 从servlet中正确返回的内容data在入参中，只要正确解析即可
			success : function(data) {
				provinceObj.html("");
				var userList = data.data;
				var s = '';
				for (var i = 0; i < userList.length; i++) {
					s += '<option value=' + userList[i].provinceId + '>'
							+ userList[i].province + '</option>';
				}
				provinceObj.append(s);
			},
			error : function() {
				alert("省信息获取异常");
			}
		});
	});

	provinceObj.click(function() {
		// alert($(this).val());
	});

	// 省份下拉发生变化时，异步请求关联的市区信息，将市区信息添加到市区下拉列表控件
	// 请特别注意你的传参，如果传参不对，请求肯定失败
	provinceObj.change(function() {
		$.ajax({// 异步调用
			type : 'post',
			url : '../register?action=findCity',// 传参１ 请求的servlet名；动作参数( Ｋ：Ｖ)
			data : {
				// 传参２ 你要传送给servlet的数据(Ｋ：Ｖ)，如果多个，有，分隔，最后一个参数不要加逗号
				// 尤其注意Ｋ是servlet将要获取的 ，如 request.getParameter("provinceId")，如果请
				// 收双方这个名称不一致，将导致
				// servlet不能正确收到你要传送的这个驮轿
				// ，而Ｖ是本请求页面中对象的取值
				provinceId : provinceObj.val()

			},
			dataType : 'json',
			// 正确请求后的回调函数 从servlet中正确返回的内容data在入参中，只要正确解析即可
			success : function(data) {
				$("#city").html("");
				var userList = data.data;
				var s = '';
				for (var i = 0; i < userList.length; i++) {
					s += '<option value=' + userList[i].id + '>'
							+ userList[i].city + '</option>';
				}
				cityObj.append(s);
			},
			error : function() {
				alert("省市联动异常");
			}
		});
	});

	// 编辑页面“保存”按钮的单击事件
	uSaveBtn.click(function() {
		// ajax去处理添加或修改, 根据页面uId隐藏域是否有值，判断是增加还是更新
		if ($("#uId").val() == 0)
			doAdd();
		else {
			doUpdate();
		}
	});

	// 管理页面中“添加”按钮的单击事件，只是调出添加页面
	umAddBtn.click(function() {
		// 在div中装载页面
		$("#mainContent").load("userAdd.jsp");
	});

	// 用户名框输入完毕离开后，ajax验证是否有同名用户的存在
	// {"data":[{key:value,key1:v1,k2:v2},{},{}}
	usernameObj.blur(function() {
		$.ajax({// 验证用户名是否存在
			type : 'post',
			url : '../register?action=check',
			data : {
				username : usernameObj.val()
			},
			dataType : 'json',
			success : function(data) {
				if (data.flag == "yes") {
					uCheck.html("此用户名已被占用，请重新选择用户名！").css({
						color : "red"
					});
					usernameObj.focus();
				} else {
					uCheck.html("此用户名可以注册！").css({
						color : "blue"
					});
				}
			},
			error : function() {
				alert("用户名检测异常");
			}
		});
	});
	// 编辑页面的更新按钮事件
	function doUpdate() {
		$.ajax({
			type : 'post',
			url : 'admin?action=update',
			data : {
				id : uId.val(),
				username : usernameObj.val(),
				realname : $("#realname").val(),
				rule : $("#rule").val(),
				sex : $("#sex").val(),
				city : $("#city").val(),
				certType : $("#certType").val(),
				cert : $("#cert").val(),
				birthday : $("#birthday").val(),
				userType : $("#userType").val(),
				content : $("#content").val()
			},
			dataType : 'json',
			success : function(data) {
				if (data.flag == "yes") {
					alert("修改成功");
					doRefresh();
				}
			},
			error : function() {
				alert("修改异常");
			}
		});
	}

	// delete
	umDelBtn.click(function() {// 输出选中的值
		var ids = [];// 定义一个数组
		$('input[name="sUid"]:checked').each(function() {// 遍历每一个名字为sUid的复选框，其中选中的执行函数
			ids.push($(this).val());// 将选中的值添加到数组ids中["3","4","25"]
		});
		if (ids.length == 0) {
			alert("请选择至少一条记录");
		} else {
			if (confirm("确定要删除\n" + ids + "\n共" + ids.length + "条记录？")) {
				doDel(ids);
			} else {// 取消选择，防止在有选择的情况下产生误操作
				$('input[name="sUid"]').attr("checked", false);
			}
		}
	});

	// 管理页面上的编辑按钮事件，只记载ＩＤ，传送到servlet，以便让servlet找到
	// 那条记录，将信息填充到编辑页面。等待编辑
	umModiBtn.click(function() {
		var ids = [];// 定义一个数组
		$('input[name="sUid"]:checked').each(function() {// 遍历每一个名字为sUid的复选框，其中选中的执行函数
			ids.push($(this).val());// 将选中的值添加到数组ids中
		});
		if (ids.length != 1) {
			alert("请选择一条记录");
		} else {
			location.href = "admin?action=findAndPush&id=" + ids[0];
		}
	});

	// doDel
	function doDel(ids) {
		// alert(ids.toString()); // 5,8,23
		$.ajax({
			type : 'post',
			url : 'admin?action=del',
			data : {
				// ids为字符串数组
				ids_del : ids.toString()
			// "3,4,25"
			},
			dataType : 'json',
			success : function(data) {
				if (data.flag == "yes") {
					alert("删除成功");
					doRefresh();
				} else {
					alert("删除失败");
				}
			},
			error : function() {
				alert("删除异常!!!");
			}
		});
	}

	// doRefresh
	function doRefresh() {
		// 重新发数据请求，相当于刷新
		location.href = "admin?action=show";
	}

	// add
	function doAdd() {
		$.ajax({
			type : 'post',
			url : 'admin?action=add',
			data : {
				username : usernameObj.val(),
				realname : $("#realname").val(),
				rule : $("#rule").val(),
				sex : $("#sex").val(),
				city : $("#city").val(),
				certType : $("#certType").val(),
				cert : $("#cert").val(),
				birthday : $("#birthday").val(),
				userType : $("#userType").val(),
				content : $("#content").val()
			},
			dataType : 'json',
			success : function(data) {
				if (data.flag == "yes") {
					alert("数据更改成功");
					// 重新发数据请求，相当于刷新
					doRefresh();
				}
			},
			error : function() {
				alert("数据更改异常");
			}
		});
	}

});
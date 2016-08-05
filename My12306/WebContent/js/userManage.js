//登录界面各输入框的验证代码
$(function() {
	// 不选
	$("#selNo").click(function() {
		$('input[name="sUid"]').attr("checked", false);
	});

	// 反选
	$("#selOther").click(function() {
		$('input[name="sUid"]').each(function() {
			$(this).attr("checked", !$(this).attr("checked"));
		});
	});

	// 全选
	$("#selAll").click(function() {
		$('input[name="sUid"]').attr("checked", true);
	});

	// refresh
	$("#refresh").click(function() {
		doRefresh();
	});

	// modify
	$("#modify").click(function() {
		var ids = [];// 定义一个数组
		$('input[name="sUid"]:checked').each(function() {// 遍历每一个名字为sUid的复选框，其中选中的执行函数
			ids.push($(this).val());// 将选中的值添加到数组ids中
		});
		if (ids.length != 1) {
			alert("请选择一条记录");
		} else {
			doModify(ids);
		}
	});

	// find
	$("#find").click(function() {
		doFind();
	});

	// 点击添加按钮的事件
	$("#add").click(function() {
		// window.showModalDialog("userEdit.jsp", null,
		// "dialogWidth:500px;dialogHeight:auto;status:yes;help:no;resizable:no;");
		doAdd();
	});

	// delete
	$("#delete").click(function() {// 输出选中的值
		var ids = [];// 定义一个数组
		$('input[name="sUid"]:checked').each(function() {// 遍历每一个名字为sUid的复选框，其中选中的执行函数
			ids.push($(this).val());// 将选中的值添加到数组ids中
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

	// doModify
	function doModify(ids) {
		location.href = "userManageServlet?method=findUid&uId=" + ids;
	}

	// doAdd
	function doAdd() {
		location.href = "manage/user/userEdit.jsp";
	}

	// doFind
	function doFind() {
		location.href = "userManageServlet?method=find&txtFind="
				+ $("#txtFind").val();
	}

	// doDel
	function doDel(ids) {
		// alert(ids.toString()); // 5,8,23
		$.ajax({
			type : 'post',
			url : 'userManageServlet?method=del',
			data : {
				ids_del : ids.toString()
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
		location.href = "userManageServlet?method=show";
	}
	// hover(in,out)
	$("table tr").hover(function() {
		$(this).addClass("hover");
	}, function() {
		$(this).removeClass("hover");
	});

	// 鼠标变指针
	$(".sel").hover(function() {
		$(this).addClass("select");
	}, function() {
		$(this).removeClass("select");
	});

});
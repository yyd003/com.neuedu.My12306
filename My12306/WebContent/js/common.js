var cityObj = document.getElementById('city');
var usernameObj = document.getElementById("username");
// ajax异步处理
var req = false;// 是否重名的异步对象
var req1 = false;// 查询市列表返回的异步对象
function processRequest() {
	if (window.XMLHttpRequest) {
		req = new XMLHttpRequest();
	} else if (window.ActiveXObject) {
		req = new ActiveXObject("Microsoft.XMLHTTP");
	}

	if (req) {
		var url = "register?action=check";
		// POST方式
		req.open("post", url, false);
		// POST方式需要自己设置http的请求头
		req.setRequestHeader("Content-Type",
				"application/x-www-form-urlencoded");
		req.onreadystatechange = updatePage;

		// 有点儿像preparedState,先执行，再传参数
		var usernameVal = document.getElementById("username").value;
		req.send("username=" + usernameVal);// username=123 action=check;
	}
}

function updatePage() {
	// alert(req.readyState);
	if (req.readyState == 4) {
		if (req.status == 200) {
			var content = req.responseText;
			var res = eval("(" + content + ")").flag;
			alert(res);
			if (res != "yes") {
				document.getElementById("res").innerHTML = "可以使用";
			} else {
				document.getElementById("res").innerHTML = "已经被占用";
				usernameObj.focus();
			}
		}
	}
}

// 异步调用从父省表找其下子市表
function getCity() {
	if (window.XMLHttpRequest) {
		req1 = new XMLHttpRequest();
	} else if (window.ActiveXObject) {
		req1 = new ActiveXObject("Microsoft.XMLHTTP");
	}

	if (req1) {
		var url = "register?action=findCity";
		// POST方式
		req1.open("post", url, false);
		// POST方式需要自己设置http的请求头
		req1.setRequestHeader("Content-Type",
				"application/x-www-form-urlencoded");
		var provinceId = document.getElementById("province").value;// 123
		req1.onreadystatechange = getCityData;
		// 有点儿像preparedState,先执行，再传参数
		req1.send("provinceId=" + provinceId);// username=123 action=check;
	}
}

function getCityData() {
	if (req1.readyState == 4) {
		if (req1.status == 200) {
			var jsonStr = req1.responseText;
			if (jsonStr != null) {
				// 将json字符串转换成js数组
				var dataArray = eval("(" + jsonStr + ")").data;
				// 清空city列表
				cityObj.innerHTML = "";
				// 添加city列表
				for (var i = 0; i < dataArray.length; i++) {
					var childOption = document.createElement('option');
					childOption.value = dataArray[i].id;
					childOption.text = dataArray[i].city;
					cityObj.options.add(childOption);
				}
			}
		}
	}
}

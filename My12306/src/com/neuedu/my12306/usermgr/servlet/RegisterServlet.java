package com.neuedu.my12306.usermgr.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import com.neuedu.my12306.common.Md5Utils;
import com.neuedu.my12306.common.TextUtils;
import com.neuedu.my12306.usermgr.domain.CertType;
import com.neuedu.my12306.usermgr.domain.City;
import com.neuedu.my12306.usermgr.domain.Province;
import com.neuedu.my12306.usermgr.domain.User;
import com.neuedu.my12306.usermgr.domain.UserType;
import com.neuedu.my12306.usermgr.service.CertTypeService;
import com.neuedu.my12306.usermgr.service.CityService;
import com.neuedu.my12306.usermgr.service.ProvinceService;
import com.neuedu.my12306.usermgr.service.UserService;
import com.neuedu.my12306.usermgr.service.UserTypeService;

/**
 * Servlet implementation class RegisterServlet
 */
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		// 转去处理doPost(request,response);
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		// 数据初始化(证件类型，用户类型，省，市)

		// 接收请求参数
		String action = request.getParameter("action");// "action":"check"
		// 判断是何种请求
		if (null == action || "register".equals(action)) {
			doRegister(request, response);
		} else if ("check".equals(action)) {
			// 这里要处理用户是否同名的检查
			doUsernameCheck(request, response);
		} else if ("init".equals(action)) {
			try {
				dataInit(request, response);
				request.getRequestDispatcher("/Login.jsp").forward(request,
						response);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		} else if ("findCity".equals(action)) {
			try {
				doFindCity(request, response);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if ("findProvince".equals(action)) {
			try {
				provinceDataInit(request, response);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	protected void doUsernameCheck(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username");
		UserService userService = UserService.getInstance();
		JSONObject data = new JSONObject();
		// 构造并填充要查询的对象
		User tmp = new User();
		tmp.setUsername(username);
		// 如果查找到此用户名，此返回yes，告诉对象这个名不能注册
		if (userService.findUser(tmp) != null) {
			data.put("flag", "yes");
		}
		// 将消息输出到请求端
		response.setContentType("text/html;charset=utf-8");
		PrintWriter pw = response.getWriter();
		pw.print(data);
		pw.close();
	}

	protected void doRegister(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		// 取得请求参数
		User user = new User();
		// 将由client传来的各属性值填充到user中，以名这里的代码过长
		populate(request, user);

		// rule(可以放在Service中)
		user.setRule("2");
		// Status(可以放在Service中)
		user.setStatus("1");

		// 服务器端验证
		String msg = validate(user);
		if (TextUtils.isEmpty(msg)) {
			// 调用Service方法
			UserService userService = UserService.getInstance();

			// 检查用户名是否重复
			User tmp = new User();
			tmp.setUsername(user.getUsername());
			User dbUser = userService.findUser(tmp);
			if (dbUser == null) {
				// MD5
				user.setPassword(Md5Utils.md5(user.getPassword()));
				userService.addUser(user);
				msg = "注册成功";
			} else {
				msg = "用户名重复";
			}
		}

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		// out.println("<html>");
		// out.println("<head> <meta charset='UTF-8'>");
		// out.println("<title>用户注册成功</title>");
		// out.println("</head>");
		out.println("<!DOCTYPE html><html lang='en'><head><meta charset='UTF-8'><title>Document</title></head><body>");
		out.println("<p>" + msg + "</p>");
		out.println("</body>");
		out.println("</html>");
		out.close();
	}

	// 通过省查找其下的市
	private void doFindCity(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException,
			Exception {
		JSONObject jsonData = new JSONObject();
		// 1 　得到要查询的父(province)的id
		String provinceId = request.getParameter("provinceId");
		// 2 　调用cityService的getCityListByProID方法查询其子(cityList)集合
		CityService cs = CityService.getInstance();
		List<City> cityList = cs.getCityListByProID(provinceId);
		// 2-1列表转json数据
		if (cityList != null) {
			jsonData.put("data", cityList);
		} else {
			jsonData.put("data", null);
		}

		// 3 　将查询到的子集合（cityList）以字符串形式传回到页面，在页面的js中处理结果接收并据此添加
		// 字符流
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		// String cityData = jsonData.toString();
		out.print(jsonData.toString());
		out.close();
		// 去js页面接收数据并产生option元素
	}

	// 数据初始化
	private void dataInit(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException,
			Exception {
		// 证件类型初始化
		certTypeDataInit(request, response);
		// 用户类型初始化
		userTypeDataInit(request, response);
		// 省份类型初始化
		// provinceDataInit(request, response);
	}

	// 省份信息初始化
	private void provinceDataInit(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException,
			Exception {
		// 1 构建json对象
		JSONObject jsonData = new JSONObject();
		// 2 检索数据
		ProvinceService cts = ProvinceService.getInstance();
		List<Province> dataList = cts.getProvinceList();
		// 3 将数据写进data
		if (dataList != null) {
			jsonData.put("data", dataList);
		} else {
			jsonData.put("data", null);
		}
		// request.getSession().setAttribute("provinceTypeData", jsonData);
		// 3 　将查询到的子集合（cityList）以字符串形式传回到页面，在页面的js中处理结果接收并据此添加
		// 字符流
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.print(jsonData.toString());
		out.close();
		// 去js页面接收数据并产生option元素
	}

	// 用户类型初始化
	private void userTypeDataInit(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException,
			Exception {
		// 1 构建json对象
		JSONObject jsonData = new JSONObject();
		// 2 检索数据
		UserTypeService cts = UserTypeService.getInstance();
		List<UserType> dataList = cts.getUserTypeList();
		// 3 将数据写进data
		if (dataList != null) {
			jsonData.put("data", dataList);
		} else {
			// data.put("rows", 0);
			jsonData.put("data", null);
		}
		// 4　将data写进session,以便在程序会话中（非断开连接）使用
		request.getSession().setAttribute("userTypeData", jsonData);
	}

	// 证件类型初始化
	private void certTypeDataInit(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException,
			Exception {
		// 1 构建json对象
		JSONObject jsonData = new JSONObject();
		// 2 检索数据
		CertTypeService cts = CertTypeService.getInstance();
		ArrayList<CertType> dataList = cts.getCertTypeList();
		// 3 将数据写进data
		if (dataList != null) {
			jsonData.put("data", dataList);
		} else {
			// data.put("rows", 0);
			jsonData.put("data", null);
		}
		// 4　将data写进session,以便在程序会话中（非断开连接）使用
		request.getSession().setAttribute("certTypeData", jsonData);
	}

	private String validate(User user) {
		// String errorMsg = null;
		// if (TextUtils.isEmpty(user.getUsername())) {
		// errorMsg = "请输入用户名";
		// } else if (user.getUsername().length() < 6
		// || user.getUsername().length() > 30) {
		// errorMsg = "用户名长度在6到30位之间";
		// } else if (!user.getUsername().matches("[a-zA-Z0-9_]{6,30}")) {
		// errorMsg = "用户名只能包含由字母、数字或“_”";
		// } else if (TextUtils.isEmpty(user.getPassword())) {
		// errorMsg = "请输入密码";
		// } else if (!user.getPassword().equals(user.getPassword2())) {
		// errorMsg = "两次密码不相等";
		// } else if (TextUtils.isEmpty(user.getRealname())) {
		// errorMsg = "请输入真实姓名";
		// } else if (user.getCity().getId() == null) {
		// errorMsg = "请选择所在城市";
		// } else if (TextUtils.isEmpty(user.getCert())) {
		// errorMsg = "请输入证件号码";
		// } else if (user.getBirthday() == null) {
		// errorMsg = "请输入出生日期";
		// }
		// return errorMsg;
		return null;
	}

	private void populate(HttpServletRequest request, User user) {
		// 获取客户端IP
		String loginIp = request.getRemoteAddr();

		// 获取表单参数
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String password2 = request.getParameter("password2");
		String realname = request.getParameter("realname");
		String sex = request.getParameter("sex");
		// 需要修改前台代码
		// String cityId = request.getParameter("city");
		String certTypeId = request.getParameter("certType");
		String cert = request.getParameter("cert");
		String birthday = request.getParameter("birthday");
		String userTypeId = request.getParameter("userType");
		String content = request.getParameter("content");

		user.setLoginIp(loginIp);
		user.setUsername(username);

		user.setPassword(password);
		user.setPassword2(password2);
		user.setRealname(realname);
		user.setSex(sex);

		// City
		City city = new City();
		city.setId(1);
		user.setCity(city);

		// CertType
		CertType certType = new CertType();
		certType.setId(Integer.parseInt(certTypeId));
		user.setCertType(certType);

		// cert
		user.setCert(cert);

		// birthday
		if (!TextUtils.isEmpty(birthday)) {
			user.setBirthday(birthday);
		}

		// UserType
		UserType userType = new UserType();
		userType.setId(Integer.parseInt(userTypeId));
		user.setUserType(userType);

		// content
		user.setContent(content);
	}

}

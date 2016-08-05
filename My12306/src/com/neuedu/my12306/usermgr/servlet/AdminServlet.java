package com.neuedu.my12306.usermgr.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jxl.Workbook;
import jxl.format.Border;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;
import net.sf.json.JSONObject;

import com.neuedu.my12306.common.Md5Utils;
import com.neuedu.my12306.common.TextUtils;
import com.neuedu.my12306.usermgr.domain.CertType;
import com.neuedu.my12306.usermgr.domain.City;
import com.neuedu.my12306.usermgr.domain.IpAddress;
import com.neuedu.my12306.usermgr.domain.User;
import com.neuedu.my12306.usermgr.domain.UserType;
import com.neuedu.my12306.usermgr.service.UserService;

/**
 * Servlet implementation class AdminServlet
 */
//@WebServlet("/Admin/admin")
public class AdminServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	UserService service = UserService.getInstance();

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String action = request.getParameter("action");
		if ("add".equals(action)) {
			try {
				doAdd(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if ("del".equals(action)) {
			doDel(request, response);
		} else if ("findAndPush".equals(action)) {
			try {
				doFindAndPush(request, response);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if ("export".equals(action)) {
			try {
				doExport(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if ("chart".equals(action)) {
			try {
				doChart(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if ("show".equals(action)) {
			try {
				doShow(request, response);
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if ("update".equals(action)) {
			try {
				doUpdate(request, response);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private void doUpdate(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// List<IpAddress> list = service.getIpList();
		// TODO Auto-generated method stub
		User user = new User();
		populate(request, user);// 填充用户信息
		String sid = request.getParameter("id");
		user.setId(Integer.parseInt(sid));
		UserService us = UserService.getInstance();
		JSONObject data = new JSONObject();
		if (us.updateUser(user)) {
			data.put("flag", "yes");
		}
		// 设置输出端的内容类型及格式
		response.setContentType("text/html;charset=utf-8");
		// 取得输出流
		PrintWriter pw = response.getWriter();
		// 向输出流写入内容
		pw.print(data.toString());
		// 清空输出流，将内容推送到响应端
		pw.close();

	}

	private void doChart(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		List<IpAddress> list = service.getIpList();
		request.setAttribute("iplist", list);
		request.getRequestDispatcher("/Admin/chart.jsp").forward(request,
				response);

	}

	private void doFindAndPush(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// 1 接收uId
		String ids = request.getParameter("id");
		int id = Integer.parseInt(ids);
		// 2 查找此uId用户
		User u = new User();
		u.setId(id);
		User findUser = new User();
		findUser = service.findUser(u);// find id=5
		findUser.setId(u.getId());
		JSONObject data = new JSONObject();
		data.put("userData", findUser);
		request.setAttribute("data", data);
		// 3 　组织数据，推送到编辑页面
		request.getRequestDispatcher("/Admin/userAdd.jsp").forward(request,
				response);
	}

	private void doExport(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession se = request.getSession();
		User user = (User) se.getAttribute("listUser");
		int rowCount = service.getUserListRowCount(user);
		List<User> list = service.getUserList(rowCount, 1, user);
		response.setHeader("Content-disposition", "attachment; filename="
				+ new String("用户".getBytes("GB2312"), "8859_1") + ".xls");
		response.setHeader("pragma", "no-cache");
		response.setContentType("application/msexcel");
		ServletOutputStream os = response.getOutputStream();
		if (list == null) {
			return;
		}
		try {
			WritableWorkbook workbook = Workbook.createWorkbook(os);
			WritableSheet ws = workbook.createSheet("用户列表", 0);

			int rowNum = 0;

			WritableFont font1 = new WritableFont(WritableFont.TIMES, 16,
					WritableFont.BOLD);
			WritableCellFormat format1 = new WritableCellFormat(font1);
			Label cell = new Label(0, 0, "导出用户列表", format1);
			ws.addCell(cell);

			WritableCellFormat cellFormat2 = new WritableCellFormat();
			cellFormat2.setBorder(Border.ALL, jxl.format.BorderLineStyle.THIN);

			ws.setColumnView(7, 50);

			rowNum = 1;
			for (int i = 0; i < list.size(); i++, rowNum++) {// 写sheet
				User tmp = list.get(i);

				ws.addCell(new Label(0, rowNum, tmp.getId() + "", cellFormat2));
				ws.addCell(new Label(1, rowNum, tmp.getUsername(), cellFormat2));
				ws.addCell(new Label(2, rowNum,
						"1".equals(tmp.getRule()) ? "管理员" : "普通用户", cellFormat2));
				ws.addCell(new Label(3, rowNum, tmp.getRealname(), cellFormat2));
				ws.addCell(new Label(4, rowNum, "1".equals(tmp.getSex()) ? "男"
						: "女", cellFormat2));
				ws.addCell(new Label(5, rowNum, tmp.getCity().getCity(),
						cellFormat2));
				ws.addCell(new Label(6, rowNum, tmp.getCertType().getContent(),
						cellFormat2));
				ws.addCell(new Label(7, rowNum, tmp.getCert(), cellFormat2));
			}
			workbook.write();
			workbook.close();
		} catch (RowsExceededException e) {
			throw new RuntimeException(e);
		} catch (WriteException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		os.close();
	}

	private void doShow(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// 根据页面要求的页数，每页的记录数，生成数据，然后传到页面
		UserService us = UserService.getInstance();
		String strPageSize = request.getParameter("pageSize");
		String strPageNum = request.getParameter("page");// 第几页
		// 如果没有限制页数及每页显示数，则默认显示第1页，每页5条
		int pageSize = 5, pageNum = 1;
		if (strPageSize != null) {
			pageSize = Integer.parseInt(strPageSize);
		}
		if (strPageNum != null) {
			pageNum = Integer.parseInt(strPageNum);
		}
		// 要显示的用户及条件，如果没有为其填充，则为无条件显示
		User showUser = new User();
		// 填充并返回用户
		populate(request, showUser);
		// 获取满足条件的记录数
		// int rows = us.getUserListRowCount(showUser);/
		int pages = us.getUserListPageCount(pageSize, showUser);// 总共多少页
		// 生成页数列表1 2 3
		ArrayList<Integer> pageList = new ArrayList<Integer>();
		for (int i = 1; i <= pages; i++) {
			pageList.add(i);
		}

		List<User> userList = us.getUserList(pageSize, pageNum, showUser);

		// 包装成json数据
		JSONObject data = new JSONObject();
		if (userList != null) {
			data.put("users", userList);
			data.put("pages", pageList);// 分页的页数
		} else {
			data.put("users", null);
			data.put("pages", null);
		}
		request.setAttribute("data", data);// request范围
		request.getRequestDispatcher("/Admin/newIndex.jsp").forward(request,
				response);
	}

	// XML方式组织数据，显示用户列表
	private void doAdminList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		HttpSession se = request.getSession();

		// 如果没有传按每页多少条显示，则默认每页10条显示
		String pageSize = request.getParameter("pageSize");
		if (pageSize == null) {
			pageSize = "10";
		}

		// 如果没有传显示第几页，则默认显示第1页
		String page = request.getParameter("page");
		if (page == null) {
			page = "1";
		}

		String source = request.getParameter("source");

		if (source != null) {
			User user = new User();
			populateList(request, user);
			se.setAttribute("listUser", user);
			se.setAttribute("pageSize", pageSize);
		}

		User user = (User) se.getAttribute("listUser");
		UserService userService = UserService.getInstance();
		// 得到满足某个条件，第1页，每页10条的一个记录集。
		List<User> list = userService.getUserList(Integer.parseInt(pageSize),
				Integer.parseInt(page), user);
		// 得到满足某个条件的用户按每页１０条，总共有多少页
		int pageCount = userService.getUserListPageCount(
				Integer.parseInt(pageSize), user);

		// 用XML数据格式进行传送
		StringBuffer xml = new StringBuffer();
		xml.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		xml.append("<res>");
		xml.append("<pageCount>" + pageCount + "</pageCount>");
		xml.append("<list>");
		for (User u : list) {
			xml.append("<user>");
			xml.append("<id>" + u.getId() + "</id>");
			xml.append("<realname>" + u.getRealname() + "</realname>");
			xml.append("<sex>" + ("1".equals(u.getSex()) ? "男" : "女")
					+ "</sex>");
			xml.append("<certType>" + u.getCertType().getContent()
					+ "</certType>");
			xml.append("<cert>" + u.getCert() + "</cert>");
			xml.append("<userType>" + u.getUserType().getContent()
					+ "</userType>");
			xml.append("</user>");
		}

		xml.append("</list>");
		xml.append("</res>");

		response.setContentType("text/xml;charset=UTF-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter out = response.getWriter();
		out.println(xml.toString());
		out.close();
	}

	private void doEdit(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub

	}

	private void doDel(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		JSONObject data = new JSONObject();
		// 将接收到的字符串"'3','4'.'5'"，最终转换成整型数组(因为持久层要这种数据)，如[3,4,5]
		String[] ids = request.getParameter("ids_del").split(",");// "'3','4','25'"--->["3","4","25"]
		int[] userIdList = new int[ids.length];
		for (int i = 0; i < ids.length; i++) {
			userIdList[i] = Integer.parseInt(ids[i]);
		}
		// 调用业务层，处理删除业务
		UserService userService = UserService.getInstance();
		// 如果批量删除成功，则返回成功标志
		if (userService.deleteUsers(userIdList)) {
			data.put("flag", "yes");
		}
		// 将此成功标志，以json格式推送至请求端
		response.setContentType("text/html;charset=utf-8");
		PrintWriter pw = response.getWriter();
		pw.print(data.toString());
		pw.close();
	}

	private void doAdd(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// TODO Auto-generated method stub
		User user = new User();
		populate(request, user);// 填充用户信息
		user.setStatus("1");// 用户默认激活状态
		user.setPassword(Md5Utils.md5("123456"));// 初始密码

		UserService us = UserService.getInstance();
		JSONObject data = new JSONObject();
		if (us.addUser(user)) {
			data.put("flag", "yes");
		}
		// 设置输出端的内容类型及格式
		response.setContentType("text/html;charset=utf-8");
		// 取得输出流
		PrintWriter pw = response.getWriter();
		// 向输出流写入内容
		pw.print(data.toString());
		// 清空输出流，将内容推送到响应端
		pw.close();
	}

	// 验证用户输入合法性（如果用Html5，则可以在前端页面上完成这个功能）
	private String validate(User user) {
		String errorMsg = null;
		// if (TextUtils.isEmpty(user.getUsername())) {
		// errorMsg = "请输入用户名";
		// } else if (user.getUsername().length() < 6
		// || user.getUsername().length() > 30) {
		// errorMsg = "用户名长度在6到30位之间";
		// } else if (!user.getUsername().matches("[a-zA-Z0-9_]{6,30}")) {
		// errorMsg = "用户名只能包含由字母、数字或“_”";
		// } else if (TextUtils.isEmpty(user.getRealname())) {
		// errorMsg = "请输入真实姓名";
		// } else if (user.getCity().getId() == null) {
		// errorMsg = "请选择所在城市";
		// } else if (TextUtils.isEmpty(user.getCert())) {
		// errorMsg = "请输入证件号码";
		// } else if (user.getBirthday() == null) {
		// errorMsg = "请输入出生日期";
		// }
		return errorMsg;
	}

	// 填充用户的操作
	private void populate(HttpServletRequest request, User user) {
		// 获取表单参数
		String strId = request.getParameter("id");
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String rule = request.getParameter("rule");
		String realname = request.getParameter("realname");
		// try {
		// realname=new String(realname.getBytes("iso8859-1"),"utf-8");
		// } catch (UnsupportedEncodingException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }

		String sex = request.getParameter("sex");
		// 需要修改前台代码
		String cityId = request.getParameter("city");
		String certTypeId = request.getParameter("certType");
		String cert = request.getParameter("cert");
		String birthday = request.getParameter("birthday");
		String userTypeId = request.getParameter("userType");
		String content = request.getParameter("content");
		String status = request.getParameter("status");
		String image_path = request.getParameter("image_path");

		if (strId != null && !strId.isEmpty()) {
			user.setId(Integer.parseInt(strId));
		}
		user.setUsername(username);
		user.setRule(rule);
		user.setPassword(password);
		user.setRealname(realname);
		user.setSex(sex);

		// City
		City city = new City();
		if (cityId != null && !cityId.isEmpty()) {
			city.setId(Integer.parseInt(cityId));
		}
		user.setCity(city);

		// CertType
		CertType certType = new CertType();
		if (certTypeId != null && !certTypeId.isEmpty()) {
			certType.setId(Integer.parseInt(certTypeId));
		}
		user.setCertType(certType);

		// cert
		user.setCert(cert);

		// birthday
		if (!TextUtils.isEmpty(birthday)) {
			user.setBirthday(birthday);
		}

		// UserType
		UserType userType = new UserType();
		if (userTypeId != null && !userTypeId.isEmpty()) {
			userType.setId(Integer.parseInt(userTypeId));
		}
		user.setUserType(userType);

		// content
		user.setContent(content);

		user.setStatus(status);

		user.setImagePath(image_path);
	}

	// 根据查询条件填充用户
	private void populateList(HttpServletRequest request, User user) {
		// 获取表单参数
		String realname = request.getParameter("realname");
		String sex = request.getParameter("sex");
		String certTypeId = request.getParameter("certType");
		String cert = request.getParameter("cert");
		String userTypeId = request.getParameter("userType");

		user.setRealname(realname);
		user.setSex(sex);
		// CertType
		CertType certType = new CertType();
		certType.setId(Integer.parseInt(certTypeId));
		user.setCertType(certType);

		// cert
		user.setCert(cert);

		// UserType
		UserType userType = new UserType();
		userType.setId(Integer.parseInt(userTypeId));
		user.setUserType(userType);
	}

}

package com.neuedu.my12306.usermgr.test;

import org.junit.Assert;
import org.junit.Test;

import com.neuedu.my12306.common.Md5Utils;
import com.neuedu.my12306.usermgr.domain.CertType;
import com.neuedu.my12306.usermgr.domain.City;
import com.neuedu.my12306.usermgr.domain.User;
import com.neuedu.my12306.usermgr.domain.UserType;
import com.neuedu.my12306.usermgr.service.UserService;

public class TestUserService {
	UserService service = UserService.getInstance();

	// 测试添加用户
	// @Test
	public void testAddUser() {
		// fail("Not yet implemented");
		// UserService service = UserService.getInstance();
		User user = new User();
		user.setUsername("testuser");
		user.setPassword(Md5Utils.md5("123456"));
		// user.setPassword("123456");
		user.setRule("2");//
		user.setRealname("陈老师");
		user.setSex("0");//

		// city
		City city = new City();
		city.setId(1);
		user.setCity(city);

		// certType
		CertType certType = new CertType();
		certType.setId(1);
		user.setCertType(certType);

		user.setCert("440901198903127998");
		//
		// Calendar c = Calendar.getInstance();
		// c.set(Calendar.YEAR, 1989);
		// c.set(Calendar.MONTH, 2);
		// c.set(Calendar.DATE, 12);
		user.setBirthday("1999-02-03");// java.util.Date

		// userType　id为１的证件类型
		UserType userType = new UserType();
		userType.setId(1);
		user.setUserType(userType);

		user.setContent("无");//
		user.setStatus("1");//
		user.setLoginIp("192.168.1.2");//
		user.setImagePath("/home/a.png");//
		for (int i = 0; i < 20; i++)
			Assert.assertTrue(service.addUser(user));
	}

	// 测试修改加用户
	// @Test
	public void testUpdateUser() throws Exception {
		User user = new User();
		// 将用户id为25(测试用户一定要在数据表中)的sex修改为"0"
		// 一定要设置用户的ＩＤ,不然操作不成功
		// 因为：：如果对象没有封装ＩＤ，ＳＱＬ语句就不知道要操作哪条记录，
		// 以致会对整个表相应字段修改成同样的内容,这个是不允许的。

		user.setId(25);
		User fu = new User();
		fu = service.findUser(user);

		fu.setId(user.getId());
		// fu.setUsername("111");
		City city = new City();
		city.setId(210);
		fu.setCity(city);

		UserType ut = new UserType();
		ut.setId(3);
		fu.setUserType(ut);

		fu.setRealname("这是");
		fu.setBirthday("2001-01-01");
		fu.setCert("11001010101001001");
		fu.setRule("0");
		fu.setPassword("wwqwe2qwawar3ar3ar");

		CertType ct = new CertType();
		ct.setId(2);
		fu.setCertType(ct);

		fu.setContent("111");
		fu.setSex("0");
		Assert.assertTrue(service.updateUser(fu));
	}

	// 测试删除用户
	// @Test
	public void testDeleteUsers() {
		// fail("Not yet implemented");
		// UserService service = UserService.getInstance();
		// 保证数据表中有需要删除的真实数据
		int[] userIdList = { 6, 7 };
		Assert.assertTrue(service.deleteUsers(userIdList));
	}

	// 　测试登录
	// @Test
	public void testLogin() throws Exception {
		// fail("Not yet implemented");
		// UserService service = UserService.getInstance();
		User user = service.login("adminsys", Md5Utils.md5("123456"));
		// Assert.assertNotNull(user);
		Assert.assertTrue(user.getRealname().equals("系统管理员"));
	}

	// 测试获取用户列表最大行数
	// @Test
	public void TestGetUserListRowCount() {
		User u = new User();// 什么都不填充，意味着所有用户
		u.setUsername("test");
		System.out.println("表中总共有记录数：" + service.getUserListRowCount(u));
	}

	// 获取用户列表最大页数
	// @Test
	public void TestGetUserListPageCount() {
		User u = new User();// 什么都不填充，意味着所有用户
		int page = 10;
		String info = "满足条件的记录数共有" + service.getUserListRowCount(u) + "条"
				+ "\n";
		info += "若按每页显示" + page + "条计算，则总共有"
				+ service.getUserListPageCount(page, u) + "页";
		System.out.println(info);
	}

	// 测试查找用户
	// @Test
	public void testFind() {

		// 生成id=5的对象，持这个对象去查询，应该能找出编号为5的记录
		// userdaoimpl中的查询代码
		// 查询条件id字段
		// Integer id = one.getId();
		// if (id != null && id != 0) {
		// find_sql.append(" AND u.id=" + id);
		// tag = true;
		// }
		// select .. from .. where ID=5;
		User findUser = new User();
		findUser.setId(6);
		User user = service.findUser(findUser);
		System.out.println(user.toString());
		// Assert.assertNotNull(user);
		// Assert.assertTrue(user.getRealname().equals("陈老师2"));
	}

	// 测试得到用户列表，若无用户限制，则为findALl
	@Test
	public void testGetUserList() {
		// Assert.assertNotNull(service.getUserList(0, 0, new User()));
		// Assert.assertEquals(3, service.getUserList(10, 1, new
		// User()).size());
		User findUser = new User();
		findUser.setUsername("test");
		System.out.println("数据共有条数:" + service.getUserListRowCount(findUser));
		System.out.println("数据共有页数:"
				+ service.getUserListPageCount(5, findUser));
		for (User u : service.getUserList(5, 1, findUser)) {
			System.out.println(u.getId() + "  " + u.getRealname());
		}
	}
}

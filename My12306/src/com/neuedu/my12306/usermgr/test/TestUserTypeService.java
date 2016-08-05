package com.neuedu.my12306.usermgr.test;

import org.junit.Assert;
import org.junit.Test;

import com.neuedu.my12306.usermgr.service.UserTypeService;

public class TestUserTypeService {
	// １　生成对象
	UserTypeService instant = UserTypeService.getInstance();

	@Test
	public void testFindAll() {
		Assert.assertNotNull(instant.getUserTypeList());
	}
}

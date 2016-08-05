package com.neuedu.my12306.usermgr.test;

import org.junit.Assert;
import org.junit.Test;

import com.neuedu.my12306.usermgr.service.CityService;

public class TestCityService {
	// １　生成对象
	CityService instant = CityService.getInstance();

	@Test
	public void testGetCityListByProID() {
//		Assert.assertTrue(instant.getCityListByProID("130000").size()==11);
	System.out.println(instant.getCityListByProID("130000"));
	}
}

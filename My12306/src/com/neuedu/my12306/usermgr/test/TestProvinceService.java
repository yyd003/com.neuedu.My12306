package com.neuedu.my12306.usermgr.test;

import org.junit.Assert;
import org.junit.Test;

import com.neuedu.my12306.usermgr.service.ProvinceService;

public class TestProvinceService {
	// １　生成对象
	ProvinceService instant = ProvinceService.getInstance();

	// 测试方法
	// 功能：查找所有的对象
	// 测试结果：非空（因为数据表为非空，所果方法无错，则返回结果肯定是非空，反之，证明方法编写出错）
	// 测试岗位要做的就是将此测试结果反馈给开发部门即可。
	// 测试岗位并不需要知道具体的数据，所以尽量避免用以往的“数据展示”来测试方法
	@Test
	public void testFindAll() {
		Assert.assertNotNull(instant.getProvinceList());
	}
}

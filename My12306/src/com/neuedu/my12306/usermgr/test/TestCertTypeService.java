package com.neuedu.my12306.usermgr.test;

import org.junit.Assert;
import org.junit.Test;

import com.neuedu.my12306.usermgr.domain.CertType;
import com.neuedu.my12306.usermgr.service.CertTypeService;

public class TestCertTypeService {

	CertTypeService cts = CertTypeService.getInstance();

	 @Test//测试查找所有
	public void testFindAll() throws Exception {
		Assert.assertNotNull(cts.getCertTypeList());
	}

	// @Test
	// 测试精确查找。
	public void testFindJQ() throws Exception {
		Assert.assertNull(cts.findJQ("ID", 1114));
		// Assert.assertEquals("护照", cts.findJQ("ID", 4).getContent());
	}

//	 @Test//测试添加
	public void testAdd() throws Exception {
		CertType ct = new CertType();
		ct.setContent("新添加的证件类型");
		Assert.assertTrue(cts.add(ct));
	}

	// @Test
	// 测试删除
	public void testDelete() throws Exception {
		CertType ct = new CertType();
		ct.setId(1);
		Assert.assertTrue(cts.delete(ct));
	}

//	@Test
	// 测试修改
	public void testModify() throws Exception {
		CertType ct = new CertType();
		ct.setId(88);// 要修改的那条记录的ＩＤ
		ct.setContent("对８８号的修改");// 要修改的content字段的新内容
		Assert.assertTrue(cts.modify(ct));
	}

	// @Test
	// 测试模糊查找
	public void testFindMH() throws Exception {
		// System.out.println(cts.findMH("content", "类型").size());
		Assert.assertTrue(cts.findMH("content", "证").size() > 0);
	}
}

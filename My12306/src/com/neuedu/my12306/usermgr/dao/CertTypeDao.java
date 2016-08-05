package com.neuedu.my12306.usermgr.dao;

import java.util.ArrayList;

import com.neuedu.my12306.usermgr.domain.CertType;

/**
 * 证件类型表操作接口
 * 
 * @author Administrator
 *
 */
public interface CertTypeDao {
	/**
	 * 获取所有证件类型列表
	 * 
	 * @return 证件类型列表
	 * @throws Exception
	 */
	ArrayList<CertType> getCertTypeList() throws Exception;

	boolean add(CertType ct) throws Exception;

	CertType findJQ(String key, Object value) throws Exception;

	ArrayList<CertType> findMH(String key, String value) throws Exception;

	boolean delete(CertType ct) throws Exception;

	boolean modify(CertType ct) throws Exception;

}

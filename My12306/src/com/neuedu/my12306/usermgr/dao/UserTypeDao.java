package com.neuedu.my12306.usermgr.dao;

import java.sql.SQLException;
import java.util.List;

import com.neuedu.my12306.usermgr.domain.UserType;

/**
 * 旅客类型表操作接口
 * @author Administrator
 *
 */
public interface UserTypeDao {
	/**
	 * 获取所有旅客类型列表
	 * @return 旅客类型列表
	 * @throws SQLException
	 */
	List<UserType> getUserTypeList() throws SQLException;
}

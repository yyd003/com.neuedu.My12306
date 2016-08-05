package com.neuedu.my12306.usermgr.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import com.neuedu.my12306.common.DBUtils;
import com.neuedu.my12306.common.ServiceException;
import com.neuedu.my12306.usermgr.dao.CertTypeDao;
import com.neuedu.my12306.usermgr.dao.CertTypeDaoImpl;
import com.neuedu.my12306.usermgr.domain.CertType;

/**
 * 证件类型服务类（采用单例模式实现）
 */
public class CertTypeService {
	/**
	 * 类实例
	 */
	private static final CertTypeService instance = new CertTypeService();

	/**
	 * 取得实例
	 * 
	 * @return 实例对象
	 */
	public static CertTypeService getInstance() {
		return instance;
	}

	/**
	 * 构造方法
	 */
	private CertTypeService() {
	}

	/**
	 * 获取所有证件类型列表
	 * 
	 * @return 证件类型列表
	 * @throws Exception
	 * @throws SQLException
	 */
	public ArrayList<CertType> getCertTypeList() throws Exception {
		Connection conn = null;
		ArrayList<CertType> res = null;
		try {
			conn = DBUtils.getConnection();
			CertTypeDao certTypeDao = new CertTypeDaoImpl(conn);
			DBUtils.beginTransaction(conn);
			res = certTypeDao.getCertTypeList();// 持久层的事儿
			DBUtils.commit(conn);
		} catch (SQLException e) {
			DBUtils.rollback(conn);
			throw new ServiceException("查询错误", e);
		} finally {
			DBUtils.closeConnection(conn);
		}

		return res;
	}

	/*
	 * 模糊查找 k:要查找的关键字　 v:要查找的值
	 */
	public ArrayList<CertType> findMH(String k, String v) throws Exception {
		Connection conn = null;
		ArrayList<CertType> res = null;
		try {
			conn = DBUtils.getConnection();
			CertTypeDao certTypeDao = new CertTypeDaoImpl(conn);
			DBUtils.beginTransaction(conn);
			res = certTypeDao.findMH(k, v);// 持久层的事儿
			DBUtils.commit(conn);
		} catch (SQLException e) {
			DBUtils.rollback(conn);
			throw new ServiceException("模糊查询错误", e);
		} finally {
			DBUtils.closeConnection(conn);
		}
		return res;
	}

	/*
	 * 精确查找 k:要查找的关键字　 v:要查找的值
	 */
	public CertType findJQ(String k, Object v) throws Exception {
		Connection conn = null;
		CertType res = null;
		try {
			conn = DBUtils.getConnection();
			CertTypeDao certTypeDao = new CertTypeDaoImpl(conn);
			DBUtils.beginTransaction(conn);
			res = certTypeDao.findJQ(k, v);// 持久层的事儿
			DBUtils.commit(conn);
		} catch (SQLException e) {
			DBUtils.rollback(conn);
			throw new ServiceException("查询错误", e);
		} finally {
			DBUtils.closeConnection(conn);
		}

		return res;
	}

	/*
	 * 添加 : ct:要添加的对象
	 */
	public boolean add(CertType ct) throws Exception {
		Connection conn = null;
		boolean res = false;
		try {
			conn = DBUtils.getConnection();
			CertTypeDao certTypeDao = new CertTypeDaoImpl(conn);
			DBUtils.beginTransaction(conn);
			res = certTypeDao.add(ct);// 持久层的事儿
			DBUtils.commit(conn);
		} catch (SQLException e) {
			DBUtils.rollback(conn);
			throw new ServiceException("添加错误", e);
		} finally {
			DBUtils.closeConnection(conn);
		}

		return res;
	}

	/*
	 * 修改 : ct:要修改的对象
	 */
	public boolean modify(CertType ct) throws Exception {
		Connection conn = null;
		boolean res = false;
		try {
			conn = DBUtils.getConnection();
			CertTypeDao certTypeDao = new CertTypeDaoImpl(conn);
			DBUtils.beginTransaction(conn);
			res = certTypeDao.modify(ct);// 持久层的事儿
			DBUtils.commit(conn);
		} catch (SQLException e) {
			DBUtils.rollback(conn);
			throw new ServiceException("修改错误", e);
		} finally {
			DBUtils.closeConnection(conn);
		}
		return res;
	}

	/*
	 * 删除 : ct:要删除的对象
	 */
	public boolean delete(CertType ct) throws Exception {
		Connection conn = null;
		boolean res = false;
		try {
			conn = DBUtils.getConnection();
			CertTypeDao certTypeDao = new CertTypeDaoImpl(conn);
			DBUtils.beginTransaction(conn);
			res = certTypeDao.delete(ct);// 持久层的事儿
			DBUtils.commit(conn);
		} catch (SQLException e) {
			DBUtils.rollback(conn);
			throw new ServiceException("删除错误", e);
		} finally {
			DBUtils.closeConnection(conn);
		}
		return res;
	}
}

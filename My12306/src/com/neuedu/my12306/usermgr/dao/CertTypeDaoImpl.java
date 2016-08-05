package com.neuedu.my12306.usermgr.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.neuedu.my12306.common.DBUtils;
import com.neuedu.my12306.usermgr.domain.CertType;

public class CertTypeDaoImpl implements CertTypeDao {

	/**
	 * 数据库连接
	 */
	private Connection conn;

	/**
	 * 构造方法 数据库连接
	 */
	public CertTypeDaoImpl(Connection conn) {
		this.conn = conn;
	}

	@Override
	public boolean add(CertType ct) throws Exception {
		String find_sql = "insert into tab_certtype(content) values(?)";
		PreparedStatement pstmt = null;
		int i = 0;
		try {
			// 设置语句对象，SQL语句条件
			pstmt = conn.prepareStatement(find_sql);
			pstmt.setString(1, ct.getContent());
			i = pstmt.executeUpdate();
			if (i != 0) {
				return true;
			} else {
				return false;
			}
		} finally {
			DBUtils.closeStatement(null, pstmt);
		}

	}

	/*
	 * 精确查找 k:要查找的关键字　 v:要查找的值
	 */
	@Override
	public CertType findJQ(String k, Object v) throws Exception {
		String find_sql = "SELECT * FROM tab_certtype where " + k + "=?";

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		CertType one = null;
		try {
			// 设置语句对象，SQL语句条件
			pstmt = conn.prepareStatement(find_sql);
			// pstmt.setString(1, k);
			pstmt.setObject(1, v);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				// 解析结果集对象
				// 1 生成对象
				one = new CertType();
				// ２　填充对象
				one.setId(rs.getInt("ID"));
				one.setContent(rs.getString("content"));
			}
		} finally {
			// DBUtils.closeStatement(rs, pstmt);
		}
		System.out.println("ctdaoimpl:" + pstmt);
		return one;
	}

	/*
	 * 模糊查找 k:要查找的关键字　 v:要查找的值
	 */
	@Override
	public ArrayList<CertType> findMH(String key, String value)
			throws Exception {
		String find_sql = "select * from tab_certtype where " + key
				+ " like '%" + value + "%'";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<CertType> certTypeList = new ArrayList<CertType>();
		try {
			// 设置语句对象，SQL语句条件
			pstmt = conn.prepareStatement(find_sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				// 解析结果集对象
				// 1 生成对象
				CertType one = new CertType();
				// ２　填充对象
				one.setId(rs.getInt("ID"));
				one.setContent(rs.getString("content"));
				// ３　将填充完毕的完整对象添加到列表中
				// 保存证件信息列表
				certTypeList.add(one);
			}
		} finally {
			DBUtils.closeStatement(rs, pstmt);
		}
		return certTypeList;
	}

	@Override
	public boolean delete(CertType ct) throws Exception {
		String find_sql = "delete from tab_certtype where ID=?";
		PreparedStatement pstmt = null;
		int i = 0;
		try {
			// 设置语句对象，SQL语句条件
			pstmt = conn.prepareStatement(find_sql);
			pstmt.setInt(1, ct.getId());
			i = pstmt.executeUpdate();
			if (i != 0) {
				return true;
			} else {
				return false;
			}
		} finally {
			DBUtils.closeStatement(null, pstmt);
		}
	}

	// 现只处理修改一个字段，以后再处理同时修改多个字段的值
	@Override
	public boolean modify(CertType ct) throws Exception {
		// 先改固定的，以后传条件集合过来再进行升级
		// [content:"新修改内容",name:"新修改内容",realname:"新修改内容",password:"新修改内容"]
		String find_sql = "update tab_certtype set content=? where ID=?";
		PreparedStatement pstmt = null;
		int i = 0;
		try {
			// 设置语句对象，SQL语句条件
			pstmt = conn.prepareStatement(find_sql);
			pstmt.setString(1, ct.getContent());
			pstmt.setInt(2, ct.getId());
			i = pstmt.executeUpdate();
			if (i != 0) {
				return true;
			} else {
				return false;
			}
		} finally {
			DBUtils.closeStatement(null, pstmt);
		}
	}

	@Override
	public ArrayList<CertType> getCertTypeList() throws Exception {
		// SQL语句
		String find_sql = "SELECT * FROM tab_certtype";

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<CertType> certTypeList = new ArrayList<CertType>();
		try {
			// 设置语句对象，SQL语句条件
			pstmt = conn.prepareStatement(find_sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				// 解析结果集对象
				// 1 生成对象
				CertType one = new CertType();
				// ２　填充对象
				one.setId(rs.getInt("ID"));
				one.setContent(rs.getString("content"));
				// ３　将填充完毕的完整对象添加到列表中
				// 保存证件信息列表
				certTypeList.add(one);
			}
		} finally {
			DBUtils.closeStatement(rs, pstmt);
		}

		return certTypeList;
	}

}

package com.neuedu.my12306.usermgr.domain;

/**
 * 证件类型实体类
 */
public class CertType {

	/**
	 * ID
	 */
	private Integer id;

	/**
	 * 证件类型
	 */
	private String content;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Override  //注解
	public String toString() {
		return "CertType [id=" + id + ", content=" + content + "]";
	}

}

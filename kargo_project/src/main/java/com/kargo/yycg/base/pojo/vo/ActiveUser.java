package com.kargo.yycg.base.pojo.vo;

import java.util.Iterator;
import java.util.List;

import com.sun.org.apache.bcel.internal.generic.NEW;

public class ActiveUser  implements java.io.Serializable {
	private String userid;//用户账号
	private String username;//用户名称
	private String groupid;//用户类型
	private String groupname;//用户类型名称

	private String password;//用户密码
	private Menu menu;//操作菜单
	private List<Operation> operationList;//操作权限
	private List arealist;//用户管理区域列表
	private String iscenteruser;//是否超级管理
	private String sysid;//用户业务信息id


	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}


	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public List getArealist() {
		return arealist;
	}

	public void setArealist(List arealist) {
		this.arealist = arealist;
	}

	public String getIscenteruser() {
		return iscenteruser;
	}

	public void setIscenteruser(String iscenteruser) {
		this.iscenteruser = iscenteruser;
	}


	public String getGroupid() {
		return groupid;
	}

	public void setGroupid(String groupid) {
		this.groupid = groupid;
	}


	public String getSysid() {
		return sysid;
	}

	public void setSysid(String sysid) {
		this.sysid = sysid;
	}


	/*public String getLogintype() {
		return logintype;
	}

	public void setLogintype(String logintype) {
		this.logintype = logintype;
	}*/



	public String getGroupname() {
		return groupname;
	}

	public List<Operation> getOperationList() {
		return operationList;
	}

	public void setOperationList(List<Operation> operationList) {
		this.operationList = operationList;
	}

	public void setGroupname(String groupname) {
		this.groupname = groupname;
	}

	public Menu getMenu() {
		return menu;
	}

	public void setMenu(Menu menu) {
		this.menu = menu;
	}

/*	public Operation getActionUrl(String actionUrl) {
		if (operationList != null) {
			
			for (Iterator<Operation> o_list = operationList.iterator(); o_list.hasNext();) {
				Operation o_i = (Operation) o_list.next();
				if (actionUrl.indexOf(o_i.getActionUrl())>0) {
					return o_i;
				}
			}
		}
		return null;
	}*/
	

}

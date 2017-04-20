package com.kargo.yycg.base.pojo.vo;

import java.io.Serializable;
import java.util.List;

/**
 * 菜单模型类
 * @author mrt
 *
 */
public class Menu implements Serializable{

	//菜单
	private List<Menu> menus;
	
	private String menuid;
	
	private String icon;
	
	private String menuname;
	
	private String url;
	
	//菜单下的各各操作链接为了权限链接
	private List<Operation> operations;

	public List<Menu> getMenus() {
		return menus;
	}

	public void setMenus(List<Menu> menus) {
		this.menus = menus;
	}

	public String getMenuid() {
		return menuid;
	}

	public void setMenuid(String menuid) {
		this.menuid = menuid;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getMenuname() {
		return menuname;
	}

	public void setMenuname(String menuname) {
		this.menuname = menuname;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	
	public List<Operation> getOperations() {
		return operations;
	}

	public void setOperations(List<Operation> operations) {
		this.operations = operations;
	}

	@Override
	public String toString() {
		return "Menu [menus=" + menus + ", menuid=" + menuid + ", icon=" + icon
				+ ", menuname=" + menuname + ", url=" + url + "]";
	}
	
	
	
	
	
	
}

/**
The MIT License (MIT) * Copyright (c) 2016 铭飞科技(mingsoft.net)

 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
 * the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:

 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.

 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.mingsoft.weixin.entity;

import java.util.List;

import com.mingsoft.base.entity.BaseEntity;
import com.mingsoft.weixin.constant.e.MenuTypeEnum;

/**
 * 
 * 微信底部菜单的实体类
 * @author 成卫雄(qq:330216230)
 * @version 
 * 版本号：100-000-000<br/>
 * 创建日期：2015年8月26日 上午10:39:26<br/>
 * 历史修订：<br/>
 */
public class MenuEntity extends BaseEntity {
	

	/**
	 * 菜单编号
	 */
	private int menuId;
	
	/**
	 * 菜单所属商家微信的编号
	 */
	private int menuAppId;
	
	/**
	 * 菜单名字
	 */
	private String menuTitle;
	
	/**
	 * 菜单链接地址
	 */
	private String menuUrl;
	
	/**
	 * 菜单点击类型</br>
	 * 1、click：点击推事件</br>
	 * 2、view：跳转URL</br>
	 * 3、scancode_push：扫码推事件</br>
	 * 4、scancode_waitmsg：扫码推事件且弹出“消息接收中”提示框</br>
	 * 5、pic_sysphoto：弹出系统拍照发图</br>
	 * 6、pic_photo_or_album：弹出拍照或者相册发图</br>
	 * 7、pic_weixin：弹出微信相册发图器</br>
	 * 8、location_select：弹出地理位置选择器</br>
	 * @see MenuTypeEnum
	 */
	private int menuType = 0;

	/**
	 * 菜单分类,该分类是平台分类；
	 * <option value="1" selected="">文本</option>
				<option value="2">图文</option>
				<option value="3">语音</option>
				<option value="4">外链</option>
				<option value="5">应用商城</option>
				<option value="6">关键词触发</option>

	 */
	private int menuStyle;
	
	/**
	 * 菜单状态默认:1</br>
	 *	0：不启用</br>
	 *	1：启用</br>
	 */
	private Integer menuStatus = 1;
	
	/**
	 * 父菜单编号
	 */
	private Integer menuMenuId = 0;
	
	/**
	 * 菜单排序
	 */
	private Integer menuSort=0;
	
	/**
	 * 菜单外连接的授权id
	 */
	private Integer menuOauthId = 0;
	
	/**
	 * 关联微信唯一ID
	 */
	private Integer menuWeixinId;	
	
	/**
	 * 子菜单
	 */
	private List<MenuEntity> childsMenu  = null;
	

	public List<MenuEntity> getChildsMenu() {
		return childsMenu;
	}

	public void setChildsMenu(List<MenuEntity> childsMenu) {
		this.childsMenu = childsMenu;
	}

	/**
	 * 获取menuId
	 * @return  menuId
	 */
	public int getMenuId() {
		return menuId;
	}

	public int getMenuStyle() {
		return menuStyle;
	}

	public void setMenuStyle(int menuStyle) {
		this.menuStyle = menuStyle;
	}

	/**
	 * 设置menuId
	 * @param menuId
	 */
	public void setMenuId(int menuId) {
		this.menuId = menuId;
	}

	/**
	 * 获取menuAppId
	 * @return  menuAppId
	 */
	public int getMenuAppId() {
		return menuAppId;
	}

	/**
	 * 设置menuAppId
	 * @param menuAppId
	 */
	public void setMenuAppId(int menuAppId) {
		this.menuAppId = menuAppId;
	}

	/**
	 * 获取menuTitle
	 * @return  menuTitle
	 */
	public String getMenuTitle() {
		return menuTitle;
	}

	/**
	 * 设置menuTitle
	 * @param menuTitle
	 */
	public void setMenuTitle(String menuTitle) {
		this.menuTitle = menuTitle;
	}

	/**
	 * 获取menuUrl
	 * @return  menuUrl
	 */
	public String getMenuUrl() {
		return menuUrl;
	}

	/**
	 * 设置menuUrl
	 * @param menuUrl
	 */
	public void setMenuUrl(String menuUrl) {
		this.menuUrl = menuUrl;
	}

	/**
	 * 获取menuType
	 * @return  menuType
	 */
	public int getMenuType() {
		return menuType;
	}

	/**
	 * 设置menuType
	 * @param menuType
	 * @deprecated
	 */
	public void setMenuType(int menuType) {
		this.menuType = menuType;
	}
	
	public void setMenuType(MenuTypeEnum menuType) {
		this.menuType = menuType.toInt();
	}

	/**
	 * 获取menuStatus
	 * @return  menuStatus
	 */
	public Integer getMenuStatus() {
		return menuStatus;
	}

	/**
	 * 设置menuStatus
	 * @param menuStatus
	 */
	public void setMenuStatus(Integer menuStatus) {
		this.menuStatus = menuStatus;
	}

	/**
	 * 获取menuMenuId
	 * @return  menuMenuId
	 */
	public Integer getMenuMenuId() {
		return menuMenuId;
	}

	/**
	 * 设置menuMenuId
	 * @param menuMenuId
	 */
	public void setMenuMenuId(Integer menuMenuId) {
		this.menuMenuId = menuMenuId;
	}

	public Integer getMenuSort() {
		return menuSort;
	}

	public void setMenuSort(Integer menuSort) {
		this.menuSort = menuSort;
	}

	public Integer getMenuOauthId() {
		return menuOauthId;
	}

	public void setMenuOauthId(Integer menuOauthId) {
		this.menuOauthId = menuOauthId;
	}


	public Integer getMenuWeixinId() {
		return menuWeixinId;
	}
	
	public void setMenuWeixinId(Integer menuWeixinId) {
		this.menuWeixinId = menuWeixinId;
	}
	
	
	
}
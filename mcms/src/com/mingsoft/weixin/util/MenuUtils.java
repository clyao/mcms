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

package com.mingsoft.weixin.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mingsoft.util.StringUtil;
import com.mingsoft.weixin.bean.MenuBean;
import com.mingsoft.weixin.entity.MenuEntity;
import com.mingsoft.weixin.http.HttpClientConnectionManager;

/**
 * 微信自定义菜单创建
 * @author wangtp
 * @version 
 * 版本号：100-000-000<br/>
 * 创建日期：2013-12-23<br/>
 * 历史修订：<br/>
 */
public class MenuUtils extends BaseUtils {

	/**
	 *微信菜单创建地址
	 */
	private final static String CREATE_MENU = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=";
	
	/**
	 * 微信菜单查询地址
	 */
	private final static String QUERY_MENU = "https://api.weixin.qq.com/cgi-bin/menu/get?access_token=";
	
	/**
	 * 微信菜单删除地址
	 */
	private final static String REMOVE_MENU = "https://api.weixin.qq.com/cgi-bin/menu/delete?access_token=";
	
	public MenuUtils(String appid, String secret) {
		super(appid, secret);
	}


	/**
	 * 创建菜单
	 * @param params 菜单的json格式，详细参考：</br>
	 * 					http://mp.weixin.qq.com/wiki/index.php?title=%E8%87%AA%E5%AE%9A%E4%B9%89%E8%8F%9C%E5%8D%95%E5%88%9B%E5%BB%BA%E6%8E%A5%E5%8F%A3</br>
	 * @param accessToken 有效的授权码
	 * @return
	 * @throws Exception
	 */
	public String createMenu(String params)
			throws Exception {
		
		if(getAccessToken()!=null){
			HttpPost httpost = HttpClientConnectionManager.getPostMethod(CREATE_MENU+ getAccessToken());
			httpost.setEntity(new StringEntity(params, "UTF-8"));
			HttpResponse response = HTTPCLIENT.execute(httpost);
			String jsonStr = EntityUtils.toString(response.getEntity(), "utf-8");
			logger.debug("---------创建菜单："+jsonStr);
			JSONObject object = JSON.parseObject(jsonStr);
			
			return object.getString(WEIXIN_JSON_ERR_STR);
		}else{
			return "";
		}
	}


	/**
	 * 查询菜单
	 */
	public String queryMenu() throws Exception {
		HttpGet get = HttpClientConnectionManager.getGetMethod(QUERY_MENU + getAccessToken());
		HttpResponse response = HTTPCLIENT.execute(get);
		String jsonStr = EntityUtils.toString(response.getEntity(), "utf-8");
		return jsonStr;
	}

	/**
	 * 删除自定义菜单
	 */
	public boolean removeMenu() throws Exception {
		HttpGet get = HttpClientConnectionManager.getGetMethod(REMOVE_MENU + getAccessToken());
		HttpResponse response = HTTPCLIENT.execute(get);
		String jsonStr = EntityUtils.toString(response.getEntity(), "utf-8");
		logger.debug(jsonStr);
		JSONObject object = JSON.parseObject(jsonStr);
		if(object.getString(WEIXIN_JSON_ERR_STR).equals("ok")){
			return true;
			
		}
		return false;
		//return object.getString(WEIXIN_JSON_ERR_STR);
	}
	
	/**
	 * 
	 * @param menuListFather 父菜单信息 map:  key:1;value:父菜单编号</br>
	 * 																key:2;value:菜单名称</br>
	 * 																key:3;value:菜单类型</br>
	 * 																key:4;value:点击菜单返回事件(菜单类型的值为click和view时存在该值)</br>
	 * @param menuListSon 子菜单信息 map: key:子菜单编号对应父菜单中集合中Map的key值1;</br> 
	 * 															value: map:key:1;value:菜单名称</br>
	 * 																			 key:2;value:菜单类型</br>
	 * 																			 key:3;value:点击菜单返回事件(菜单类型的值为click和view时存在该值)</br>
	 * @return true 成功
	 */
	@Deprecated
	public Boolean setMenu(List<Map<Integer,String>> menuListFather,Map<Integer,List<Map<Integer,String>>> menuListSon){
		
		if(menuListFather == null || menuListFather.size() == 0){
			return false;
		}
		
		StringBuffer json = new StringBuffer();
		json.append("{\"button\":[");
		for(int i = 0;i<menuListFather.size();i++){
			Map<Integer,String> mapFather = menuListFather.get(i);//取出父菜单信息
			//判断该父菜单下是否存在子菜单
			List<Map<Integer,String>> _menuListSon = menuListSon.get(Integer.parseInt(mapFather.get(1)));
			
			if(_menuListSon == null || _menuListSon.size() == 0){//当该父菜单下不存在子菜单时
				json.append(menuTypeJson(mapFather.get(2),Integer.parseInt(mapFather.get(3)),mapFather.get(4)));
			}else{//当该父菜单下存在子菜单时
				json.append("{\"name\":\"").append(mapFather.get(2)).append("\",\"sub_button\":[");
				for(int n=0;n<_menuListSon.size();n++){
					Map<Integer,String> menuSonMap = _menuListSon.get(n);
					json.append(menuTypeJson(menuSonMap.get(1),Integer.parseInt(menuSonMap.get(2)),menuSonMap.get(3)));
					
					if(n<(_menuListSon.size()-1) ){
						json.append(",");
					}
				}
				json.append("]}");
			}
			
			if(i <(menuListFather.size()-1)){
				json.append(",");
			}
			
		}
		
		json.append("]}");
		logger.debug("----菜单JSON:"+json.toString());
		try {
			String careateMenu = createMenu(json.toString());
			if(careateMenu.equals("ok")){
				return true;
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e);
		}
		
		return false;
	}
	
	/**
	 * 根据菜单类型生成一条JSON
	 * @param menuName 菜单名称
	 * @param menuType 菜单类型</br>
	 *								 1、click：点击推事件</br>
	 * 								 2、view：跳转URL</br>
	 *								 3、scancode_push：扫码推事件</br>
	 * 								 4、scancode_waitmsg：扫码推事件且弹出“消息接收中”提示框</br>
	 * 								 5、pic_sysphoto：弹出系统拍照发图</br>
	 * 								 6、pic_photo_or_album：弹出拍照或者相册发图</br>
	 *								 7、pic_weixin：弹出微信相册发图器</br>
	 *								 8、location_select：弹出地理位置选择器</br>		
	 * @param returnValue 点击菜单的返回值
	 * @return JSON字符串 {'name':'名称','type':'类型','value':'值'}
	 */
	@Deprecated
	private String menuTypeJson(String menuTitle,int menuType,String returnValue){
		
		String json = null;
		
		if(StringUtil.isBlank(menuTitle) || menuType == 0){
			return json;
		}
		
		switch (menuType) {
		case 1://click：点击推事件
			json = "{\"name\":\""+menuTitle+"\",\"type\":\"click\""+",\"key\":\""+returnValue+"\"}";
			break;
			
		case 2://view：跳转URL
			json = "{\"name\":\""+menuTitle+"\",\"type\":\"view\""+",\"url\":\""+returnValue+"\"}";
			break;
			
		case 3://scancode_push：扫码推事件
			json = "{\"name\":\""+menuTitle+"\",\"type\":\"scancode_push\",\"key\":\"rselfmenu_0_1\",\"sub_button\": [ ]}";
			break;
			
		case 4://scancode_waitmsg：扫码推事件且弹出“消息接收中”提示框
			json = "{\"name\":\""+menuTitle+"\",\"type\":\"scancode_waitmsg\",\"key\":\"rselfmenu_0_0\",\"sub_button\": [ ]}";
			break;
			
		case 5://pic_sysphoto：弹出系统拍照发图
			json = "{\"name\":\""+menuTitle+"\",\"type\":\"pic_sysphoto\",\"key\":\"rselfmenu_1_0\",\"sub_button\": [ ]}";
			break;
			
		case 6://pic_photo_or_album：弹出拍照或者相册发图
			json = "{\"name\":\""+menuTitle+"\",\"type\":\"pic_photo_or_album\",\"key\":\"rselfmenu_1_1\",\"sub_button\": [ ]}";
			break;
		case 7://pic_weixin：弹出微信相册发图器
			json = "{\"name\":\""+menuTitle+"\",\"type\":\"pic_weixin\",\"key\":\"rselfmenu_1_2\",\"sub_button\": [ ]}";
			break;
			
		case 8://location_select：弹出地理位置选择器
			json = "{\"name\":\""+menuTitle+"\",\"type\":\"location_select\",\"key\":\"rselfmenu_2_0\"}";
			break;	
			
		default:
			json = null;
			break;
		}
		logger.debug("----一条菜单JSON:"+json);
		return  json;
	}
	
	/**
	 * 组织菜单
	 * @param menu
	 * @return
	 */
	public KeyLabel menuJson(MenuEntity menu){
		int menuType = menu.getMenuType();
		
		KeyLabel keyLabel = new KeyLabel();
		
		switch (menuType) {
		case 1://click：点击推事件
			
			keyLabel.setKey(menu.getMenuUrl());
			keyLabel.setType("click");
			keyLabel.setName(menu.getMenuTitle());
			keyLabel.setKey(menu.getMenuUrl());
			break;
			
		case 2://view：跳转URL
			keyLabel.setName(menu.getMenuTitle());
			keyLabel.setType("view");
			keyLabel.setKey("");
			keyLabel.setUrl(menu.getMenuUrl());
			break;
			
		case 3://scancode_push：扫码推事件
			keyLabel.setKey("rselfmenu_0_1");
			keyLabel.setType("scancode_push");
			keyLabel.setName(menu.getMenuTitle());
			break;
			
		case 4://scancode_waitmsg：扫码推事件且弹出“消息接收中”提示框
			keyLabel.setKey("rselfmenu_1_0");
			keyLabel.setType("scancode_waitmsg");
			keyLabel.setName(menu.getMenuTitle());
			break;
			
		case 5://pic_sysphoto：弹出系统拍照发图
			keyLabel.setKey("rselfmenu_1_1");
			keyLabel.setType("pic_sysphoto");
			keyLabel.setName(menu.getMenuTitle());
			break;
			
		case 6://pic_photo_or_album：弹出拍照或者相册发图
			keyLabel.setKey("rselfmenu_1_1");
			keyLabel.setType("pic_photo_or_album");
			keyLabel.setName(menu.getMenuTitle());
			break;
		case 7://pic_weixin：弹出微信相册发图器
			keyLabel.setKey("rselfmenu_1_2");
			keyLabel.setType("pic_weixin");
			keyLabel.setName(menu.getMenuTitle());
			break;
			
		case 8://location_select：弹出地理位置选择器
			keyLabel.setKey("rselfmenu_2_0");
			keyLabel.setType("location_select");
			keyLabel.setName(menu.getMenuTitle());
			break;
			
		default:
			return null;
		
		}
		return keyLabel;
	}
	
	
	
	/**
	 * 设置菜单
	 * @param MenuList 菜单列表
	 * @return 菜单设置是否成功
	 */
	public boolean setMenu(List<MenuEntity> MenuList){
		MenuBean menuBean = new MenuBean();
		List menuList = new ArrayList();
		for(int i = 0;i<MenuList.size();i++){
			//查询该菜单下是否存在子菜单
			if(MenuList.get(i).getChildsMenu()==null || MenuList.get(i).getChildsMenu().size()==0){
				//判断菜单类型是否为“view”类型，如果为view，则加入url类型，否则加“key”类型
				KeyLabel keyLabel =(KeyLabel) menuJson(MenuList.get(i));
				menuList.add(keyLabel);
			}else{
				SubMenuBean sonMenuBean = new SubMenuBean();
				sonMenuBean.setName(MenuList.get(i).getMenuTitle());
				List sub_button = new ArrayList();
				
				//遍历子菜单
				for(int j = 0;j<MenuList.get(i).getChildsMenu().size();j++){
					MenuEntity menuSon = MenuList.get(i).getChildsMenu().get(j);
					KeyLabel keyLabel = menuJson(menuSon);
					sub_button.add(keyLabel);
				}
				sonMenuBean.setSub_button(sub_button);
				menuList.add(sonMenuBean);
			}
		}
		menuBean.setButton(menuList);
		try {
			String careateMenu = createMenu(JSONObject.toJSONString(menuBean));
			logger.debug("----一条菜单JSON:"+JSONObject.toJSONString(menuBean));
			if(careateMenu.equals("ok")){
				return true;
			}
			
		} catch (Exception e) {
			logger.error(e);
		}
		return false;
	}
	
	
	
	
	/**
	 * 含有“key“的参数类型
	 */
	class KeyLabel{
		
		private String key;
		
		private String url;
		/**
		 * 菜单的名称
		 */
		private String name;
		/**
		 * 菜单的类型
		 */
		private String type;

		public String getKey() {
			return key;
		}
		public void setKey(String key) {
			this.key = key;
		}
		public String getUrl() {
			return url;
		}
		public void setUrl(String url) {
			this.url = url;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getType() {
			return type;
		}
		public void setType(String type) {
			this.type = type;
		}
		
		
		
	}
	
	
	
	/**
	 * 含有子菜单的菜单类型
	 */
	class SubMenuBean {
		/**
		 * 菜单的子菜单
		 */
		private List sub_button = new ArrayList();
		/**
		 * 菜单的名称
		 */
		private String name;
		
		public List getSub_button() {
			return sub_button;
		}

		public void setSub_button(List sub_button) {
			this.sub_button = sub_button;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
	}
	
	
	
}
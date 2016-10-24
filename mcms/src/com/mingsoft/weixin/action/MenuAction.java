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

package com.mingsoft.weixin.action;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.mingsoft.basic.constant.Const;
import com.mingsoft.util.StringUtil;
import com.mingsoft.weixin.biz.IMenuBiz;
import com.mingsoft.weixin.biz.INewsBiz;
import com.mingsoft.weixin.biz.IPassiveMessageBiz;
import com.mingsoft.weixin.constant.e.MenuStyleEnum;
import com.mingsoft.weixin.entity.MenuEntity;
import com.mingsoft.weixin.entity.NewsEntity;
import com.mingsoft.weixin.entity.PassiveMessageEntity;
import com.mingsoft.weixin.entity.WeixinEntity;
import com.mingsoft.weixin.util.MenuUtils;

/**
 * 
 * 微信菜单
 * @author 成卫雄(qq:330216230)
 * @version 
 * 版本号：100-000-000<br/>
 * 创建日期：2015年8月26日 下午2:50:43<br/>
 * 历史修订：<br/>
 */
@Controller
@RequestMapping("/${managerPath}/weixin/menu")
public class MenuAction extends BaseAction{
	
	/**
	 * 注入微信菜单业务层
	 */
	@Autowired
	private IMenuBiz menuBiz;
	
	/**
	 * 注入素材业务层
	 */
	@Autowired
	private INewsBiz newsBiz;
	
	/**
	 * 注入被动消息回复业务层
	 */
	@Autowired
	private IPassiveMessageBiz passiveMessageBiz;
	
	
	/**
	 * 生成微信菜单
	 * @param response
	 * @param request
	 */
	@RequestMapping("/generateMenu")
	@ResponseBody
	public void generateMenu(HttpServletResponse response,HttpServletRequest request){
		try {
			//获取appId
			int appId = this.getAppId(request);
			//取出微信实体
			WeixinEntity weixin = this.getWeixinSession(request);
			//若微信或微信相关字段为空
			if(weixin == null || weixin.getWeixinId()<=0 || StringUtil.isBlank(weixin.getWeixinAppSecret()) || StringUtil.isBlank(weixin.getWeixinAppID())){
				this.outJson(response, null, false);
				return;
			}
			//获取微信ID
			int weixinId = weixin.getWeixinId();
			//引入菜单工具类
			MenuUtils menuUtils = new MenuUtils(weixin.getWeixinAppID(),weixin.getWeixinAppSecret());
			//生成微信菜单，判断正确/错误
			Boolean temp = menuUtils.setMenu(this.menuBiz.queryMenu(appId,weixinId));
			this.outJson(response, null, temp);			
		} catch (Exception e) {
			LOG.error(e);
			this.outJson(response, null, false);
		}
	}
	
	/**
	 * 清除微信菜单
	 * @param response
	 * @param request
	 */
	@RequestMapping("/stopMenu")
	@ResponseBody
	public void stopMenu(HttpServletResponse response,HttpServletRequest request){
		//取出微信实体
		WeixinEntity weixin = this.getWeixinSession(request);
		//若微信或微信相关字段为空
		if(weixin == null || weixin.getWeixinId()<=0 || StringUtil.isBlank(weixin.getWeixinAppSecret()) || StringUtil.isBlank(weixin.getWeixinAppID())){
			this.outJson(response, null, false);
			return;
		}
		//引入菜单工具类
		MenuUtils menuUtils = new MenuUtils(weixin.getWeixinAppID(),weixin.getWeixinAppSecret());
		try {
			//清除微信菜单
			Boolean temp = menuUtils.removeMenu();
			this.outJson(response,null,temp);
		} catch (Exception e) {
			LOG.error(e);
			this.outJson(response, null, false);
		}
	}
	
	/**
	 * 菜单列表
	 * @param request
	 * @param mode
	 * @return 菜单列表
	 */
	@RequestMapping("/list")
	public String list(ModelMap mode,HttpServletRequest request){
		//取出微信实体
		WeixinEntity weixin = this.getWeixinSession(request);
		//获取微信ID
		int weixinId = weixin.getWeixinId();
		//获取应用ID
		int appId = this.getAppId(request);
		//根据微信id查询菜单列表
		List<MenuEntity> menuList = this.menuBiz.queryListById(appId,weixinId);
		mode.addAttribute("menuList", JSONObject.toJSONString(menuList));
		return Const.VIEW+"/weixin/menu/menu_list";
	}
	
	/**
	 * 新增菜单
	 * @param request
	 * @param mode
	 * @return 新建菜单页面
	 */
	@RequestMapping("/add")
	public String add(HttpServletRequest request,ModelMap mode){
		//创建空的菜单实体
		MenuEntity menu = new MenuEntity();
		mode.addAttribute("menu", menu);
		//取出微信实体
		WeixinEntity weixin = this.getWeixinSession(request);
		//获取微信ID
		int weixinId = weixin.getWeixinId();
		//获取appId
		int appId = this.getAppId(request);
		//根据微信id查询菜单列表
		List<MenuEntity> menuList = this.menuBiz.queryListById(appId,weixinId);
		//压入menuList，并转换为json格式
		mode.addAttribute("menuList", JSONObject.toJSONString(menuList));		
		List<Map.Entry<String, String>> menuStyle = this.getResources("com/mingsoft/weixin/resources/menu_style");
		mode.addAttribute("menuStyle", menuStyle);
		return Const.VIEW+"/weixin/menu/menu_form";
	}
	
	/**
	 * 保存菜单实体
	 * @param menu :菜单实体
	 * @param request
	 * @param response
	 */
	@RequestMapping("/save")
	@ResponseBody
	public void save(@ModelAttribute MenuEntity menu,HttpServletRequest request, HttpServletResponse response) {
		if(menu == null){
			this.outJson(response, null, false);
			return;
		}
		// 更新前判断数据是否合法
		if(!StringUtil.checkLength(menu.getMenuTitle(), 1,10)){
			this.outJson(response, null, false,getResString("err.length",this.getResString("weixin.menu.title"),"1","10"));
			return ;
		}
		//取出微信实体
		WeixinEntity weixin = this.getWeixinSession(request);
		if(weixin == null || weixin.getWeixinId()<=0){
			this.outJson(response, null, false);
			return;
		}
		//获取微信Id
		int weixinId = weixin.getWeixinId();
		//获取应用编号
		int appId = this.getAppId(request);
		menu.setMenuWeixinId(weixinId); //微信ID
		menu.setMenuAppId(appId); //应用编号	
		if(menu.getMenuStyle()==MenuStyleEnum.LINK.toInt()) {//如果是外连接
			//获取外连接地址
			String sourceUrl = request.getParameter("source_url");
			if(StringUtil.isBlank(sourceUrl)){
				this.outJson(response, null,false,this.getResString("err.empty", this.getResString("weixin.menu.url")));
				return;
			}
			//新增外连接菜单
			this.menuBiz.updateOrCreateLink(menu,sourceUrl);
		}else if(menu.getMenuStyle()==MenuStyleEnum.TEXT.toInt()){//如果是文本
			//获取文本内容
			String replyText = request.getParameter("replyText");
			if(StringUtil.isBlank(replyText)){
				this.outJson(response, null,false,this.getResString("err.empty", this.getResString("weixin.menu.text")));
				return;
			}
			//新增文本菜单
			this.menuBiz.updateOrCreateText(menu, replyText, appId);
		}else if(menu.getMenuStyle()==MenuStyleEnum.PIC_ARTICLE.toInt()){//如果是图文
			//获取图文id
			String picId = request.getParameter("picTextId");
			if(StringUtil.isBlank(picId) || picId.equals("0")){
				this.outJson(response, null,false,this.getResString("err.empty", this.getResString("weixin.menu.news")));
				return;
			}
			//新增图文菜单
			this.menuBiz.updateOrCreatePic(menu, picId, appId);
		}else if(menu.getMenuStyle()==MenuStyleEnum.KEYWORD.toInt()){ //如果是关键词
			//获取图文id
			String passiveMessageId =  request.getParameter("passiveMessageId");
			//获取关键词
			String keyword = request.getParameter("keyword");
			if(StringUtil.isBlank(passiveMessageId) || StringUtil.isBlank(keyword)){
				this.outJson(response, null, false, this.getResString("err.empty"));
				return;
			}
			//新增关键词菜单
			this.menuBiz.updateOrCreateKeyword(menu, passiveMessageId,keyword);
		}
		this.outJson(response, null,true,JSONObject.toJSONString(menu));
	}
	
	/**
	 * 根据菜单id删除菜单
	 * @param menuId 菜单ID
	 * @param response
	 * @param request
	 */
	@RequestMapping("/{menuId}/delete")
	@ResponseBody
	public void delete(@PathVariable("menuId") int menuId,HttpServletResponse response,HttpServletRequest request){
		if( menuId <= 0 ){
			this.outJson(response, null, false);
			return;
		}
		//获取应用id
		int appId = this.getAppId(request);
		//获取微信
		WeixinEntity weixin = this.getWeixinSession(request);
		//若微信或微信相关字段为空
		if(weixin == null || weixin.getWeixinId()<=0){
			this.outJson(response, null, false);
			return;
		}
		//获取微信ID
		int weixinId = weixin.getWeixinId();
		//判断该菜单下是否存在子菜单
		List<MenuEntity> menuList = this.menuBiz.queryByParentId(appId,weixinId,menuId);
		//若有子菜单，不能删除
		if(menuList.size()>0){
			this.outJson(response, null, false);
			return;
		}
		//获取菜单实体
		MenuEntity menu = (MenuEntity) this.menuBiz.getEntity(menuId);
		if(menu == null){
			this.outJson(response, null, false);
			return;
		}
		//删除菜单
		this.menuBiz.deleteEntity(menuId);
		this.outJson(response, null, true);
	}
	
	/**
	 * 编辑菜单
	 * @param menuId 菜单ID
	 * @param mode 
	 * @param request
	 * @return 编辑菜单页面
	 */
	@RequestMapping("/{menuId}/edit")
	public String edit(@PathVariable("menuId") int menuId,ModelMap mode,HttpServletRequest request){
		//根据菜单Id查询菜单实体
		MenuEntity menu = (MenuEntity) this.menuBiz.getEntity(menuId);
		//获取appId
		int appId = this.getAppId(request);
		//根据微信id查询菜单列表
		List<MenuEntity> menuList = this.menuBiz.queryListById(appId,menu.getMenuWeixinId());
		mode.addAttribute("menuList", JSONObject.toJSONString(menuList));		
		if(menu.getMenuStyle()==MenuStyleEnum.LINK.toInt()){//如果是外连接
			if(!StringUtil.isBlank(menu.getMenuUrl())){
				mode.addAttribute("sourceUrl",menu.getMenuUrl());
			}
		}else if(menu.getMenuStyle()==MenuStyleEnum.TEXT.toInt()){//如果是文本
			PassiveMessageEntity message = (PassiveMessageEntity) passiveMessageBiz.getEntityBySendMessage(appId,menu.getMenuWeixinId(),MenuStyleEnum.KEYWORD.toInt(),menu.getMenuUrl(),0);
			//查询消息表中的相关数据
			NewsEntity news = this.newsBiz.getNewsByNewsId(message.getPassiveMessageNewsId());
			if(news!=null){
				//将用户填写的地址，返回到页面
				mode.addAttribute("replyText",news.getNewsContent());
			}
		}else if(menu.getMenuStyle()==MenuStyleEnum.PIC_ARTICLE.toInt()){//如果是图文
			PassiveMessageEntity message = (PassiveMessageEntity) passiveMessageBiz.getEntityBySendMessage(appId,menu.getMenuWeixinId(),MenuStyleEnum.KEYWORD.toInt(),menu.getMenuUrl(),0);
			//查询消息表中的相关数据
			NewsEntity news = this.newsBiz.getNewsByNewsId(message.getPassiveMessageNewsId());
			if(news!=null){
				//将用户填写的地址，返回到页面
				mode.addAttribute("picTextId",news.getNewsId());
			}
		}else if(menu.getMenuStyle()==MenuStyleEnum.KEYWORD.toInt()){//如果是关键词
			//获取关键词回复实体
			PassiveMessageEntity message = (PassiveMessageEntity) passiveMessageBiz.getEntityBySendMessage(appId,menu.getMenuWeixinId(),MenuStyleEnum.KEYWORD.toInt(),menu.getMenuUrl(),0);
			if(message != null){
				mode.addAttribute("passiveMessageId",message.getPassiveMessageId());
				mode.addAttribute("keyword",message.getPassiveMessageKey());
			}		
		}
		mode.addAttribute("menuId", menuId);
		mode.addAttribute("menu", menu);
		List<Map.Entry<String, String>> menuStyle = this.getResources("com/mingsoft/weixin/resources/menu_style");
		mode.addAttribute("menuStyle", menuStyle);
		return Const.VIEW+"/weixin/menu/menu_form";
	}
	
	/**
	 * 更新菜单实体
	 * @param menu :菜单实体
	 * @param request
	 * @param response
	 */
	@RequestMapping("/update")
	@ResponseBody
	public void update(@ModelAttribute MenuEntity menu,HttpServletRequest request, HttpServletResponse response){
		//若菜单不存在
		if(menu == null){
			this.outJson(response, null, false);
			return;
		}
		//更新前判断数据是否合法
		if(!StringUtil.checkLength(menu.getMenuTitle(), 1,16)){
			this.outJson(response, null, false,getResString("err.length",this.getResString("fieldTipsName"),"1","16"));
			return ;
		}
		//取出微信实体
		WeixinEntity weixin = this.getWeixinSession(request);
		//若微信或微信相关字段为空
		if(weixin == null || weixin.getWeixinId()<=0){
			this.outJson(response, null, false);
			return;
		}
		//获取微信ID
		int weixinId = weixin.getWeixinId();
		//获取应用编号
		int appId = this.getAppId(request);
		menu.setMenuWeixinId(weixinId); //微信ID
		menu.setMenuAppId(appId); //应用编号
		if(menu.getMenuStyle()==MenuStyleEnum.LINK.toInt()){//如果是外连接
			//获取外连接地址
			String sourceUrl = request.getParameter("source_url");
			if(StringUtil.isBlank(sourceUrl)){
				this.outJson(response, null,false,this.getResString("err.empty", this.getResString("weixin.menu.urlt")));
				return;
			}
			//更新外连接菜单
			this.menuBiz.updateOrCreateLink(menu,sourceUrl);
		}else if(menu.getMenuStyle()==MenuStyleEnum.TEXT.toInt()){//如果是文本
			//获取文本内容
			String replyText = request.getParameter("replyText");
			if(StringUtil.isBlank(replyText)){
				this.outJson(response, null,false,this.getResString("err.empty", this.getResString("weixin.menu.text")));
				return;
			}
			//更新文本菜单
			this.menuBiz.updateOrCreateText(menu, replyText,appId);
		}else if(menu.getMenuStyle()==MenuStyleEnum.PIC_ARTICLE.toInt()){//如果是图文
			//获取图文ID	
			String picId = request.getParameter("picTextId");
			if(StringUtil.isBlank(picId)|| picId.equals("0")){
				this.outJson(response, null,false,this.getResString("err.empty", this.getResString("weixin.menu.news")));
				return;
			}
			//更新图文菜单
			this.menuBiz.updateOrCreatePic(menu, picId, appId);
		}else if(menu.getMenuStyle()==MenuStyleEnum.KEYWORD.toInt()){//如果是关键词
			//获取关键词回复ID
			String passiveMessageId =  request.getParameter("passiveMessageId");
			//获取关键词
			String keyword = request.getParameter("keyword");
			if(StringUtil.isBlank(passiveMessageId) || StringUtil.isBlank(keyword)){
				this.outJson(response, null, false, this.getResString("err.empty"));
				return;
			}
			//更新关键词菜单
			this.menuBiz.updateOrCreateKeyword(menu, passiveMessageId,keyword);
		}	
		this.outJson(response, null,true,JSONObject.toJSONString(menu));
	}
	
	/**
	 * 改变菜单节点
	 * @param menuId 菜单ID
	 * @param menuMenuId 父菜单ID
	 * @param response
	 */
	@RequestMapping("{menuId}/{menuMenuId}/changeParent")
	@ResponseBody
	public void changeParent(@PathVariable("menuId") int menuId,@PathVariable("menuMenuId") int menuMenuId, HttpServletResponse response){
		//对菜单ID做合法性判断
		if(menuId<=0 || menuMenuId<=0 || menuId==menuMenuId){
			this.outJson(response, null, false);
			return;
		}
		//根据菜单id查找实体
		MenuEntity menu = (MenuEntity) this.menuBiz.getEntity(menuId);
		//若菜单不存在
		if(menu == null){
			this.outJson(response, null, false);
			return;
		}
		//保存父菜单ID
		menu.setMenuMenuId(menuMenuId);
		//更新菜单
		menuBiz.updateEntity(menu);
		this.outJson(response, null,true);
	}
	

	/**
	 * 获取微信菜单的类型数组
	 * @param path 路径
	 * @return 菜单的类型列表
	 */
	private List<Map.Entry<String, String>> getResources(String path) {
		Map<String, String> map = getMapByProperties(path);
		Set<Entry<String, String>> set = map.entrySet();
		List<Map.Entry<String, String>> menuType = new ArrayList<Map.Entry<String, String>>();
		for (Iterator<Entry<String, String>> it = set.iterator(); it.hasNext();) {
			Map.Entry<String, String> entry = (Map.Entry<String, String>) it.next();
			menuType.add(entry);
		}
		return menuType;
	}
}
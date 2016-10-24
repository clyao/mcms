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

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mingsoft.basic.biz.IModelBiz;
import com.mingsoft.basic.biz.IRoleModelBiz;
import com.mingsoft.basic.entity.ModelEntity;
import com.mingsoft.basic.entity.RoleModelEntity;
import com.mingsoft.util.PageUtil;
import com.mingsoft.util.StringUtil;
import com.mingsoft.weixin.biz.IWeixinBiz;
import com.mingsoft.weixin.constant.SessionConst;
import com.mingsoft.weixin.constant.e.WeixinTypeEnum;
import com.mingsoft.weixin.entity.WeixinEntity;

/**
 * 
 * 微信基础信息控制层
 * 
 * @author 付琛 QQ1658879747
 * @version 版本号：100-000-000<br/>
 *          创建日期：2015年11月25日 历史修订：<br/>
 */
@Controller
@RequestMapping("/${managerPath}/weixin")
public class WeixinAction extends BaseAction {

	/**
	 * 注入微信基础业务层
	 */
	@Autowired
	private IWeixinBiz weixinBiz;

	@Autowired
	private IModelBiz modelBiz;
	
	@Autowired
	private IRoleModelBiz roleModelBiz;

	/**
	 * 查询微信列表
	 * 
	 * @param request
	 * @param mode
	 * @return manager/weixin/weixin_list.ftl
	 */
	@RequestMapping("/list")
	public String queryList(HttpServletRequest request, ModelMap mode) {
		// 得到appId
		int appId = this.getAppId(request);
		// 获取当前页码
		int pageNo = this.getPageNo(request);
		// 查询当前应用下的微信总数
		int count = this.weixinBiz.getCountByAppId(appId);
		// 分页工具类
		PageUtil page = new PageUtil(pageNo, count, "/manager/weixin/list.do");

		// 查询分页列表,获取微信集合
		List<WeixinEntity> weixinList = weixinBiz.queryAllByAppId(appId, page);

		// 获取微信类型
		List<Map<String, Object>> weixinTypeList = this.weixinEnumToList(WeixinTypeEnum.class);

		// 将微信信息压入list
		mode.addAttribute("weixinList", weixinList);
		mode.addAttribute("weixinTypeList", weixinTypeList);
		mode.addAttribute("page", page);
		// 返回manager/weixin/weixin_list.ftl
		return view("/weixin/weixin_list");
	}

	/**
	 * 返回新增页面
	 * 
	 * @param mode
	 * @param request
	 * @return 新增微信页面
	 */
	@RequestMapping("/add")
	public String add(ModelMap mode, HttpServletRequest request) {
		WeixinEntity weixin = new WeixinEntity();
		mode.addAttribute("weixin", weixin);
		int appId = this.getAppId(request);
		mode.addAttribute("appId", appId);
		// 返回路径
		return view("/weixin/weixin_form");
	}

	/**
	 * 返回编辑页面
	 * 
	 * @param weixinId
	 *            微信ID
	 * @param mode
	 * @param request
	 * @return 编辑微信页面
	 */
	@RequestMapping("/{weixinId}/edit")
	public String edit(@PathVariable int weixinId, ModelMap mode, HttpServletRequest request) {
		if (weixinId <= 0) {
			// 非法
			return this.redirectBack(request, true);
		}
		// 得到实体信息
		WeixinEntity weixin = weixinBiz.getEntityById(weixinId);
		// 获取应用编号
		int appId = this.getAppId(request);
		mode.addAttribute("appId", appId);
		mode.addAttribute("weixin", weixin);
		return view("/weixin/weixin_form");
	}

	/**
	 * 微信功能列表页
	 * 
	 * @param weixinId
	 *            微信ID
	 * @param mode
	 * @param request
	 * @return 微信功能列表页
	 */
	@RequestMapping("/{weixinId}/function")
	public String function(@PathVariable int weixinId, ModelMap mode, HttpServletRequest request) {
		if (weixinId <= 0) {
			// 非法
			return this.redirectBack(request, true);
		}
		mode.addAttribute("weixinId", weixinId);
		// 根据微信ID获取微信实体
		WeixinEntity weixin = this.weixinBiz.getEntityById(weixinId);
		// 获取公众号名称
		String weixinName = weixin.getWeixinName();
		// 压入微信公众号名称
		mode.addAttribute("weixinName", weixinName);
		// 微信实体压进session
		this.setWeixinSession(request, SessionConst.WEIXIN_SESSION, weixin);
		return view("/weixin/weixin_function");
	}

	/**
	 * 持久化新增微信帐号
	 * 
	 * @param weixin
	 *            微信帐号信息
	 * @param request
	 * @param response
	 */
	@RequestMapping("/save")
	@ResponseBody
	public void save(@ModelAttribute WeixinEntity weixin, HttpServletRequest request, HttpServletResponse response) {
		if (weixin == null) {
			this.outJson(response, null, false);
			return;
		}
		// 获取appId
		int appId = this.getAppId(request);
		// 设置微信的应用编号
		weixin.setAppId(appId);
		// 问题：去掉图片中的"|"
		if (!StringUtil.isBlank(weixin.getWeixinHeadImg())) {
			weixin.setWeixinHeadImg(weixin.getWeixinHeadImg().replace("|", ""));
		}
		if (!StringUtil.isBlank(weixin.getWeixinImage())) {
			weixin.setWeixinImage(weixin.getWeixinImage().replace("|", ""));
		}
		// 保存微信
		weixinBiz.saveEntity(weixin);
		this.outJson(response, null, true, null);
	}

	/**
	 * 批量删除微信
	 * 
	 * @param response
	 * @param response
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public void delete(HttpServletResponse response, HttpServletRequest request) {
		// 得到需要删除的微信ID数组
		String[] weixinIds = request.getParameterValues("weixinIds");
		// 判断数组是否为空
		if (StringUtil.isBlank(weixinIds)) {
			// 返回json数据
			this.outJson(response, null, false);
			return;
		}
		// 判断字符串数组是否转成integer型数组
		if (!StringUtil.isIntegers(weixinIds)) {
			// 返回json数据
			this.outJson(response, null, false);
			return;
		}
		// 得到ID数组并将字符串数组转化为int型数组
		int[] ids = StringUtil.stringsToInts(weixinIds);
		// 根据ID批量删除微信
		weixinBiz.deleteByIds(ids);
		// 返回json数据
		this.outJson(response, null, true);
	}

	/**
	 * 更新微信实体
	 * 
	 * @param weixin
	 *            微信实体
	 * @param weixinId
	 *            微信ID
	 * @param response
	 */
	@RequestMapping("/{weixinId}/update")
	@ResponseBody
	public void update(@ModelAttribute WeixinEntity weixin, @PathVariable int weixinId, HttpServletResponse response) {
		if (weixinId <= 0) {
			this.outJson(response, null, false);
			return;
		}
		// 保存微信ID
		weixin.setWeixinId(weixinId);
		// 问题：去掉图片中的"|"
		if (!StringUtil.isBlank(weixin.getWeixinHeadImg())) {
			weixin.setWeixinHeadImg(weixin.getWeixinHeadImg().replace("|", ""));
		}
		if (!StringUtil.isBlank(weixin.getWeixinImage())) {
			weixin.setWeixinImage(weixin.getWeixinImage().replace("|", ""));
		}
		// 更新微信
		weixinBiz.updateEntity(weixin);
		this.outJson(response, null, true);
	}

	@RequestMapping("/initWeixin")
	@ResponseBody
	public void initWeixin(HttpServletRequest request, HttpServletResponse response) {
		
		String str = "";
		ModelEntity model = modelBiz.getEntityByModelCode("05000000");
		if (model == null) {
			
			model = new ModelEntity();
			model.setModelTitle("微信");
			model.setModelCode("05000000");
			model.setModelIcon("&#xe834;");
			model.setModelManagerId(0);
			model.setModelDatetime(new Timestamp(System.currentTimeMillis()));
			
			modelBiz.saveEntity(model);
			
			ModelEntity modelChild = new ModelEntity();
			modelChild.setModelModelId(model.getModelId());
			modelChild.setModelTitle("公众号管理");
			modelChild.setModelCode("05010000");
			modelChild.setModelUrl("/weixin/list.do");
			modelChild.setModelManagerId(0);
			modelChild.setModelDatetime(new Timestamp(System.currentTimeMillis()));
			
			modelBiz.saveEntity(modelChild);
			
			
			List list = new ArrayList();
			RoleModelEntity roleModel = new RoleModelEntity();
			roleModel.setModelId(model.getModelId());
			roleModel.setRoleId(this.getManagerBySession(request).getManagerRoleID());
			list.add(roleModel);
			
			RoleModelEntity roleModel2 = new RoleModelEntity();
			roleModel2.setModelId(modelChild.getModelId());
			roleModel2.setRoleId(this.getManagerBySession(request).getManagerRoleID());
			list.add(roleModel2);
			
			
			roleModelBiz.saveEntity(list);
			this.outString(response, "微信插件安装成功!请刷新后台主界面");
		} else {
			
			this.outString(response, "插件不能重复安装");
			
		}
		
		
		
	}

}
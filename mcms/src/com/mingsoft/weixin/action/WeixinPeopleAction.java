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
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.mingsoft.basic.constant.Const;
import com.mingsoft.basic.constant.e.CookieConstEnum;
import com.mingsoft.util.PageUtil;
import com.mingsoft.weixin.biz.IWeixinPeopleBiz;
import com.mingsoft.weixin.entity.WeixinEntity;
import com.mingsoft.weixin.entity.WeixinPeopleEntity;

/**
 * 微信基用户控制层
 * Copyright: Copyright (c) 2014 - 2015
 * @author 成卫雄   QQ:330216230
 * Comments:微信基用户控制层
 * Create Date:2014-10-5
 * Modification history:
 */
@Controller
@RequestMapping("/${managerPath}/weixin/weixinPeople")
public class WeixinPeopleAction extends BaseAction{
	
	/**
	 * 注入微信用户业务层
	 */
	@Autowired
	private IWeixinPeopleBiz weixinPeopleBiz;
	
	private static final int PEOPLE_NUM = 200;
	/**
	 * 分页查询所有的微信用户信息
	 * @param request
	 * @param mode
	 * @param response
	 * @return 微信用户列表
	 */
	@SuppressWarnings("static-access")
	@RequestMapping("/list")
	public String list(HttpServletRequest request,ModelMap mode, HttpServletResponse response){
		//取出微信实体
		WeixinEntity weixin = this.getWeixinSession(request);
		//获取应用ID 
		int appId = this.getAppId(request);
		//获取当前页码
		int pageNo = this.getPageNo(request);
		//查询总记录数
		int recordCount =weixinPeopleBiz.queryCount(appId,weixin.getWeixinId());
		//创建分页对象
		PageUtil page=new PageUtil(pageNo,recordCount,"list.do?");
		//保存cookie值
		this.setCookie(request, response, CookieConstEnum.PAGENO_COOKIE, String.valueOf(pageNo));
		//分页查询
		List<WeixinPeopleEntity> listPeople =weixinPeopleBiz.queryList(appId,weixin.getWeixinId(), page, "wp.PW_OPEN_ID", false);
		JSONObject json = new JSONObject();
		mode.addAttribute("peopleList",json.toJSON(listPeople).toString());
		mode.addAttribute("page",page);
		return Const.VIEW+"/weixin/people/people_list";
	}
	
	/**
	 * 导入微信所有用户
	 * @param request
	 */
	@RequestMapping("/importPeople")
	public void importPeople(HttpServletRequest request,HttpServletResponse response){
		//取出微信实体
		WeixinEntity weixin = this.getWeixinSession(request);
		//调用递归方法,openId为""表示从第一个用户开始
		Boolean bool = weixinPeopleBiz.recursionImportPeople(weixin,"",PEOPLE_NUM);
		this.outJson(response, null, bool);
	}
	

	
	/**
	 * 根据用户ID获取用户实体
	 * @param peopleId 用户编号
	 * @param response
	 */
	@RequestMapping("/{peopleId}/getPeopleById")
	@ResponseBody
	public void getPeopleById(@PathVariable int peopleId,HttpServletResponse response){
		if(peopleId<=0){
			this.outJson(response, null, false);
			return;
		}
		//根据用户编号查询用户实体
		WeixinPeopleEntity people = weixinPeopleBiz.getPeopleById(peopleId);
		if(people == null){
			this.outJson(response, null, false);
			return;
		}
		//返回json的格式用户实体信息
		this.outJson(response,null, true ,JSONObject.toJSONString(people));
	}
	
	
}
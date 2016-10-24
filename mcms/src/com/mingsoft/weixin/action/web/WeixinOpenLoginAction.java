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

package com.mingsoft.weixin.action.web;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mingsoft.people.entity.PeopleEntity;
import com.mingsoft.util.StringUtil;
import com.mingsoft.weixin.action.BaseAction;
import com.mingsoft.weixin.biz.IWeixinBiz;
import com.mingsoft.weixin.biz.IWeixinPeopleBiz;
import com.mingsoft.weixin.entity.WeixinEntity;
import com.mingsoft.weixin.entity.WeixinPeopleEntity;
import com.mingsoft.weixin.util.WeixinOpenLoginUtil;
import com.mingsoft.weixin.util.bean.WeixinPeopleEntityUtils;

/**
 * 
 * 微信开放平台用户登录
 * @author 成卫雄(qq:330216230)
 * @version 
 * 版本号：100-000-000<br/>
 * 创建日期：2015年9月23日 下午5:00:25<br/>
 * 历史修订：<br/>
 */
@Controller("webWeixinOpenLogin")
@RequestMapping("/weixin/open/login")
public class WeixinOpenLoginAction extends BaseAction{
	
	/**
	 * 微信业务层
	 */
	@Autowired
	private IWeixinBiz weixinBiz;
	
	/**
	 * 微信用户业务层
	 */
	@Autowired
	private IWeixinPeopleBiz weixinPeopleBiz;
	
	/**
	 * 获取微信开放平台拼装JS请求的必须数据<br>
	 * @param id 微信ID
	 */
	@ResponseBody
	@RequestMapping("/{id}/getUrlByJs")
	public void getUrlByJs(@PathVariable("id")Integer id,HttpServletRequest request,HttpServletResponse response){
		//获取当前用户访问地址
		String reqUrl = this.getUrl(request);
		LOG.debug(reqUrl);
		this.outJson(response, null, true, reqUrl);
	}
	
	
	/**
	 * 微信开放平台用户登录</br>
	 * 直接返回获取用户信息的JSON</br>
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/getUser",method=RequestMethod.GET)
	public String getUser(HttpServletRequest request,HttpServletResponse response){
		//接收用户授权码

		String code = request.getParameter("code");
		//微信地址可携带的返回参数，这里作为重定向地址的查询使用
		String state = request.getParameter("state");
		LOG.debug("获取到的code:"+code+"获取到的state:"+state);
		//获取微信ID
		String weixinId = request.getParameter("id");
		
		//验证参数
		if(StringUtil.isBlank(code) || StringUtil.isBlank(state) || StringUtil.isBlank(weixinId)){
			LOG.error("-------关键性参数错误!");
			//重定向到错误地址
			return "redirect:/500/error.do";
		}
		
		//查询微信详细信息
		WeixinEntity weixinEntity = weixinBiz.getEntityById(Integer.parseInt(weixinId));
		if(weixinEntity == null){
			LOG.error("-----未查询到对应授权的微信基础信息！");
			//返回错误地址
			return "redirect:/500/error.do";
		}
		
		//获取微信用户信息
		WeixinOpenLoginUtil weixinOpenUtil = new WeixinOpenLoginUtil(weixinEntity.getWeixinAppID(),weixinEntity.getWeixinAppSecret());
		String userInfoJson = weixinOpenUtil.getUserInfo(code);
		LOG.debug(userInfoJson);
		WeixinPeopleEntity weixinPeopleEntity = WeixinPeopleEntityUtils.userInfoToWeixinPeople(userInfoJson, weixinEntity.getAppId(), weixinEntity.getWeixinId());
		if(weixinPeopleEntity ==  null){
			LOG.error("------获取用户信息失败！"+userInfoJson);
			//返回错误地址
			return "redirect:/500/error.do";		
		}
		
		//检测系统中是否已经存在该用户
		WeixinPeopleEntity weixinPeople = weixinPeopleBiz.checkSoleWeixinPeople(weixinPeopleEntity);
		PeopleEntity people = new PeopleEntity();
		people.setPeopleId(weixinPeople.getPeopleId());
		//将用户的openId压入session:weixn_people_session
		this.setPeopleBySession(request, people);
		//将微信实体压入session:weinxin_session
		this.setWeixinSession(request,com.mingsoft.weixin.constant.SessionConst.WEIXIN_SESSION,weixinEntity);
		
		LOG.debug("重定向地址："+state);
		return "redirect:"+state;			
	}
	 
	
	
	
	
}
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

package com.mingsoft.weixin.servlet.open;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.mingsoft.util.StringUtil;
import com.mingsoft.weixin.biz.IOauthBiz;
import com.mingsoft.weixin.biz.IWeixinBiz;
import com.mingsoft.weixin.biz.IWeixinPeopleBiz;
import com.mingsoft.weixin.entity.OauthEntity;
import com.mingsoft.weixin.entity.WeixinEntity;
import com.mingsoft.weixin.entity.WeixinPeopleEntity;
import com.mingsoft.weixin.servlet.BaseServlet;
import com.mingsoft.weixin.util.WeixinOpenLoginUtil;
import com.mingsoft.weixin.util.bean.WeixinPeopleEntityUtils;

/**
 * 微信开放平台用户授权登录
 * @author 成卫雄(qq:330216230)
 * @version 
 * 版本号：100-000-000<br/>
 * 创建日期：2015年8月28日 上午9:18:15<br/>
 * 历史修订：<br/>
 */
@WebServlet("/weixin/open/oauth")
public class OpenOauthServlet extends BaseServlet{

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//接收用户授权码
		String code = request.getParameter("code");
		//微信地址可携带的返回参数，这里作为重定向地址的查询使用
		String state = request.getParameter("state");
		logger.debug("获取到的code:"+code+"获取到的state:"+state);
		
		//验证参数
		if(StringUtil.isBlank(code) || !StringUtil.isInteger(state)){
			logger.error("-------关键性参数错误!");
			//重定向到错误地址
			response.sendRedirect(request.getContextPath()+"/500/error.do");
			return ;
		}
		
		//注入网页授权2.0业务层
		IOauthBiz oauthBiz = (IOauthBiz) this.getBean(request.getServletContext(), "oauthBiz");
		
		//根据设置的state查询授权详细信息
		OauthEntity oauthEntity = (OauthEntity) oauthBiz.getEntity(Integer.parseInt(state));
		if(oauthEntity == null){
			logger.error("-----未查询到授权信息！");
			//返回错误地址
			response.sendRedirect(request.getContextPath()+"/500/error.do");
			return ;			
		}
		
		//注入微信基础信息业务层
		IWeixinBiz weixinBiz = (IWeixinBiz) this.getBean(request.getServletContext(),"weixinBiz");
		//查询微信详细信息
		int weixinId = oauthEntity.getOauthWeixinId();
		WeixinEntity weixinEntity = weixinBiz.getEntityById(weixinId);
		if(weixinEntity == null){
			logger.error("-----未查询到对应授权的微信基础信息！");
			//返回错误地址
			response.sendRedirect(request.getContextPath()+oauthEntity.getOauthErrorUrl());
			return ;
		}
		
		//获取微信用户信息
		WeixinOpenLoginUtil weixinOpenUtil = new WeixinOpenLoginUtil(weixinEntity.getWeixinAppID(),weixinEntity.getWeixinAppSecret());
		String userInfoJson = weixinOpenUtil.getUserInfo(code);
		WeixinPeopleEntity weixinPeopleEntity = WeixinPeopleEntityUtils.userInfoToWeixinPeople(userInfoJson, weixinEntity.getAppId(), weixinEntity.getWeixinId());
		if(weixinPeopleEntity ==  null){
			logger.error("------获取用户信息失败！"+userInfoJson);
			//返回错误地址
			response.sendRedirect(request.getContextPath()+oauthEntity.getOauthErrorUrl());
			return ;			
		}
		
		//注入微信用户控制层
		IWeixinPeopleBiz weixinPeopleBiz = (IWeixinPeopleBiz) this.getBean(request.getServletContext(),"weixinPeopleBiz");
		
		//检测系统中是否已经存在该用户
		WeixinPeopleEntity weixinPeople = weixinPeopleBiz.checkSoleWeixinPeople(weixinPeopleEntity);
		
		//获取用户openId
		String openId = weixinPeople.getWeixinPeopleOpenId();
		
		//将用户的openId压入session:weixn_people_session
		this.setPeopleBySession(request, weixinPeople);
		//将微信实体压入session:weinxin_session
		this.setWeixinSession(request,com.mingsoft.weixin.constant.SessionConst.WEIXIN_SESSION,weixinEntity);
		
		request.setAttribute("openId",openId);
		logger.debug("重定向地址："+oauthEntity.getOauthSuccessUrl());
		response.sendRedirect(request.getContextPath()+StringUtil.buildUrl(oauthEntity.getOauthSuccessUrl(), "openId="+openId));
	}
	
}
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

package com.mingsoft.weixin.servlet;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mingsoft.weixin.servlet.BaseServlet;
import com.mingsoft.util.StringUtil;
import com.mingsoft.weixin.biz.IOauthBiz;
import com.mingsoft.weixin.biz.IWeixinBiz;
import com.mingsoft.weixin.biz.IWeixinPeopleBiz;
import com.mingsoft.weixin.entity.OauthEntity;
import com.mingsoft.weixin.entity.WeixinEntity;
import com.mingsoft.weixin.entity.WeixinPeopleEntity;
import com.mingsoft.weixin.util.OauthUtils;
import com.mingsoft.weixin.util.bean.WeixinPeopleEntityUtils;

/**
 * 授权通用地址,主要用户手机段微信wap页面授权</br>
 * state扩展字段，长度为128长度,详细见微信开发接口</br>
 * @author killfen
 * @version 
 * 版本号：100-000-000<br/>
 * 创建日期：2015年8月30日 下午5:05:09<br/>
 * 历史修订：<br/>
 */
@WebServlet(urlPatterns="/weixin/oauth")
public class OauthServlet extends BaseServlet {
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {	
		String code = req.getParameter("code"); //用户同意授权就可以获得
		String state = req.getParameter("state");
		logger.debug("获取的code:"+code+"------获取state:"+state);
		
		if(StringUtil.isBlank(state) || StringUtil.isBlank(code)){
			logger.debug("关键性参数错误");
			//返回错误地址
			resp.sendRedirect(req.getContextPath()+"/500/error.do");
			return ;
		}
		
		//网页授权2.0业务层
		IOauthBiz oauthBiz = (IOauthBiz) getBean(req.getServletContext(),"oauthBiz");
		
		//根据解析到的state查询网页2.0授权信息
		OauthEntity oauth = (OauthEntity) oauthBiz.getEntity(Integer.parseInt(state));
		
		if(oauth == null){
			logger.debug("关键性参数错误");
			//返回错误地址
			resp.sendRedirect(req.getContextPath()+"/500/error.do");
			return ;			
		}
		
		//微信基础信息业务层
		IWeixinBiz weixinBiz = (IWeixinBiz) getBean(req.getServletContext(), "weixinBiz");

		//微信基础信息ID
		int weixinId = oauth.getOauthWeixinId();
		WeixinEntity weixin = weixinBiz.getEntityById(weixinId);
		
		//获取用户openid
		OauthUtils au = new OauthUtils(weixin.getWeixinAppID(),weixin.getWeixinAppSecret());
		Map<String,Object> userMap = au.getUser(code);
		WeixinPeopleEntity weixinPeopleEntity = WeixinPeopleEntityUtils.userInfoToWeixinPeople(userMap,weixin.getAppId(),weixin.getWeixinId());
		if(weixinPeopleEntity == null){
			resp.sendRedirect(req.getContextPath()+"/500/error.do");
			return;			
		}
		
		//根据AppId和用户OpenId查询用户实体并将用户信息压入到session中
		IWeixinPeopleBiz weixinPeopleBiz = (IWeixinPeopleBiz) getBean(req.getServletContext(),"weixinPeopleBiz");
		
		//检测系统中是否已经存在该用户,如果不存在则将该用户持久化
		WeixinPeopleEntity weixinPeople = weixinPeopleBiz.checkSoleWeixinPeople(weixinPeopleEntity);
		
		//将用户的openId压入session:weixn_people_session
		this.setPeopleBySession(req, weixinPeople);
		//将微信实体压入session:weinxin_session
		this.setWeixinSession(req,com.mingsoft.weixin.constant.SessionConst.WEIXIN_SESSION,weixin);
		
		logger.debug("-----------------------授权获得openid:" + userMap.get("openid"));
		req.setAttribute("openId", userMap.get("openid").toString());
		logger.debug("重定向地址："+oauth.getOauthSuccessUrl());
		resp.sendRedirect(req.getContextPath()+StringUtil.buildUrl(oauth.getOauthSuccessUrl(), "openId="+userMap.get("openid")));
	}
	
}
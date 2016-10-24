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

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mingsoft.util.StringUtil;
import com.mingsoft.weixin.biz.IOauthBiz;
import com.mingsoft.weixin.biz.IWeixinBiz;
import com.mingsoft.weixin.constant.Const;
import com.mingsoft.weixin.constant.e.OauthTypeEnum;
import com.mingsoft.weixin.entity.OauthEntity;
import com.mingsoft.weixin.entity.WeixinEntity;
import com.mingsoft.weixin.util.OauthUtils;

/**
 * 
 * 微信APP授权登录
 * @author 成卫雄(qq:330216230)
 * @version 
 * 版本号：100-000-000<br/>
 * 创建日期：2015年8月30日 上午9:25:05<br/>
 * 历史修订：<br/>
 */
@WebServlet("/weixin/login")
public class LoginServlet extends BaseServlet{

	/**
	 * 接受登录请求</br>
	 * 重定向到微信请求地址</br>
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//获取当前授权实体Id
		String oauthId = request.getParameter("id");
		if(!StringUtil.isInteger(oauthId)){
			logger.debug("----------oauth err");
			//返回错误地址
			response.sendRedirect(request.getContextPath()+"/500/error.do");
			return ;
		}
		
		//获取微信ID
		IOauthBiz oauthBiz = (IOauthBiz) this.getBean(request.getServletContext(), "oauthBiz");
		OauthEntity oauth = (OauthEntity) oauthBiz.getEntity(Integer.parseInt(oauthId));
		if(oauth == null){
			logger.debug("----------OauthEntity null err");
			//返回错误地址
			response.sendRedirect(request.getContextPath()+"/500/error.do");
			return ;			
		}
		int weixinId = oauth.getOauthWeixinId();
		
		//根据微信ID查询授权地址
		IWeixinBiz weixinBiz = (IWeixinBiz) this.getBean(request.getServletContext(),"weixinBiz");
		WeixinEntity weixin = weixinBiz.getEntityById(weixinId);
		if(oauth == null){
			logger.debug("----------WeixinEntity null err");
			//返回错误地址
			response.sendRedirect(request.getContextPath()+oauth.getOauthErrorUrl());
			return ;			
		}
		//微信授权重定向地址
		String weixinOauthUrl = weixin.getWeixinOauthUrl();
		if(StringUtil.isBlank(weixinOauthUrl)){
			logger.debug("----------weixinOauthUrl null err");
			//返回错误地址
			response.sendRedirect(request.getContextPath()+oauth.getOauthErrorUrl());
			return ;				
		}
		
		//拼装微信从定向授权地址
		if(oauth.getOauthType() == OauthTypeEnum.SCOPE_USERINFO.toInt()){//弹出授权界面
			String oauthUrl = OauthUtils.getCodeUrl(weixin.getWeixinAppID(),weixinOauthUrl+Const.OAUTH_APP_URL,false,oauthId);
			logger.debug("weixin open oauth:"+oauthUrl);
			//发起重定向请求
			response.sendRedirect(oauthUrl);				
		}else if(oauth.getOauthType() == OauthTypeEnum.SCOPE_BASE.toInt()){//不弹出授权界面
			String oauthUrl = OauthUtils.getCodeUrl(weixin.getWeixinAppID(),weixinOauthUrl+Const.OAUTH_APP_URL,true,oauthId);
			logger.debug("weixin open oauth:"+oauthUrl);
			//发起重定向请求
			response.sendRedirect(oauthUrl);	
		}
	}

	
}
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
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mingsoft.basic.servlet.BaseServlet;
import com.mingsoft.util.StringUtil;
import com.mingsoft.util.proxy.Proxy;
import com.mingsoft.util.proxy.Result;
import com.mingsoft.weixin.biz.IWeixinBiz;
import com.mingsoft.weixin.entity.WeixinEntity;

/**
 * 
 * @author killfen
 * @version 微信接口代理，内部开发测试使用，由于微信接口设置需要80端口，而现在的家用宽带无法向外网提供80服务，只能通过代理来进行测试微信接口
 * 版本号：100-000-000<br/>
 * 创建日期：2015年8月25日 下午4:12:26<br/>
 * 历史修订：<br/>
 */
@WebServlet(urlPatterns="/weixin/proxytoken")
public class ProxyTokenServlet extends BaseServlet {
	
	private static String UTF8 = "UTF-8";

	/**
	 * 用户微信与平台间的验证，需要在微信后台配置
	 * 
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		logger.debug("proxy start"); 
		response.setCharacterEncoding(UTF8);
		request.setCharacterEncoding(UTF8);
		//获取微信管理员ID
		String weixinId = request.getParameter("t");
		
		if (StringUtil.isBlank(weixinId) || !StringUtil.isInteger(weixinId)) {
			response.getWriter().print("errc params");
			return;
		}
		
		//根据管理员ID查询该微信信息
		IWeixinBiz weixinBiz = (IWeixinBiz) getBean(request.getServletContext(), "weixinBiz");
		WeixinEntity wx = weixinBiz.getEntityById(Integer.parseInt(weixinId));
		logger.debug("proxy url" +wx.getWeixinProxyUrl());
		if (!StringUtil.isBlank(wx.getWeixinProxyUrl())) {
			Result rs = Proxy.get(wx.getWeixinProxyUrl(), null,this.assemblyRequestMap(request));
			this.outString(response, rs.getContent(UTF8));
		} else {
			this.outJson(response, "no data params");
		}
	}

	/**
	 * 接受微信请求，详细参考微信开发接口http://mp.weixin.qq.com/wiki/home/index.html
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding(com.mingsoft.base.constant.Const.UTF8);
		response.setCharacterEncoding(com.mingsoft.base.constant.Const.UTF8);

		
		String weixinId = request.getParameter("t");
		
		if (StringUtil.isBlank(weixinId) || !StringUtil.isInteger(weixinId)) {
			response.getWriter().print("err params");
			return;
		}
		
		//根据管理员ID查询该微信信息
		IWeixinBiz weixinBiz = (IWeixinBiz) getBean(request.getServletContext(), "weixinBiz");
		WeixinEntity wx  = weixinBiz.getEntityById(Integer.parseInt(weixinId));
		if (!StringUtil.isBlank(wx.getWeixinProxyUrl())) {
			Map<String,String> param = new HashMap<String,String>();
			param.put("t",weixinId);
			param.put("requestStr", this.readStreamParameter(request));
			Result rs = Proxy.post(wx.getWeixinProxyUrl(),null,param,UTF8);
			this.outString(response, rs.getContent(UTF8));
		} else {
			this.outJson(response, "no data params");
		}
	}

}
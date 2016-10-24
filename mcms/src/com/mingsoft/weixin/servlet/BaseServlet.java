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

import java.net.InetAddress;
import java.net.UnknownHostException;
import javax.servlet.http.HttpServletRequest;
import com.mingsoft.util.StringUtil;
import com.mingsoft.weixin.constant.SessionConst;
import com.mingsoft.weixin.entity.WeixinEntity;

/**
 * 铭飞科技
 * Copyright: Copyright (c) 2014 - 2015
 * @author killfen
 * Comments:微信层serlvet基础层
 * Create Date:2015-3-28
 * Modification history:
 */
public class BaseServlet extends com.mingsoft.people.servlet.BaseServlet{

	/**
	 * 设置session
	 * @param request HttpServletRequest 对象
	 * @param key 键SessionConst里面定义
	 * @param obj 对象
	 */
	protected void setWeixinSession(HttpServletRequest request,SessionConst key,Object obj) {
		if (StringUtil.isBlank(obj.toString())) {
			return;
		}
		request.getSession().setAttribute(key.toString(), obj);
	}

	/**
	 * 获取session
	 * @param request HttpServletRequest 对象
	 * @param key 键SessionConst里面定义
	 */
	protected Object getWeixinSession(HttpServletRequest request,SessionConst key) {
		return request.getSession().getAttribute(key.toString());
	}
	
	/**
	 * 获取微信Id
	 * @param request
	 * @return 微信Id
	 */
	protected int getWeixinId(HttpServletRequest request){
		WeixinEntity weixin = (WeixinEntity) this.getWeixinSession(request,SessionConst.WEIXIN_SESSION);
		return weixin.getWeixinId();
	}
	
	/**
	 * 读取服务器主机ip信息
	 * @return 返回主机IP，异常将会获取不到ip
	 */
	protected String getHostIp() {
		InetAddress addr;
		try {
			addr = InetAddress.getLocalHost();
			return addr.getHostAddress().toString();// 获得本机IP
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}	
	
}
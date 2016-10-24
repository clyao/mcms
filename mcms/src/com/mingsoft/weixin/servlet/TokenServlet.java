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
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mingsoft.weixin.constant.Const;
import com.mingsoft.weixin.constant.SessionConst;
import com.mingsoft.util.StringUtil;
import com.mingsoft.util.proxy.SHA1;
import com.mingsoft.weixin.biz.IWeixinBiz;
import com.mingsoft.weixin.entity.WeixinEntity;
import com.mingsoft.weixin.event.WeixinEventChain;

/**
 * 提供给微信高级开发模式使用的接口，get方法用于验证token，post主要用于验证用户触发的事件。
 * @author killfen
 * @version 
 * 版本号：100-000-000<br/>
 * 创建日期：2015年8月26日 下午7:20:14<br/>
 * 历史修订：<br/>
 */
@WebServlet(urlPatterns="/weixin/token")
public class TokenServlet extends BaseServlet {
	
	private static String UTF8 = "UTF-8";

	/**
	 * 用户微信与平台间的验证，需要在微信后台配置
	 * 
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding(UTF8);
		request.setCharacterEncoding(UTF8);
		//获取微信自增长Id
		String weixinId = request.getParameter("t");
		
		logger.debug("weixinId:" + weixinId); 
		
		if (StringUtil.isBlank(weixinId) || !StringUtil.isInteger(weixinId)) {
			response.getWriter().print("err params");
			return;
		}
		
		//根据管理员ID查询该微信信息
		IWeixinBiz weixinBiz = (IWeixinBiz) getBean(request.getServletContext(), "weixinBiz");
		//根据微信自增长Id查询微信基础信息
		WeixinEntity wx = weixinBiz.getEntityById(Integer.parseInt(weixinId));
		
		logger.debug(wx.getWeixinName());
		if (wx == null) {
			response.getWriter().print("no data params");
			return;
		}
		//将当前微信实体压入session
		this.setWeixinSession(request,SessionConst.WEIXIN_SESSION,wx);		
		
		// 微信加密签名
		String signature = request.getParameter("signature");
		// 随机字符串
		String echostr = request.getParameter("echostr");
		// 时间戳
		String timestamp = request.getParameter("timestamp");
		// 随机数
		String nonce = request.getParameter("nonce");

		String[] str = { wx.getWeixinToken(), timestamp, nonce };
		Arrays.sort(str); // 字典序排序
		String bigStr = str[0] + str[1] + str[2];
		// SHA1加密
		String digest = new SHA1().getDigestOfString(bigStr.getBytes()).toLowerCase();
		// 确认请求来至微信
		if (digest.equals(signature)) {
			PrintWriter out  = response.getWriter();
			out.print(echostr);
			out.flush();
			out.close();
		}
	}

	/**
	 * 接受微信请求，详细参考微信开发接口http://mp.weixin.qq.com/wiki/home/index.html
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding(com.mingsoft.base.constant.Const.UTF8);
		response.setCharacterEncoding(com.mingsoft.base.constant.Const.UTF8);
		PrintWriter out = response.getWriter();

		//获取微信自增长Id
		String weixinId = request.getParameter("t");
		if (StringUtil.isBlank(weixinId) || !StringUtil.isInteger(weixinId)) {
			response.getWriter().print("err params");
			return;
		}	
		//查询微信应用ID
		IWeixinBiz weixinBiz = (IWeixinBiz) getBean(request.getServletContext(), "weixinBiz");
		WeixinEntity weixin = weixinBiz.getEntityById(Integer.parseInt(weixinId));
		if(weixin == null || weixin.getAppId() == 0){
			response.getWriter().print("err params mms appId");
			return;
		}
		//将微信实体唯压入session
		this.setWeixinSession(request,SessionConst.WEIXIN_SESSION,weixin);
		
		String requestStr = this.readStreamParameter(request);
		//当获取的参数为null时判断是否为接口转发
		if(StringUtil.isBlank(requestStr)){
			requestStr = request.getParameter("requestStr");
		}
		
		logger.debug("微信回调数据"+requestStr);
		Map<String,Object> params = new HashMap<String,Object> ();
		params.put("originalWeixinMsg", requestStr);;
		params.put("appId",weixin.getAppId());
		params.put("weixinId",Integer.parseInt(weixinId));
		params.put("hostUrl", getUrl(request));
		params.put("savePath", request.getSession().getServletContext().getRealPath(Const.CHAT_MEDIA_PATH));
		
		WeixinEventChain weixinEventChain = (WeixinEventChain) getBean(request.getServletContext(),"weixinEventChain");
		Map<String, Object> returnMap = weixinEventChain.execute(params);
		if (returnMap != null && returnMap.get("type") != null && (Boolean) returnMap.get("type")) { 
			logger.debug("返回的xml数据:"+returnMap.get("content"));
			if (returnMap.get("content") instanceof List) {
				List list = (List)returnMap.get("content");
				for (int i=0;i<list.size();i++) {
					out.print(list.get(i));
				}
			} else {
				out.print(returnMap.get("content"));
			}
		}
		out.flush();
		out.close();
	}

}
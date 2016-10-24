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

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mingsoft.weixin.constant.Const;
import com.mingsoft.basic.servlet.BaseServlet;
import com.mingsoft.util.StringUtil;
/**
 * mswx-铭飞微信酒店预订平台
 * Copyright: Copyright (c) 2013 - 2015
 * Company:景德镇铭飞科技有限公司
 * @author 荣繁奎
 * @version 100-000-000
 * 版权所有
 * Comments:预定成功后 发送给用户的包含订单号的二维码 用来显示微信二维码的大图标页面
 * Create Date:2014-3-14
 * Modification history:暂无
 */
public class QrcodeForOrderSuccessServlet extends BaseServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String picture = req.getParameter("picture");
		String openId = req.getParameter("openid");
		logger.debug("openid:"+openId);
		if(!StringUtil.isBlank(picture)){
			//图片地址绝对路径
			String absolutyPath = com.mingsoft.base.constant.Const.PROJECT_PATH + Const.ORDER_PATH;
			//物理路径
			String path = com.mingsoft.base.constant.Const.HOST_URL + Const.ORDER_PATH;
			File file = new File(absolutyPath+picture);
			if(file.exists()){
				req.setAttribute("pictureUrl", path + picture );
				req.getRequestDispatcher("/hotel/qrcode/qrcode_orderok_show.ftl").forward(req, resp);
			}else{
				this.sendHtml(req, resp,getResString("qrcode.no.error"));
			}
		}else{
			this.sendHtml(req, resp,getResString("qrcode.error"));
		}
	}
}
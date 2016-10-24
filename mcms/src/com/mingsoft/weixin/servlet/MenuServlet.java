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
import com.mingsoft.basic.servlet.BaseServlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Deprecated
public class MenuServlet extends BaseServlet {
	/*
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
//			String accessToken = getAccessToken("wx7e5c5824684a7005",
//					"eda48c039dc80c65c18e63eb291939e6");
			String s = "{\"button\":[{\"name\":\"预 dd订\",\"key\":\"V1001_TODAY_MUSIC\",\"type\":\"click\"}]}";
//			String res = MenuUtils.createMenu(s, accessToken);
			MenuUtils menu = new MenuUtils("wx7e5c5824684a7005","eda48c039dc80c65c18e63eb291939e6");
			menu.createMenu(s);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
*/
}
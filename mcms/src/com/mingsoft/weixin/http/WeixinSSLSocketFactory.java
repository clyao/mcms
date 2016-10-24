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

package com.mingsoft.weixin.http;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import org.apache.http.conn.ssl.SSLSocketFactory;

/**
 * Copyright: Copyright (c) 2014 - 2015
 * Company:景德镇铭飞科技有限公司
 * @author wangtp
 * @version 300-001-001
 * 版权所有 铭飞科技
 * Comments: SSL通用类,单例模式的类，外包不需要修改此类,只提供一个getInstance方法
 * Create Date:2013-12-23
 *  Modification history:
 */
public final class WeixinSSLSocketFactory extends SSLSocketFactory {

	static {
		mySSLSocketFactory = new WeixinSSLSocketFactory(createSContext());
	}

	private static WeixinSSLSocketFactory mySSLSocketFactory = null;

	private WeixinSSLSocketFactory(SSLContext sslContext) {
		super(sslContext);
		this.setHostnameVerifier(ALLOW_ALL_HOSTNAME_VERIFIER);
	}

	/**
	 * 活动ssl公测对象
	 * @return WeixinSSLSocketFactory
	 */
	public static WeixinSSLSocketFactory getInstance() {
		if (mySSLSocketFactory != null) {
			return mySSLSocketFactory;
		} else {
			return mySSLSocketFactory = new WeixinSSLSocketFactory(
					createSContext());
		}
	}

	private static SSLContext createSContext() {
		SSLContext sslcontext = null;
		try {
			sslcontext = SSLContext.getInstance("SSL");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		try {
			sslcontext.init(null,
					new TrustManager[] { new TrustAnyTrustManager() }, null);
		} catch (KeyManagementException e) {
			e.printStackTrace();
			return null;
		}
		return sslcontext;
	}

}
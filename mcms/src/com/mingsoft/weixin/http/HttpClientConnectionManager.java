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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.impl.client.DefaultHttpClient;

/**
 * Copyright: Copyright (c) 2014 - 2015
 * Company:景德镇铭飞科技有限公司
 * @author wangtp
 * @version 300-001-001
 * 版权所有 铭飞科技
 * Comments:
 * 微信客户端请求通用方法,对应的com.mingsoft.weixin所有的类有绑定过，一般不需要再新建一个httpclient
 * 应用平台只需要调用想用的utils工具类
 * Create Date:2013-12-23
 * Modification history:
 */
public class HttpClientConnectionManager {

	/**
	 * 网页字符集编码UTF-8
	 */
	public final static String UTF8 = "UTF-8";
	/**
	 * 网页字符集编码GB2312
	 */
	public final static String GB2312 = "GB2312";
	
	
	public final static String POST = "POST";
	
	public final static String GET = "GET";

	/**
	 * 获取SSL验证的HttpClient
	 * 
	 * @param httpClient
	 * @return
	 */
	public static HttpClient getSSLInstance(HttpClient httpClient) {
		ClientConnectionManager ccm = httpClient.getConnectionManager();
		SchemeRegistry sr = ccm.getSchemeRegistry();
		sr.register(new Scheme("https", 443, WeixinSSLSocketFactory.getInstance()));
		httpClient = new DefaultHttpClient(ccm, httpClient.getParams());
		return httpClient;
	}

	/**
	 * 发起请求
	 * @param postUrl 请求地址
	 * @param param 参数
	 * @param method 请求方法
	 * @return null出现异常
	 */
	public static String request(String postUrl, String param, String method) {
		URL url;
		try {
			url = new URL(postUrl);

			HttpURLConnection conn;
			conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(30000); // 设置连接主机超时（单位：毫秒)
			conn.setReadTimeout(30000); // 设置从主机读取数据超时（单位：毫秒)
			conn.setDoOutput(true); // post请求参数要放在http正文内，顾设置成true，默认是false
			conn.setDoInput(true); // 设置是否从httpUrlConnection读入，默认情况下是true
			conn.setUseCaches(false); // Post 请求不能使用缓存
			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			conn.setRequestMethod(method);// 设定请求的方法为"POST"，默认是GET
			conn.setRequestProperty("Content-Length", param.length() + "");
			String encode = "utf-8";
			OutputStreamWriter out = null;
			out = new OutputStreamWriter(conn.getOutputStream(), encode);
			out.write(param);
			out.flush();

			if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
				return null;
			}
			// 获取响应内容体
			BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
			String line = "";
			StringBuffer strBuf = new StringBuffer();
			while ((line = in.readLine()) != null) {
				strBuf.append(line).append("\n");
			}
			in.close();
			out.close();
			return strBuf.toString();
		} catch (MalformedURLException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 模拟浏览器post提交
	 * 
	 * @param url
	 * @return
	 */
	public static HttpPost getPostMethod(String url) {

		URI uri = null;
		try {
			URL _url = new URL(url);
			uri = new URI(_url.getProtocol(), _url.getHost(), _url.getPath(), _url.getQuery(), null);

		} catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		HttpPost pmethod = new HttpPost(uri); // 设置响应头信息
		pmethod.addHeader("Connection", "keep-alive");
		pmethod.addHeader("Accept", "*/*");
		pmethod.addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
		pmethod.addHeader("Host", "mp.weixin.qq.com");
		pmethod.addHeader("X-Requested-With", "XMLHttpRequest");
		pmethod.addHeader("Cache-Control", "max-age=0");
		pmethod.addHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.0) ");
		return pmethod;
	}

	/**
	 * 模拟浏览器GET提交
	 * 
	 * @param url
	 * @return
	 */
	public static HttpGet getGetMethod(String url) {
		URI uri = null;
		try {
			URL _url = new URL(url);
			uri = new URI(_url.getProtocol(), _url.getHost(), _url.getPath(), _url.getQuery(), null);

		} catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		HttpGet pmethod = new HttpGet(uri);
		// 设置响应头信息
		pmethod.addHeader("Connection", "keep-alive");
		pmethod.addHeader("Cache-Control", "max-age=0");
		pmethod.addHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.0) ");
		pmethod.addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/;q=0.8");
		return pmethod;
	}
}
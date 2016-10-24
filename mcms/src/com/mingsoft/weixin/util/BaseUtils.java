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

package com.mingsoft.weixin.util;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.mingsoft.util.JsonUtil;
import com.mingsoft.util.StringUtil;
import com.mingsoft.weixin.http.HttpClientConnectionManager;

/**
 * 微信平台接口基础数据，如果请求的地址、和错误常量，主要是由微信官方约定</br>
 * 详细参考：http://mp.weixin.qq.com/wiki/index.php?title=%E9%A6%96%E9%A1%B5</br>
 * 同时也将httpClient的对象绑定，后续微信其他的接口就需要基础这个类。</br>
 * @author wangtp
 * @version 
 * 版本号：100-000-000<br/>
 * 创建日期：2015年9月21日 上午10:13:58<br/>
 * 历史修订：<br/>
 */
public abstract class BaseUtils {
	
	public Logger logger = Logger.getLogger(this.getClass());
	
	/**
	 * 微信平台的appid,在开发模式下可以获得(对应微信平台中的应用ID)
	 */
	protected String appid;
	
	/**
	 * 微信平台的secret,在开发模式下可以获得
	 */
	protected String secret;
	
	/**
	 * httpclient封装，与com.mingsoft.util.proxy下面的不一样，这个可以请求带https验证的地址
	 */
	protected DefaultHttpClient HTTPCLIENT = new DefaultHttpClient();;
	
	/**
	 * 请求微信接口必须的字段，每隔一段时间或失效
	 */
	private String accessToken = null; 
		
	/**
	 * 微信错误消息字符串
	 */
	protected final static String WEIXIN_JSON_ERR_STR = "errmsg";
	
	/**
	 * 认证token返回的json字符串
	 */
	protected final static String ACCESS_TOEN_OK_STR = "access_token";
	
	/**
	 * 认证token返回的json字符串错误字符串
	 */
	protected final static String ACCESS_TOEN_ERR_STR = "invalid appid";
	
	/**
	 * 获取认证token地址
	 */
	private final static String ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&";
	
	/**
	 * 客服信息推送
	 */
	protected final static String SERVICE_MSG = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=";
	
	/**
	 * 创建二维码ticket ,凭借ticket到指定URL换取二维码
	 */
	protected final static String CREATE_TICKET = "https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=";   
	
	/**
	 * 通过ticket换取二维码
	 */
	protected final static String GET_QRCODE = "https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=";
	
	/**
	 * 拉取微信关注者openid列表
	 */
	protected final static String GET_USER_OPENID_LIST = "https://api.weixin.qq.com/cgi-bin/user/get?access_token=";
	
	
	/**
	 * 通过openid 获取用户的基本资料
	 */
	protected final static String GET_USERDATA_BYOPENID ="https://api.weixin.qq.com/cgi-bin/user/info?access_token=";
	
	/**
	 * 通过code换取网页授权access_token;使用时应携带参数：
	 *		appid:必须携带, 公众号的唯一标识
	 *		secret:必须携带,公众号的appsecret
	 *		code:必须携带,填写第一步获取的code参数
	 *		grant_type:必须携带,填写为authorization_code
	 *如:https://api.weixin.qq.com/sns/oauth2/access_token?appid=wxdf90b2c4ece8de81&secret=5888a47b886535146ffa8ab6f1bed24b&code="获取的用户code"&grant_type=authorization_code
	 */
	protected final static String AUTHORIZATION_ACCESS_TOKEN_URL="https://api.weixin.qq.com/sns/oauth2/access_token";
	
	/**
	 * 由于access_token拥有较短的有效期,当access_token超时后，可以使用refresh_token进行刷新
	 * refresh_token拥有较长的有效期（7天、30天、60天、90天），当refresh_token失效的后，需要用户重新授权。
	 * 链接后接参数:
	 *		appid:必须参数, 公众号的唯一标识
	 *		grant_type:必须参数, 填写为refresh_token
	 *		refresh_token:必须参数,填写通过access_token获取到的refresh_token参数
	 *如:https://api.weixin.qq.com/sns/oauth2/refresh_token?appid=APPID&grant_type=refresh_token&refresh_token=REFRESH_TOKEN
	 */
	protected final static String AUTHORIZATION_REFRESH_TOKEN_URL="https://api.weixin.qq.com/sns/oauth2/refresh_token";
	
	/**
	 * 拉取用户信息
	 * 如果网页授权作用域为snsapi_userinfo，则此时开发者可以通过access_token和openid拉取用户信息了。
	 * 连接后接参数:
	 *		access_token:必须参数,网页授权接口调用凭证,注意：此access_token与基础支持的access_token不同
	 *		openid:必须参数,用户的唯一标识
	 *		lang:必须参数,返回国家地区语言版本，zh_CN 简体，zh_TW 繁体，en 英语
	 *如:https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN
	 */
	protected final static String AUTHORIZATION_SCOPE_URL="https://api.weixin.qq.com/sns/userinfo";	
	
	/**
	 * 操作当前公众号分组请求地址
	 */
	protected final static String GROUP_URL="https://api.weixin.qq.com/cgi-bin/groups/";
	
	/**
	 * 调用微信接口必须要传递微信公众号的开发者凭据信息
	 * @param appid 具体见微信平台的开发模式描述
	 * @param secret 具体见微信平台的开发模式描述
	 */
	public BaseUtils(String appid,String secret) {
		this.appid = appid;
		this.secret = secret;
	}
	
	public String getAccessToken() {
		if (accessToken==null) {
			try {
				HTTPCLIENT = (DefaultHttpClient) HttpClientConnectionManager.getSSLInstance(HTTPCLIENT);
				HttpGet get = HttpClientConnectionManager.getGetMethod(ACCESS_TOKEN_URL+"appid="+ appid + "&secret=" + secret);
				HttpResponse response = HTTPCLIENT.execute(get);
				String jsonStr = EntityUtils.toString(response.getEntity(),HttpClientConnectionManager.UTF8);
				
				Map object = JsonUtil.getMap4Json(jsonStr);
				logger.debug("获取token:" + jsonStr);
				//传递过来的appid和secret不能通过验证,直接返回null
				if(object.get(this.WEIXIN_JSON_ERR_STR)!=null && object.get(this.WEIXIN_JSON_ERR_STR).equals(this.ACCESS_TOEN_ERR_STR)) {
					return null;
				}
				if(StringUtil.isBlank(object.get(ACCESS_TOEN_OK_STR).toString())){
					return null;
				}
				accessToken = object.get(ACCESS_TOEN_OK_STR).toString();
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return accessToken;
	}
	
	
	/**
	 * 向指定的地址获取返回的JSON
	 * @param pathUrl 地址
	 * @return 解析后的JSON字符串,获取其中某个参数的方法为:
	 * 					jsonsStr.getString("参数在JSON字符流中的名称")
	 */
	public Map<String,Object> jsonStrAuthor(String pathUrl) {
		HttpGet get = HttpClientConnectionManager.getGetMethod(pathUrl);
		DefaultHttpClient HTTPCLIENT = new DefaultHttpClient();
		try {
			HttpResponse responses = (HttpResponse) HTTPCLIENT.execute(get);
			String jsonStr = EntityUtils.toString(responses.getEntity(), "utf-8");
			logger.debug("回调JSON:" + jsonStr);
			return JsonUtil.getMap4Json(jsonStr);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 获取当前文件的物理路径
	 * @param request 
	 * @param filePath 文件所在服务器的相对路径
	 * @return
	 */
	public String getRealPath(HttpServletRequest request, String filePath) {
		return request.getServletContext().getRealPath(File.separator) + filePath;
	}
	
}
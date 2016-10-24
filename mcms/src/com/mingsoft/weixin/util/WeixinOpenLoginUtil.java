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

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.mingsoft.base.constant.Const;
import com.mingsoft.util.JsonUtil;
import com.mingsoft.util.StringUtil;
import com.mingsoft.util.proxy.Proxy;

/**
 * 
 * 微信开放平台登录工具类
 * @author 成卫雄(qq:330216230)
 * @version 
 * 版本号：100-000-000<br/>
 * 创建日期：2015年8月31日 上午11:15:10<br/>
 * 历史修订：<br/>
 */
public class WeixinOpenLoginUtil extends BaseUtils{

	
	/**
	 * 继承父类构造
	 * @param appid
	 * @param secret
	 */
	public WeixinOpenLoginUtil(String appid,String secret) {
		super(appid, secret);
	}
	
	/**
	 * 平转微信开放平台登录地址</br>
	 * 	若用户禁止授权，则重定向后不会带上code参数(无法获取用户信息)，仅会带上state参数</br>
	 *  详情请参考：https://open.weixin.qq.com/cgi-bin/showdocument?action=dir_list&t=resource/res_list&verify=1&lang=zh_CN</br>
	 * @param appId 开放平台应用的的唯一标识
	 * @param redirectUri 登录成功之后的重定向地址
	 * @param state 标记参数，如果没有直接传入Null
	 * @return 微信开放平台登录地址
	 */
	public static String getLoginUrl(String appId,String redirectUri,String state){
		String url = "https://open.weixin.qq.com/connect/qrconnect?appid="+appId;
		if(StringUtil.isBlank(redirectUri)){
			return null;
		}
		
		//重定向地址，需要进行UrlEncode
		redirectUri = StringUtil.encodeStringByUTF8(redirectUri);
		url = url +"&redirect_uri="+ redirectUri +"&response_type=code&scope=snsapi_login";
		//判断是否纯在回调状态值
		if(!StringUtil.isBlank(state)){
			url = url + "&state=" + state;
		}
		return url;
	}
	
	/**
	 * 获取用户信息
	 * @param code 获取用户信息的必须参数，在重定单地址中可获得
	 * @return json数据：
	 * 			{ 
	 *				"openid":"OPENID",
	 *				"nickname":"NICKNAME",
	 *				"sex":1,
	 *				"province":"PROVINCE",
	 *				"city":"CITY",
	 *				"country":"COUNTRY",
	 *				"headimgurl": "http://wx.qlogo.cn/mmopen/g3MonUZtNHkdmzicIlibx6iaFqAc56vxLSUfpb6n5WKSYVY0ChQKkiaJSgQ1dZuTOgvLLrhJbERQQ4eMsv84eavHiaiceqxibJxCfHe/0",
	 *				"privilege":[
	 *				"PRIVILEGE1", 
	 *				"PRIVILEGE2"
	 *				],
	 *				"unionid": "o6_bmasdasdsad6_2sgVt7hMZOPfL"
	 *				}
	 */
	public String getUserInfo(String code){
		//获取code
		if(StringUtil.isBlank(code)){
			logger.debug("未获取到code,无法获取用户信息");
			return null;
		}
		//通过code获取access_token和openid
		String accessTokenJson = this.getAccessToken(code);
		if(StringUtil.isBlank(accessTokenJson)){
			logger.debug("access_token为null");
			return null;
		}
		//将获取到的access_token和openid转化为Map
		Map<String,Object> mapAccessToken = JsonUtil.getMap4Json(accessTokenJson);
		if(StringUtil.isBlank(mapAccessToken.get("access_token")) || StringUtil.isBlank(mapAccessToken.get("openid"))){
			logger.debug("未获取到access_token；错误："+accessTokenJson);
			return accessTokenJson;
		}
		
		//组装请求用户信息必须参数
		Map<String,String> params = new HashMap<String,String>();
		params.put("access_token", mapAccessToken.get("access_token").toString());
		params.put("openid", mapAccessToken.get("openid").toString());
		//请求用户信息的地址,获取用户详细信息
		String url = "https://api.weixin.qq.com/sns/userinfo";
		//发起get请求
		String userInfoJson = Proxy.get(url, null, params).getContent(Const.UTF8);
		return userInfoJson;
	}
	
	/**
	 * 通过code获取access_token
	 * @param code 用户同意授权之后换取access_token的参数
	 * @return json 数据说明</br>
	 * 		示例：{	"access_token":"ACCESS_TOKEN", 
	 *				"expires_in":7200, 
	 *				"refresh_token":"REFRESH_TOKEN",
	 *				"openid":"OPENID", 
	 *				"scope":"SCOPE",
	 *				"unionid": "o6_bmasdasdsad6_2sgVt7hMZOPfL"
	 *			}</br>	
	 *		返回JSON参数说明：</br>
	 *				access_token:接口调用凭证</br>
	 *				expires_in:access_token接口调用凭证超时时间，单位（秒）</br>
	 *				refresh_token:刷新用户access_token有效期为30天</br>
	 *				openid:授权用户唯一标识</br>
	 *				scope:用户授权的作用域，使用逗号（,）分隔，如登录的作用域为snsapi_login</br>
	 * 				unionid：将多个公众号绑定到同一个微信开放平台（open.weixin.qq.com）帐号下，即同一个Union下。</br>
	 * 							参考：http://www.cnblogs.com/txw1958/p/weixin98-get-user-UnionID.html</br>
	 * 		错误返回值：</br>
	 * 			{"errcode":40029,"errmsg":"invalid code"}</br>
	 */
	private String getAccessToken(String code){
		//code参数不能为空
		if(StringUtil.isBlank(code)){
			logger.debug("code为空！不能发送请求获取access_token");
			return null;
		}
		//初始化请求参数
		Map<String,String> params = new HashMap<String,String>();
		params.put("appid",this.appid);
		params.put("secret",this.secret);
		params.put("code", code);
		params.put("grant_type","authorization_code");
		//获取accessToken的请求地址
		String url = "https://api.weixin.qq.com/sns/oauth2/access_token";
		//发送get请求,从微信开放平台获取accessaToken
		String accessTokenJson = Proxy.get(url, null, params).getContent(Const.UTF8);
		logger.debug("请求到的access_token:"+accessTokenJson);
		return accessTokenJson;
	}
	
	/**
	 * 刷新access_token延长有效期</br>
	 * refresh_token拥有较长的有效期（30天），当refresh_token失效的后，需要用户重新授权。
	 * @param refreshToken 用户刷新access_token标识参数
	 * @return 微信返回值说明</br>
	 * 			1. 若access_token已超时，那么进行refresh_token会获取一个新的access_token，新的超时时间；</br>
	 *			2. 若access_token未超时，那么进行refresh_token不会改变access_token，但超时时间会刷新，相当于续期access_token。</br>
	 *			详情参考：http://www.cnblogs.com/txw1958/p/weixin98-get-user-UnionID.html</br>
	 *			{ 
	 *			"access_token":"ACCESS_TOKEN", 
	 *			"expires_in":7200, 
	 *			"refresh_token":"REFRESH_TOKEN", 
	 *			"openid":"OPENID", 
	 *			"scope":"SCOPE" 
	 *			}
	 * 
	 */
	public String refreshAccessToken(String refreshToken){
		//refreshToken不能为空
		if(StringUtil.isBlank(refreshToken)){
			logger.debug("refreshToken为空！不能发送请求获取");
			return null;
		}
		//组装请求参数
		Map<String,String> params = new HashMap<String,String>();
		params.put("appid",this.appid);
		params.put("grant_type","refresh_token");
		params.put("refresh_token",refreshToken);
		//刷新access_token的请求地址
		String url = "https://api.weixin.qq.com/sns/oauth2/refresh_token";
		//发送get请求，刷新access_token
		String json = Proxy.get(url, null, params).getContent(Const.UTF8);
		logger.debug("刷新access_token的结果:"+json);
		return json;
	}
	
	
	/**
	 * 检验授权凭证（access_token）是否有效
	 * @param accessToken 调用接口凭证
	 * @param openid 普通用户标识，对该公众帐号唯一
	 * @return true 授权有效</br>
	 *  微信返回值说明：</br>
	 * 			{ 
	 *			"errcode":0,"errmsg":"ok"
	 *			}</br>
	 *			参考地址：https://open.weixin.qq.com/cgi-bin/showdocument?action=dir_list&t=resource/res_list&verify=1&lang=zh_CN&token=9636a40d21b1a1b13ad104a279517ddb919a99b7</br>
	 */
	public Boolean verifyAccessToke(String accessToken,String openid){
		//验证请求参数不能为空
		if(StringUtil.isBlank(accessToken) || StringUtil.isBlank(openid)){
			logger.debug("-------请求参数不能为空");
			return null;
		}
		Map<String,String> params = new HashMap<String,String>();
		params.put("access_token",accessToken);
		params.put("openid", openid);
		//验证access_token的请求地址
		String url = "https://api.weixin.qq.com/sns/auth";
		//发送get请求，验证openid和access_token
		String returnJson = Proxy.get(url, null, params).getContent(Const.UTF8);
		logger.debug("=====验证结果:"+returnJson);
		Map<String,Object> object = JsonUtil.getMap4Json(returnJson);
		if(Integer.parseInt(object.get("errcode").toString()) == 0){
			return true;
		}else{
			return false;
		}
	}
	
	
}
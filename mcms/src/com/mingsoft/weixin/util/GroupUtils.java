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

import java.io.IOException;
import java.util.Map;


import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSON;
import com.mingsoft.util.JsonUtil;
import com.mingsoft.weixin.http.HttpClientConnectionManager;

/**
 * 
 * 微信分组通用方法
 * @author 成卫雄(qq:330216230)
 * @version 
 * 版本号：100-000-000<br/>
 * 创建日期：2014-4-1<br/>
 * 历史修订：<br/>
 */
public class GroupUtils extends BaseUtils {

	/**
	 * 查询当前公众号的所有微信分组请求地址</br>
	 * access_token:当前公众号的调用接口凭证，需要动态获取。
	 */
	private final static String GROUP_QUERY_GET = "get?access_token=";
	
	/**
	 * 请求POST方法获取用户在公众号中的分组Id
	 * access_token:当前公众号的调用接口凭证，需要动态获取。
	 */
	private final static String GROUP_QUERY_USER_GETID="getid?access_token=";
	
	/**
	 * 请求POST方法创建微信用户分组
	 * access_token:当前公众号的调用接口凭证，需要动态获取。
	 */
	private final static String GROUP_SAVE_CREATE="create?access_token=";
	
	/**
	 * 请求POST方法修改微信用户分组
	 * access_token:当前公众号的调用接口凭证，需要动态获取。
	 */
	private final static String GROUP_UPDATE="update?access_token=";
	
	/**
	 * 请求POST方法将用户移动到指定分组
	 * access_token:当前公众号的调用接口凭证，需要动态获取。
	 */
	private final static String GROUP_UPDATE_MOBILE="groups/members/update?access_token=";
	
	/**
	 * 修改分组时回调JSON数据中的成功参数
	 */
	private final static String GROUP_ERRMSG="errmsg";

	/**
	 * 当前公众号中用户所在的分组Id
	 */
	private final static String GROUP_ID="groupid";
	
	/**
	 * 修改分组时回调JSON数据中的成功参数
	 */	
	private final static String GROUP_ERRMSG_OK="ok";
	
	/**
	 * 调用微信接口必须要传递微信公众号的开发者凭据信息
	 * @param appid 具体见微信平台的开发模式描述
	 * @param secret 具体见微信平台的开发模式描述
	 */
	public GroupUtils(String appid, String secret) {
		super(appid, secret);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * 查询当前公众号分组信息
	 * @return {"groups": [{ </br>
	 * 				"id":分组id，由微信分配, </br>
	 * 				"name": "分组名字，UTF8编码", </br>
	 * 				"count": 分组内用户数量}]}, 
	 */
	public String queryGrouping(){
		//获取公众号的accessToken拼装请求地址
		String pathUrl = GROUP_URL+GROUP_QUERY_GET +getAccessToken();
		HttpGet get = HttpClientConnectionManager.getGetMethod(pathUrl);
		DefaultHttpClient HTTPCLIENT = new DefaultHttpClient();
		try {
			HttpResponse responses = (HttpResponse) HTTPCLIENT.execute(get);
			String jsonStr = EntityUtils.toString(responses.getEntity(), "utf-8");
			logger.debug("回调JSON:" + jsonStr);
			return jsonStr;
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";		
	} 
	
	/**
	 * 查询用户所在分组
	 * @param openId 用户相对于微信号的唯一标识
	 * @return 用户所在分组ID {"groupid": 用户所在分组id，由微信分配}
	 */
	@SuppressWarnings("unchecked")
	public String queryUserGroup(String openId){
		String url = GROUP_URL+GROUP_QUERY_USER_GETID+getAccessToken();
		String json = "{\"openid\":\""+openId+"\"}";
		Map object = JsonUtil.getMap4Json(getPost(json,url));
		return object.get(GROUP_ID).toString();
	}
	
	/**
	 * 创建用户分组
	 * @param groupName 分组名称最大长度:不超过30个字
	 * @return 成功：{ "group": { "id": 107,  "name": "test" }}
	 */
	public String saveGroup(String groupName){
		if(!(groupName.length() > 30)){
			String url = GROUP_URL+GROUP_SAVE_CREATE+getAccessToken();
			String json = "{\"group\":{\"name\":\""+groupName+"\"}}";
			return getPost(json,url);
		}else{
			return "";
		}
	}
	
	/**
	 * 修改当前公众号分组名称
	 * @param updateGroupName 公众号新名称(分组名称最大长度:不超过30个字)
	 * @param groupId 需要修改的分组ID
	 * @return true 成功:{"errcode": 0, "errmsg": "ok"}
	 */
	@SuppressWarnings("unchecked")
	public boolean updateGroup(String updateGroupName,int groupId){
		if(!(updateGroupName.length() > 30)){
			String url = GROUP_URL+GROUP_UPDATE+getAccessToken();
			String json = "{\"group\":{\"id\":"+groupId+",\"name\":\""+updateGroupName+"\"}}";
			Map object = JsonUtil.getMap4Json(getPost(json,url));
			String accessToken = object.get(GROUP_ERRMSG).toString();
			if(accessToken.equals(GROUP_ERRMSG_OK)){
					return true;
				}
			}
		return false;
	}
	
	/**
	 * 将用户移动到指定分组
	 * @param openId 用户对于当前公众号的惟一标识
	 * @param groupId 需要移动的分组ID
	 * @return true 成功:{"errcode": 0, "errmsg": "ok"}
	 */
	@SuppressWarnings("unchecked")
	public boolean mobileGroup(String openId,int groupId){
		String url = GROUP_URL+GROUP_UPDATE_MOBILE+getAccessToken();
		String json = "{\"openid\":\""+openId+"\",\"to_groupid\":"+groupId+"}";
		Map object = JsonUtil.getMap4Json(getPost(json,url));
		String accessToken = object.get(GROUP_ERRMSG).toString();
		if(accessToken.equals(GROUP_ERRMSG_OK)){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 发送带JSON数据的POST请求
	 * @param json 请求的JSON数据
	 * @param url 请求地址
	 * @return JSON字符串查询结果
	 */	
	private String getPost(String json,String url){
		HttpPost httpost = HttpClientConnectionManager.getPostMethod(url);
		try {
			httpost.setEntity(new StringEntity(json, "UTF-8"));
			HttpResponse response = HTTPCLIENT.execute(httpost);
			String jsonStr = EntityUtils.toString(response.getEntity(), "utf-8");
			String obj = JSON.toJSONString(jsonStr);
			
			logger.debug("POST请求返回数据:" + obj);
			return obj;
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		return "";
	}
}
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
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mingsoft.util.StringUtil;
import com.mingsoft.weixin.bean.NewsBean;
import com.mingsoft.weixin.http.HttpClientConnectionManager;

/**
 * 向用户发送自定义消息</br>
 * 客服消息接口工具类</br>
 * @author 成卫雄(qq:330216230)
 * @version 
 * 版本号：100-000-000<br/>
 * 创建日期：2014-10-14<br/>
 * 历史修订：<br/>
 */
public class MessageUtils extends BaseUtils{

	/**
	 * @param appid 微信APPID
	 * @param secret 微信secret
	 */
	public MessageUtils(String appid, String secret) {
		super(appid, secret);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 向用户发送文本消息
	 * @param openId 用户的openId
	 * @param content 文本内容
	 * @return 消息状态;ok:发送成功
	 */
	public Boolean sendTextToUser(String openId,String content){
		//构建发送消息的json格式
		String massageJson = XmlUtils.buildJsonText(openId, content);
		this.logger.debug("主动回复客服文本消息的json："+massageJson);
		String jsonStr = sendToUser(massageJson);
		this.logger.debug("发送文本消息的返回值:"+jsonStr);
		JSONObject object = JSON.parseObject(jsonStr);
		return object.getString(WEIXIN_JSON_ERR_STR).equals("ok");
	}
	
	/**
	 * 向用户发送图片消息
	 * @param openId 用户的openId
	 * @param mediaId 发送的图片的媒体ID
	 * @return 消息状态;ok:发送成功
	 */
	public String sendImageToUser(String openId,String mediaId){
		String massageJson = XmlUtils.buildJsonImage(openId, mediaId);
		this.logger.debug("主动回复客服图片消息的json:"+massageJson);
		String jsonStr = sendToUser(massageJson);
		this.logger.debug("发送图片消息的返回值:"+jsonStr);
		JSONObject object = JSON.parseObject(jsonStr);
		return object.getString(WEIXIN_JSON_ERR_STR);		
	}
	
	/**
	 * 向用户发送图文消息
	 * @param openId 用户的openId
	 * @param newsBeanList 图文消息集合
	 * @return 消息状态;ok:发送成功
	 */
	public Boolean sendNewsToUser(String openId,List<NewsBean> newsBeanList){
		String massageJson = XmlUtils.buildJsonNews(openId, newsBeanList);
		this.logger.debug("主动回复图文消息的json"+massageJson);
		String jsonStr = sendToUser(massageJson);
		this.logger.debug("主动回复图文消息的返回值："+jsonStr);
		JSONObject object = JSON.parseObject(jsonStr);
		return object.getString(WEIXIN_JSON_ERR_STR).equals("ok");	
	}
	
	/**
	 * 将消息发送给用户
	 * @param jsonMsg 发送消息的json
	 * @return 
	 */
	private String sendToUser(String jsonMsg) {
		String accessToken = getAccessToken();
		if(!StringUtil.isBlank(accessToken)){
			HttpPost httpost = HttpClientConnectionManager.getPostMethod(SERVICE_MSG	+ accessToken);
			try {
				httpost.setEntity(new StringEntity(jsonMsg, "UTF-8"));
				HttpResponse response  = HTTPCLIENT.execute(httpost);
				String _jsonStr  = EntityUtils.toString(response.getEntity(), "utf-8");
				logger.debug(_jsonStr);
				return _jsonStr;
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		}
		return "";
	}
	
}
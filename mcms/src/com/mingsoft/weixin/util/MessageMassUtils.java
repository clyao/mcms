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
import java.util.Map;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mingsoft.util.JsonUtil;
import com.mingsoft.util.StringUtil;
import com.mingsoft.weixin.http.HttpClientConnectionManager;

/** 
 * 群发消息工具类
 * @author  付琛  QQ:1658879747 
 * @version 1.0 
 * 创建时间：2015年11月19日 上午12:02:11  
 * 版本号：100-000-000<br/>
 * 历史修订<br/>
 */
public class MessageMassUtils extends BaseUtils{

	/**
	 * 图文消息预览
	 */
	private final static String MESSAGE_MASS_PREVIEW ="https://api.weixin.qq.com/cgi-bin/message/mass/preview?access_token=";
	
	/**
	 * 根据分组进行群发
	 */
	private final static String MESSAGE_MASS_SENDALL ="https://api.weixin.qq.com/cgi-bin/message/mass/sendall?access_token=";
	
	/**
	 * 查询用户发送状态
	 */
	private final static String MESSAGE_MASS_GET ="https://api.weixin.qq.com/cgi-bin/message/mass/get?access_token=";
		
	
	public MessageMassUtils(String appid, String secret) {
		super(appid, secret);
		// TODO Auto-generated constructor stub
	}
	

	/**
	 * 预览图文素材
	 * @param toUser 用户openId
	 * @param mediaId 上传图文返回的media_id
	 * @return {"errcode":0,"errmsg":"preview success","msg_id":34182}
	 * errorcode:错误码;errmsg：错误信息;msg_id：消息ID
	 */
	public Boolean preivewMessageMass(String openId,String mediaId){
		String accessToken = getAccessToken();
		if(!StringUtil.isBlank(accessToken)){
			HttpPost httpPost = HttpClientConnectionManager.getPostMethod(MESSAGE_MASS_PREVIEW + accessToken);
			try {
				String MessageMassPreviewJson = XmlUtils.PreviewJsonMessageMASS(openId, mediaId);
				logger.debug("接口调用的post请求参数============="+MessageMassPreviewJson);
				httpPost.setEntity(new StringEntity(MessageMassPreviewJson, "UTF-8"));
				HttpResponse response  = HTTPCLIENT.execute(httpPost);
				String jsonStr  = EntityUtils.toString(response.getEntity(),"utf-8");
				logger.debug(jsonStr);
				JSONObject object = JSON.parseObject(jsonStr);
				//返回错误信息
				return object.getString(WEIXIN_JSON_ERR_STR).equals("preview success");
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
		return null;
	}
	
	/**
	 * 分组群发图文消息
	 * @param mediaId 图文素材ID
	 * @param bool 是否群发
	 * @param gourpId 分组的gourpId 群发到的分组的group_id，参加用户管理中用户分组接口，若bool值为true，可不填写group_id
	 * @return {"errcode":0,"errmsg":"send job submission success","msg_id":34182, "msg_data_id": 206227730}
	 * errorcode:错误码;errmsg：错误信息;msg_id:消息发送任务的ID;msg_data_id：消息的数据ID，该字段只有在群发图文消息时，才会出现
	 */
	public Boolean sendAllMessageMass(String mediaId,Boolean bool,String gourpId){
		String accessToken = getAccessToken();
		if(!StringUtil.isBlank(accessToken)){
			HttpPost httpPost = HttpClientConnectionManager.getPostMethod(MESSAGE_MASS_SENDALL + accessToken);
			try {
				String MessageMassSendJson = XmlUtils.SendAllJsonMessageMass(mediaId, bool, gourpId);
				logger.debug("接口调用的post请求参数============="+MessageMassSendJson);
				httpPost.setEntity(new StringEntity(MessageMassSendJson, "UTF-8"));
				HttpResponse response  = HTTPCLIENT.execute(httpPost);
				String jsonStr  = EntityUtils.toString(response.getEntity(),"utf-8");
				logger.debug(jsonStr);
				JSONObject object = JSON.parseObject(jsonStr);	
				//返回错误信息
				return object.getString(WEIXIN_JSON_ERR_STR).equals("send job submission success");
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
		return null;
	}
	
	
	/**
	 * 查询群发状态
	 * @param msgId 群发后的返回值
	 * @return {"msg_id":201053012,"msg_status":"SEND_SUCCESS"} 
	 * msg_id:群发消息后返回的消息id;msg_status:消息发送后的状态，SEND_SUCCESS表示发送成功
	 */
	public Boolean getMessageMass(String msgId){
		String accessToken = getAccessToken();
		if(!StringUtil.isBlank(accessToken)){
			HttpPost httpPost = HttpClientConnectionManager.getPostMethod(MESSAGE_MASS_GET + accessToken);
			try {
				String MessageMassGetJson = XmlUtils.getJsonMessageMass(msgId);
				logger.debug("接口调用的post请求参数============="+MessageMassGetJson);
				httpPost.setEntity(new StringEntity(MessageMassGetJson, "UTF-8"));
				HttpResponse response  = HTTPCLIENT.execute(httpPost);
				String jsonStr  = EntityUtils.toString(response.getEntity(),"utf-8");
				logger.debug(jsonStr);
				Map<String, Object> messageMassGetdMap = JsonUtil.getMap4Json(jsonStr);				
				return messageMassGetdMap.get("msg_status").equals("SEND_SUCCESS");
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
		return null;
	}
}
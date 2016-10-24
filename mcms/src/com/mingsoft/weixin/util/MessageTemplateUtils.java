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
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSONObject;
import com.mingsoft.util.JsonUtil;
import com.mingsoft.util.StringUtil;
import com.mingsoft.weixin.bean.MessageTemplateBean;
import com.mingsoft.weixin.http.HttpClientConnectionManager;

/**
 * 微信模版消息工具类
 * @author killfen
 * @version 
 * 版本号：100-000-000<br/>
 * 创建日期：2015-4-1<br/>
 * 历史修订：<br/>
 */
public class MessageTemplateUtils extends BaseUtils{
	
	/**
	 * 获取模板ID
	 */
	public static final String GET_TEMPLETE_ID = "https://api.weixin.qq.com/cgi-bin/template/api_add_template?access_token=";
	
	/**
	 * 发送模板信息
	 */
	public final static String TEMPLATE_MSG = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=";
	
	/**
	 * @param appid
	 * @param secret
	 */
	public MessageTemplateUtils(String appid, String secret) {
		super(appid, secret);
	}
	
	
	/**
	 * 获取模板ID
	 * @param templateIdShort
	 * @return 模板ID
	 */
	public String getTempletId(String templateIdShort){
		String accessToken = getAccessToken();
		if(!StringUtil.isBlank(accessToken)){		
			HttpPost httpPost = HttpClientConnectionManager.getPostMethod(GET_TEMPLETE_ID + accessToken);
			try {
				String TempleteIdGetJson = XmlUtils.getJsonTempleteId(templateIdShort);
				logger.debug("接口调用的post请求参数============="+TempleteIdGetJson);
				httpPost.setEntity(new StringEntity(TempleteIdGetJson, "UTF-8"));
				HttpResponse response  = HTTPCLIENT.execute(httpPost);
				String jsonStr  = EntityUtils.toString(response.getEntity(),"utf-8");
				logger.debug(jsonStr);
				Map<String, Object> TempleteIdGetMap = JsonUtil.getMap4Json(jsonStr);				
				return (String) TempleteIdGetMap.get("template_id");
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
	
	
	/**
	 * 发送自定义模板消息
	 * @param openId 用户编号
	 * @param templateId 模板编号
	 * @param url 商品连接地址
	 * @param topcolor 颜色
	 * @param title 标题
	 * @param titleColor 标题颜色
	 * @param remark 备注
	 * @param remarkColor 备注颜色
	 * @param keyword 其他信息详细参考 http://mp.weixin.qq.com/wiki/17/304c1885ea66dbedf7dc170d84999a9d.html
	 * @return true发送成功
	 */
	public boolean sendToUser(String openId,String templateId,String url,String topcolor,String title,String titleColor,String remark,String remarkColor,String ... keyword) {
		String accessToken = getAccessToken();
		if(!StringUtil.isBlank(accessToken)){
			MessageTemplateBean mtb = new MessageTemplateBean();
			mtb.setTouser(openId);
			mtb.setTemplate_id(templateId);
			mtb.setUrl(url);
			mtb.setTopcolor(topcolor);
			Map<String,Label> map = new HashMap<String,Label>();
			if (!StringUtil.isBlank(title)) {
				Label label = new Label();
				label.setValue(title);
				label.setColor(titleColor);
				map.put("first", label);
			
			}
	
			for (int i =0;i<keyword.length;i++) {
				Label label = new Label();
				label.setValue(keyword[i]);		
				map.put("keyword"+(i+1), label);
			}
			if (!StringUtil.isBlank(remark)) {
				Label label = new Label();
				label.setValue(remark);
				label.setColor(remarkColor);
				map.put("remark", label);
			}		
			mtb.getData().putAll(map);
			
			
			HttpPost httpost = HttpClientConnectionManager.getPostMethod(TEMPLATE_MSG	+ accessToken);
			try {
				logger.debug(JSONObject.toJSONString(mtb));
				httpost.setEntity(new StringEntity(JSONObject.toJSONString(mtb), "UTF-8"));
				HttpResponse response  = HTTPCLIENT.execute(httpost);
				String _jsonStr  = EntityUtils.toString(response.getEntity(), "utf-8");
				logger.debug(_jsonStr);
				Map param = (Map)JSONObject.parse(_jsonStr);
				return param.get("errmsg").equals("ok");
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
		return false;
	}

}

class Label {
	private String value;
	
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	private String color = "#173177";

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}
}
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
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;


import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSONObject;
import com.mingsoft.util.StringUtil;
import com.mingsoft.weixin.http.HttpClientConnectionManager;

/**
 *	获取二维码，并生成二维码图片，保存路径:qrcode/酒店id/二维码编号.jpg<br/>
 *          参考weixin </br>
 *          api:http://mp.weixin.qq.com/wiki/index.php?title=%E7%94%9F%E6%88%90%E5</br>
 *          %B8%A6%E5%8F%82%E6%95%B0%E7%9A%84%E4%BA%8C%E7%BB%B4%E7%A0%81 </br>
 *          二维码获取流程：１、通过appid与secrct获取到ticket，2、使用ticket获取到二维码图片字节流 </br>
 * @author 成卫雄(qq:330216230)
 * @version 
 * 版本号：100-000-000<br/>
 * 创建日期：2015年8月26日 下午7:06:13<br/>
 * 历史修订：<br/>
 */
public class QrcodeUtils extends BaseUtils {
	
	/**
	 * 返回值中的MAP键值</br>
	 * 可判断二维码是否生成功</br>
	 * 保存 true 或 false</br>
	 */
	public static final String MAP_TYPE = "type";
	
	/**
	 * 返回值中的MAP键值</br>
	 * 保存二维码生成中的信息(主要是错误信息)</br>
	 */
	public static final String MAP_MSG = "msg";
	
	public QrcodeUtils(String appid, String secret) {
		super(appid, secret);
	}

	/**
	 * 生成永久二维码</br>
	 * 永久二维码时最大值为100000（目前参数只支持1--100000）</br>
	 * @param path 创建二维码的路径+生成二维码的文件名(如:f:\\qrcode\\2.jpg)</br>
	 * @param value 二维码的值（目前参数只支持1--100000整形）
	 * @return map类型<br/>
	 *         key:type value: true:获取成功 false:获取失败<br/>
	 *         key:msg value:返回信息
	 * @throws Exception
	 */
	public Map<String, Object> createLimitQrcode(String path,int value) {
		Map<String,Object> returnMap = new HashMap<String,Object>();
		//判断场景值是否错误
		if(value<0 || value>100000){
			returnMap.put(MAP_TYPE,false);
			returnMap.put(MAP_MSG,"value error");
			return returnMap;		
		}		
		String params = "{\"action_name\": \"QR_LIMIT_SCENE\", \"action_info\": {\"scene\": {\"scene_id\":" + value + "}}}";
		logger.debug("生永久时二维码的json数据："+params);
		returnMap = createQrcode(path,params);		
		return returnMap;
	}

	/**
	 * 生成临时二维码，根据过期时间,推荐在使用临时生成二维码的时候传入过期的时间</br>
	 * 场景值ID，临时二维码时为32位非0整型</br>
	 * @param path 二维码的保存路劲+生成二维码的文件名(如:f:\\qrcode\\2.jpg)
	 * @param value 二维码的场景值（注：32位非0整型）
	 * @param time 该二维码有效时间，以秒为单位。 最大不超过1800(30分钟)。
	 * @return map类型<br/>
	 *         key:type value: true:获取成功 false:获取失败<br/>
	 *         key:msg  value:返回信息
	 * @throws Exception
	 */
	public Map<String, Object> createTempQrcode(String path,int value,int time) {
		Map<String,Object> returnMap = new HashMap<String,Object>();
		//判断时间是否错误
		if(time<0 || time>1800){
			returnMap.put(MAP_TYPE,false);
			returnMap.put(MAP_MSG,"time error");
			return returnMap;
		}
		//判断场景值是否错误
		if(value<0 || Integer.toString(value).length()>32){
			returnMap.put(MAP_TYPE,false);
			returnMap.put(MAP_MSG,"value error");
			return returnMap;		
		}
		String params = "{\"expire_seconds\": " + time + ", \"action_name\": \"QR_SCENE\", \"action_info\": {\"scene\": {\"scene_id\": " + value + "}}}";
		logger.debug("生成临时二维码的json数据："+params);
		returnMap = createQrcode(path,params);
		return returnMap;
	}	
	
	/**
	 *创建二维码ticket，
	 * @param path 创建二维码的路径+生成二维码的文件名(如:f:\\qrcode\\2.jpg)
	 * @param params 创建二维码的json数据</br>
	 * 							生成临时二维码json:{"expire_seconds": 1800, "action_name": "QR_SCENE", "action_info": {"scene": {"scene_id": 123}}}</br>
	 * 							生成永久二维码的json:{"action_name": "QR_LIMIT_SCENE", "action_info": {"scene": {"scene_id": 123}}}</br>
	 * @return map类型<br/>
	 *         type: true:获取成功 false:获取失败<br/>
	 *         msg:返回信息
	 * @throws Exception
	 */
	private Map<String,Object> createQrcode(String path,String params) {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		String accessToken = getAccessToken();
		if (!StringUtil.isBlank(accessToken)) {
			try {
				HttpPost httpost = HttpClientConnectionManager.getPostMethod(CREATE_TICKET + accessToken);

				httpost.setEntity(new StringEntity(params, "UTF-8"));
				HttpResponse response;
				response = HTTPCLIENT.execute(httpost);
				String jsonStr = EntityUtils.toString(response.getEntity(), "utf-8");
				logger.debug("生成二维码返回json:"+jsonStr);
				JSONObject obj = JSONObject.parseObject(jsonStr);
			
				if (obj.getString("ticket") != null) {
					HttpGet get = HttpClientConnectionManager.getGetMethod(GET_QRCODE + obj.getString("ticket"));
					HttpResponse getResponse = HTTPCLIENT.execute(get);
					InputStream is = getResponse.getEntity().getContent();
					OutputStream os = null;
					try {
						os = new FileOutputStream(new File(path));
						int bytesRead = 0;
						byte[] buffer = new byte[100];
						while ((bytesRead = is.read(buffer, 0, 100)) != -1) {
							os.write(buffer, 0, bytesRead);
						}
						os.close();
						is.close();
					} catch (Exception e) {
						logger.debug("二维码文件路径错误或者是没有文件名！");
						e.printStackTrace();
					} finally {
						if (os != null) {
							os.close();
						}

						if (is != null) {
							is.close();
						}
					}
					logger.debug("二维码生成成功");
					returnMap.put(MAP_TYPE, true);
					returnMap.put(MAP_MSG,"success");
				} else if (obj.getString("errcode") != null) {
					returnMap.put(MAP_TYPE, false);
					returnMap.put(MAP_MSG, obj.getString("errmsg"));
					logger.debug("二维码生成失败:"+obj.getString("errmsg"));
				}
			} catch (UnsupportedEncodingException e1) {
				// TODO Auto-generated catch block
				logger.debug(e1);
				e1.printStackTrace();
			} catch (ClientProtocolException e1) {
				// TODO Auto-generated catch block
				logger.debug(e1);
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				logger.debug(e1);
				e1.printStackTrace();
			}
		} else {
			returnMap.put(MAP_TYPE, false);
			returnMap.put(MAP_MSG,"accessToken error");
		}
		return returnMap;
	}
}
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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;
import com.mingsoft.util.JsonUtil;
import com.mingsoft.util.StringUtil;
import com.mingsoft.weixin.http.HttpClientConnectionManager;

/**
 * 
 * 获取关注者列表和,关注者信息
 * @author 成卫雄(qq:330216230)
 * @version 
 * 版本号：100-000-000<br/>
 * 创建日期：2014-9-30<br/>
 * 历史修订：<br/>
 */
public class UserUtils extends BaseUtils {

	public UserUtils(String appid, String secret) {
		super(appid, secret);
	}
	
	/**
	 * 获取关注者的列表
	 * @param nextOpenid 定位 取该用户以下的列表 不需要的就为空 "" 。
	 * @return json格式的字符串
	 */
	public Map<String, Object> queryUserOpenidList(String nextOpenid){
		String accessToken = getAccessToken();
		if(!StringUtil.isBlank(accessToken)){
			HttpGet get = HttpClientConnectionManager.getGetMethod(GET_USER_OPENID_LIST + accessToken + "&next_openid=" + nextOpenid);
			HttpResponse response = null;
			try {
				response = HTTPCLIENT.execute(get);
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String jsonStr = null;
			try {
				jsonStr = EntityUtils.toString(response.getEntity(), "utf-8");
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			logger.debug(jsonStr);
			Map<String, Object> userMap = JsonUtil.getMap4Json(jsonStr);			
			return userMap;
		}
		return null;
	}
	
	/**
	 * 获取关注者的列表
	 * @return json格式的字符串:</br>
	 * {"total":15,"count":15,"data":{"openid":["o8pZtuODm-z_nE-QK07C1ly83C60","o8pZtuFRBHt0jsXgmfPOmHZ1WmOE","o8pZtuL7F25QtlSGe3eneLDD5knk","o8pZtuJTdMCw7aGoU0RHiliKXCXI","o8pZtuH85YIwK1GrN9OmchzjWrl4","o8pZtuCkj4Flo6R_6U4WRtbswo2o","o8pZtuJ7Lze1yLRBcO3q7o8XR1W8","o8pZtuGcDD_B_opTX7nL1yR4n7l0","o8pZtuA-PWE28urS0UIDIPnsn0Ts","o8pZtuN2bYzZmKwAe3XABJj6i3yU","o8pZtuCc0pNuMy7oRRhcTsghPywI","o8pZtuPefV_kJt_Raeg7SGbYSyDU","o8pZtuCfY3vz1ixFkDi4tCc6BI34","o8pZtuIjlszCOQSZzkiwqBS1EZPI","o8pZtuNV4Fr608H8oozibwDBqM1Y"]},"next_openid":"o8pZtuNV4Fr608H8oozibwDBqM1Y"}
	 */
	public Map<String, Object> queryUserOpenidList(){
		String accessToken = getAccessToken();
		if(!StringUtil.isBlank(accessToken)){
			HttpGet get = HttpClientConnectionManager.getGetMethod(GET_USER_OPENID_LIST + accessToken);
			HttpResponse response = null;
			try {
				response = HTTPCLIENT.execute(get);
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String jsonStr = null;
			try {
				jsonStr = EntityUtils.toString(response.getEntity(), "utf-8");
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			logger.debug(jsonStr);
			Map<String, Object> userMap = JsonUtil.getMap4Json(jsonStr);
			return userMap;
		}
		return null;
	}	
	
	/**
	 * 通过用户的openid获取用户的基本资料</br>
	 *  {"subscribe":1,"openid":"oUjS1t8Xf7d1R0cluPt8yn6DSaEM","nickname":"梦中故里","sex":1,"language":"zh_CN","city":"景德镇","province":"江西","country":"中国","headimgurl":"http:\/\/wx.qlogo.cn\/mmopen\/HicffCPSemusLMOjeGo5xMH0TfDuRP0IFR5RUkdEbvsRorAvibp1oNkEUfcXfSgAG0oYUjuhCZpvjNjE4UhW2ZjSWX5v6cW4x1\/0","subscribe_time":1412481658,"remark":""}</br>
	 * @param openid 用户的openid
	 * @return 返回用户的昵称
	 */
	public Map<String, Object> syncUserInfo(String openid){
		String accessToken = getAccessToken();
		if(!StringUtil.isBlank(accessToken)){
			HttpGet get = HttpClientConnectionManager.getGetMethod(GET_USERDATA_BYOPENID + accessToken + "&openid=" + openid + "&lang=zh_CN");
			HttpResponse response = null;
			try {
				response = HTTPCLIENT.execute(get);
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String jsonStr = null;
			try {
				jsonStr = EntityUtils.toString(response.getEntity(), "utf-8");
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			logger.debug(jsonStr);
			Map<String, Object> userMap = JsonUtil.getMap4Json(jsonStr);
			if(userMap.get("errmsg") != null){
				logger.debug("获取用户信息返回错误："+jsonStr);
				return null;
			}
			return userMap;
		}
		return null;
	}
	
	/**
	 * 获取所有用户信息</br>
	 * 最多每次返回100条用户数据</br>
	 * 当用户数量超过100时查询其余的时需要传入，上面100条数据中最后一个用户的openId</br>
	 *  {"subscribe":1,"openid":"oUjS1t8Xf7d1R0cluPt8yn6DSaEM","nickname":"梦中故里","sex":1,"language":"zh_CN","city":"景德镇","province":"江西","country":"中国","headimgurl":"http:\/\/wx.qlogo.cn\/mmopen\/HicffCPSemusLMOjeGo5xMH0TfDuRP0IFR5RUkdEbvsRorAvibp1oNkEUfcXfSgAG0oYUjuhCZpvjNjE4UhW2ZjSWX5v6cW4x1\/0","subscribe_time":1412481658,"remark":""}</br>
	 *  @param nextOpenid开始位置的用户openId,若从第一个用户开始则传入""(空)
	 *  @param peopleNum 一次查询用户的条数最大1W
	 *  @return 返回用户信息 key:对应json中的名称</br>
	 * 									value:对应json中的值</br>
	 */
	public List<Map<String, Object>> queryAllUserInfo(String nextOpenid,int peopleNum){
		String accessToken = getAccessToken();
		if(StringUtil.isBlank(accessToken)){
			return null;
		}
		if(nextOpenid == null){
			nextOpenid = "";
		}
		if(peopleNum>10000){
			return null;
		}
		if(!StringUtil.isBlank(accessToken)){
			HttpGet get = HttpClientConnectionManager.getGetMethod(GET_USER_OPENID_LIST + accessToken + "&next_openid=" + nextOpenid);
			HttpResponse response = null;
			try {
				response = HTTPCLIENT.execute(get);
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String jsonStr = null;
			try {
				jsonStr = EntityUtils.toString(response.getEntity(), "utf-8");
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			logger.debug(jsonStr);
			Map<String, Object> userMap = JsonUtil.getMap4Json(jsonStr);//拉取到的用户openId
			if(Integer.parseInt(userMap.get("count").toString()) == 0){
				return null;
			}			
			logger.debug("总用户数量:"+userMap.get("total").toString()+"拉取到的用户数量:"+userMap.get("count").toString()+"拉取到的openId集合+"+userMap.get("data").toString());
			
			//获取用户openid数据格式[openid1,openid2,.....]
			Map<String, Object> _userMap = JsonUtil.getMap4Json(userMap.get("data").toString());
			String[] openIdStrArray = JsonUtil.getStringArray4Json(_userMap.get("openid").toString());
			
			if(!(Integer.parseInt(userMap.get("count").toString())>peopleNum)){
				peopleNum = Integer.parseInt(userMap.get("count").toString());
			}
			
			//储存返回的用户数据
			List<Map<String,Object>> listMap = new ArrayList<Map<String,Object>>();
			for(int i=0;i<peopleNum;i++){
				listMap.add(syncUserInfo(openIdStrArray[i]));
			}
			
			return listMap;
		}		
		
		return null;
	}
	
}
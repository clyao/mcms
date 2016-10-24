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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mingsoft.util.MD5Util;
import com.mingsoft.util.RegexUtil;
import com.mingsoft.util.StringUtil;
import com.mingsoft.util.proxy.Header;
import com.mingsoft.util.proxy.Proxy;
import com.mingsoft.util.proxy.Result;

/**
 * 
 * 铭飞科技mms
 * Copyright: Copyright (c) 2014 - 2015
 * @author 成卫雄    QQ:330216230
 * Comments:微信数据分析工具
 * Create Date:2014-9-20
 * Modification history:
 */
public class WeixinAnalysisUtil {

	protected final static Logger LOG = Logger.getLogger(WeixinAnalysisUtil.class);
	
	/**
	 * 数据请求访问主机
	 */
	private static String HOST = "mp.weixin.qq.com";

	/**
	 * 微信的JSON数据中请求结果的父名称
	 */
	private static String JSON_NAME_BASE_RESP="base_resp";

	/**
	 * 请求是否成功</br>
	 * 微信返回JSON数据中的子名称</br>
	 */
	private static String JSON_NAME_RET="ret";

	/**
	 * 请求成功的标记值
	 */
	private static int JSON_VALUE_SUCCESS_RET = 0;

	/**
	 * 微信登录成功后动态生成的密匙
	 */
	private String token;
	
	/**
	 * cookie值
	 */
	private String cookie;
	
	/**
	 * 构造方法模拟登录
	 * @param weixinUserName 微信用户名
	 * @param weixinPassword 微信密码
	 */
	public WeixinAnalysisUtil(String weixinUserName,String weixinPassword){
		Map<String,String> map = login(weixinUserName, weixinPassword);
		if(map != null){
			this.cookie = map.get("cookie");
			this.token = map.get("token");
			LOG.debug("----------COOKIE-"+this.cookie+"===token-"+this.token);
		}
	}

	//获取素材列表
	//获取用户详细信息
	
	/**
	 * 将用户批量移动到指定分组
	 * @param groupId 将用户移动到分组Id
	 * @param appId 用户ID集合
	 * @return true
	 */
	public Boolean moveGroupPeopleByBatch(String groupId,List<String> peopleIdList){
		String url = "https://mp.weixin.qq.com/cgi-bin/modifycontacts?action=modifycontacts&t=ajax-putinto-group";
		String referer = "https://mp.weixin.qq.com/cgi-bin/contactmanage?t=user/index&pagesize=10&pageidx=0&type=0&groupid="+groupId+"&token="+this.token+"&lang=zh_CN";
		
		if(peopleIdList==null || peopleIdList.size()==0){
			return false;
		}
		
		//请求条件
		Map<String, String> params = new HashMap<String, String>();	
		params.put("token", this.token);
		params.put("lang", "zh_CN");
		params.put("f", "json");
		params.put("ajax", "1");
		params.put("contacttype", groupId);
		
		String peopleId = "";
		for(int i=0;i<peopleIdList.size();i++){
			if(i == (peopleIdList.size()-1)){
				peopleId +=  peopleIdList.get(i);
				break;
			}
			peopleId +=  peopleIdList.get(i)+"|";
		}
		
		LOG.debug("====批量移动的用户ID:"+peopleId);
		params.put("tofakeidlist", peopleId);		

		Result results =  postRequest(url,referer,this.cookie, params);
		String content = results.getContent("utf8");
		LOG.debug("移动分组返回:"+content);
		
		if(JSONObject.parseObject(content).getInteger("ret") == 0){
			LOG.debug("====移动分组成功====");
			return true;
		}
		
		return false;
	}
	
	
	/**
	 * 将用户移动到指定分组
	 * @param groupId 分组ID
	 * @param peopleId 用户ID
	 * @return true 移动成功
	 * {"ret":"0","result":[{"fakeId":"2950233560","ret":"0"} ]}
	 */
	public Boolean moveGroupPeople(String groupId,String peopleId){
		String url = "https://mp.weixin.qq.com/cgi-bin/modifycontacts?action=modifycontacts&t=ajax-putinto-group";
		String referer = "https://mp.weixin.qq.com/cgi-bin/contactmanage?t=user/index&pagesize=10&pageidx=0&type=0&groupid="+groupId+"&token="+this.token+"&lang=zh_CN";
		
		//请求条件
		Map<String, String> params = new HashMap<String, String>();	
		params.put("token", this.token);
		params.put("lang", "zh_CN");
		params.put("f", "json");
		params.put("ajax", "1");
		params.put("contacttype", groupId);
		params.put("tofakeidlist", peopleId);
		
		Result results =  postRequest(url,referer,this.cookie, params);
		String content = results.getContent("utf8");
		LOG.debug("移动分组返回:"+content);
		
		if(JSONObject.parseObject(content).getInteger("ret") == 0){
			LOG.debug("====移动分组成功====");
			return true;
		}
		
		return false;
	}
	
	
	/**
	 * 获取公众号所有分组</br>
	 * {"id":0,"name":"未分组","cnt":567}</br>
	 * id:分组ID</br>
	 * name:分组名称</br>
	 * cnt:当前分组的人数</br>
	 * @return list map key:1 value分组Id</br>
	 * 						      key:2 value:分组名称</br>
	 * 							 key:3  value:当前分组人数</br>
	 */
	public List<Map<Integer,String>> getGroup(){
		LOG.debug("----------COOKIE-"+this.cookie+"===token-"+this.token);
		//请求地址
		String url = "https://mp.weixin.qq.com/cgi-bin/contactmanage";
		String regex = "\\(\\{\\\"groups\\\"\\:(\\[.*\\])\\}\\)";
		//请求条件
		Map<String, String> params = new HashMap<String, String>();	
		params.put("t", "user/index");
		params.put("type", "0");
		params.put("token", this.token);
		params.put("lang", "zh_CN");
		//请求系的数据
		Result results =  getRequest(url,null,this.cookie, params);
		String groupJson = RegexUtil.parseFirst(results.getContent("utf8"), regex, 1);//获取截取到的用户JSON信息
		LOG.debug("分组的JSON数据："+groupJson);
		List<Object> list = (List<Object>) JSON.parse(groupJson);
		
		List<Map<Integer,String>> groupList = new ArrayList<Map<Integer,String>>();
		
		for(int i=0;i<list.size();i++){
			Map<Integer,String> map = new HashMap<Integer, String>();
			JSONObject goroup = JSON.parseObject(list.get(i).toString());
			map.put(1, goroup.getString("id"));
			map.put(2, goroup.getString("name"));
			map.put(3, goroup.getString("cnt"));
			groupList.add(map);
		}
		
		return groupList;
	}
	
	/**
	 * 向单个用户发送消息
	 * @param tofakeid 微信对用户的唯一标识
	 * @param messageType 发送消息的类型,10：图文;1：文字
	 * @param managerContent 发送消息的内容，如果为图文则传入图文的Id
	 * @return true
	 */
	public Boolean messageSendPeople(String tofakeid,int messageType,String messageContent){
		String url = "https://mp.weixin.qq.com/cgi-bin/singlesend";//请求地址
		String referer = "https://mp.weixin.qq.com/cgi-bin/singlesendpage";//请求返回地址
		
		//请求条件
		Map<String, String> params = new HashMap<String, String>();	
		params.put("token",this.token);//动态获取
		params.put("lang", "zh_CN");
		params.put("f", "json");
		params.put("ajax", "1");
		params.put("tofakeid",tofakeid);
		params.put("imgcode", "");		
		params.put("type",Integer.toString(messageType));//图文 10 ;文字 1 ;图片 2;
		
		if(messageType == 1){
			params.put("content",messageContent);//发送文字消息
		}else if(messageType == 10){
			params.put("appmsgid",messageContent);//发送图文消息
		}else{
			LOG.debug("------------------消息类型错误");
			return false;
		}
		//请求系的数据
		Result results =  postRequest(url, referer,this.cookie, params);
		String content = results.getContent("utf8");
		LOG.debug("------------------content("+content+")");
		if(!StringUtil.isBlank(content)&& Integer.parseInt(JSON.parseObject(content).getJSONObject(JSON_NAME_BASE_RESP).getString(JSON_NAME_RET)) == JSON_VALUE_SUCCESS_RET){
			return true;
		}
		LOG.debug("------------------消息发送失败");
		return false;
	}

	/**
	 * 消息群发(对传入的用户集合群发消息)
	 * @param tofakeidList 用户集合
	 * @param messageType 消息类型 10：图文;1：文字
	 * @param managerContent 消息内容
	 * @return map key = success 消息发送成功用户集合 ；</br>
	 * 						key = failure 消息发送失败用户集合,一般是用户处于静默状态</br>
	 */
	public Map<String,List<String>> messageGroupSendPeople(List<String> tofakeidList,int messageType,String managerContent){
		
		if(tofakeidList != null){
			List<String> successList = new ArrayList<String>();//保存消息发送成功的用户信息
			List<String> failureList = new ArrayList<String>();//报讯消息发送失败的用户信息
			
			for(int i=0;i<tofakeidList.size();i++){
		            
						Boolean state = messageSendPeople(tofakeidList.get(i),messageType,managerContent);//发送消息
						if(state){
							successList.add(tofakeidList.get(i));
						}else{
							failureList.add(tofakeidList.get(i));
						}
						
			}
			
			Map<String,List<String>> map = new HashMap<String, List<String>>();
			map.put("success", successList);
			map.put("failure", failureList);
			return map;
		}
		
		return null;
	}


	/**
	 * 获取指定分组用户列表
	 * @param groupId 分组ID(当值为0是获取未分组用户信息，为-1获取所有用户信息)
	 * @param 一次获取用户的次数(根据微信分页)
	 * @param 当前获取的次数(根据微信分页)
	 * @return  用户Id列表
	 * 					用户列表 {"id":1602618718,"nick_name":"要快乐啊！","remark_name":"","group_id":0}
	 */
	public List<String> getPeopleList(int groupId,int pageSize,int pageNo){
		if(pageSize <= 0 ){
			return null;
		}
		//请求地址
		String url = "https://mp.weixin.qq.com/cgi-bin/contactmanage";
		String regex = "\\(\\{\\\"contacts\\\"\\:(\\[.*\\])\\}\\)";
		//请求条件
		Map<String, String> params = new HashMap<String, String>();	
		params.put("t", "user/index");
		params.put("pagesize",Integer.toString(pageSize));
		params.put("pageidx",Integer.toString(pageNo));
		params.put("type", "0");
		if(groupId != -1){
			params.put("groupid",Integer.toString(groupId));	//100,102,103
		}
		params.put("token", this.token);
		params.put("lang", "zh_CN");
		//请求系的数据
		Result results =  getRequest(url,null,this.cookie, params);
		String peopleJson = RegexUtil.parseFirst(results.getContent("utf8"), regex, 1);//获取截取到的用户JSON信息
		
		List<String> peopleList = new ArrayList<String>();//储存用户id信息
		List<Object> list = (List<Object>) JSON.parse(peopleJson);
		
		if(list != null){
			for(int i=0;i<list.size();i++){
				String peopleId = JSON.parseObject(list.get(i).toString()).get("id").toString();
				peopleList.add(peopleId);
				LOG.debug("============用户信息"+peopleId);
			}
		}
		LOG.debug("======获取用户的数量："+list.size());
		return peopleList;
	}
	
	/**
	 * 获取最新消息数量,最新关注人数,和总用户数
	 * @return List(0):最新消息数量;List(1):最新关注人数;List(2):总用户数
	 */
	public List<String> getNewPeople(){
		String url = "https://mp.weixin.qq.com/cgi-bin/home";//请求地址
		String referer = "https://mp.weixin.qq.com/cgi-bin/contactmanage?t=setting/index&action=index&lang=zh_CN&token="+this.token;//请求返回地址
		String regex = "\\\"(.*?)\\\"\\>([0-9]{0,})\\<\\/em\\>";
		
		//请求条件
		Map<String, String> params = new HashMap<String, String>();
		params.put("t","home/index");
		params.put("lang","zh_CN");
		params.put("token",this.token );		
		
		Result results = getRequest(url, referer, cookie, params);
		String content = results.getContent("utf-8");
		List<String> list = RegexUtil.parseAll(content, regex,2); 
		
		return list;
	}
	
	/**
	 * 模拟微信登录
	 * @return
	 */
	private Map<String,String> login(String weixinUserName,String weixinPassword){
		String url = "https://mp.weixin.qq.com/cgi-bin/login";//登录请求地址
		String referer = "https://mp.weixin.qq.com/";//登录返回地址
		String regexToken ="token\\=([0-9]{0,})";//截取token的正则表达式
		
		//请求条件
		Map<String, String> params = new HashMap<String, String>();
		params.put("username",weixinUserName);//动态获取
		params.put("pwd", MD5Util.MD5Encode(weixinPassword,null));//微信的访问密码采用md5 64位加密
		params.put("f", "json");
		params.put("imgcode", "");	
		
		Result results = postRequest(url, referer,null, params);
		String content = results.getContent("utf-8");
		
		Map<String,String> map = new HashMap<String, String>();
		if(!StringUtil.isBlank(content)&&Integer.parseInt(JSON.parseObject(content).getJSONObject(JSON_NAME_BASE_RESP).getString(JSON_NAME_RET))==JSON_VALUE_SUCCESS_RET){
			String cookie =  results.getCookie();
			String token =  RegexUtil.parseFirst(JSON.parseObject(content).get("redirect_url").toString(), regexToken, 1);
			LOG.debug("------------微信登录成功----cookie("+cookie+")-----token("+token+")");
			map.put("cookie",cookie);
			map.put("token",token);
			return map;
		}
		LOG.debug("------------微信登录失败");
		return null;
	}	
	
	/**
	 * 发起post请求
	 * @param url 请求地址(必填)
	 * @param referer 头部携带的返回地址(选填)
	 * @param cookie 访问的cookie值(登录时不需要填,其他时候为必填)
	 * @param params 携带的返回值
	 * @return 获取到的页面返回值
	 */
	private Result postRequest(String url,String referer,String cookie,Map<String, String> params){
		//头部参数
		Header header = new Header(url,"utf8");
		header.setHeader("Host",HOST);
		
		if(!StringUtil.isBlank(cookie)){
			header.setCookie(cookie);
		}
		
		if(!StringUtil.isBlank(referer)){
			header.setHeader("Referer", referer);
		}
		
		Result results =  Proxy.post(url,header, params,"utf8");
		
		return results;
	}
	
	/**
	 * 发起get请求
	 * @param url 请求地址(必填)
	 * @param referer 头部携带的返回地址(选填)
	 * @param cookie 访问的cookie值(登录时不需要填,其他时候为必填)
	 * @param params 携带的返回值
	 * @return 获取到的页面返回值
	 */
	private Result getRequest(String url,String referer,String cookie,Map<String, String> params){
		//头部参数
		Header header = new Header(url,"utf8");
		header.setHeader("Host",HOST);
		
		if(!StringUtil.isBlank(cookie)){
			header.setCookie(cookie);
		}
		
		if(!StringUtil.isBlank(referer)){
			header.setHeader("Referer", referer);
		}
		
		Result results =  Proxy.get(url,header, params);
		
		return results;
	}	

	
}
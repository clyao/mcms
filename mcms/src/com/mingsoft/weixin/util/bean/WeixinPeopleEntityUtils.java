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

package com.mingsoft.weixin.util.bean;

import java.util.Date;
import java.util.Map;

import com.mingsoft.util.JsonUtil;
import com.mingsoft.util.StringUtil;
import com.mingsoft.weixin.entity.WeixinPeopleEntity;
import com.mingsoft.weixin.util.OauthUtils;

/**
 * 微信用户信息实体转换工具</br>
 * @author 成卫雄(qq:330216230)
 * @version 
 * 版本号：100-000-000<br/>
 * 创建日期：2015年9月24日 上午10:53:44<br/>
 * 历史修订：<br/>
 */
public class WeixinPeopleEntityUtils extends BaseBeanUtils{
	
	/**
	 * 将获取到微信开放平台的详细信息转换成微信用户实体
	 * @param userInfoJson 用户详细信息JSON格式
	 * @param peopleAppId 用户所属的应用iD
	 * @param weixinId 微信ID
	 * @return 微信用户实体
	 */
	public static WeixinPeopleEntity userInfoToWeixinPeople(String userInfoJson,int peopleAppId,int weixinId){
		if(StringUtil.isBlank(userInfoJson)){
			return null;
		}
		//将获取到JSON格式的微信用户信息进行转换成Map
		Map<String,Object> userMap = JsonUtil.getMap4Json(userInfoJson);
		if(StringUtil.isBlank(userMap.get("openid"))){
			return null;
		}
		WeixinPeopleEntity weixinPeopleEntity = new WeixinPeopleEntity();
		weixinPeopleEntity.setPeopleUserNickName(userMap.get("nickname").toString());
		weixinPeopleEntity.setPeopleUserSex(Integer.parseInt(userMap.get("sex").toString()));
		weixinPeopleEntity.setWeixinPeopleProvince(userMap.get("province").toString());
		weixinPeopleEntity.setPeopleUserIcon(userMap.get("headimgurl").toString());//用户头像
		weixinPeopleEntity.setWeixinPeopleCity(userMap.get("city").toString());
		weixinPeopleEntity.setWeixinPeopleHeadimgUrl(userMap.get("headimgurl").toString());
		weixinPeopleEntity.setWeixinPeopleState(WeixinPeopleEntity.WEIXIN_PEOPLE_OPEN);
		weixinPeopleEntity.setPeopleUserAppId(peopleAppId);
		weixinPeopleEntity.setPeopleAppId(peopleAppId);
		weixinPeopleEntity.setWeixinPeopleAppId(peopleAppId);
		weixinPeopleEntity.setWeixinPeopleWeixinId(weixinId);
		weixinPeopleEntity.setWeixinPeopleOpenId(userMap.get("openid").toString());
		weixinPeopleEntity.setPeopleDateTime(new Date());		
		return weixinPeopleEntity;		
	}
	
	/**
	 * 将获取到微信App用户的详细信息转换成微信用户实体
	 * @param userMap 用户详细信息
	 * @param peopleAppId 用户所属的应用iD
	 * @param weixinId 微信ID
	 * @return 微信用户实体
	 */
	public static WeixinPeopleEntity userInfoToWeixinPeople(Map<String,Object> userMap,int peopleAppId,int weixinId){
		if(userMap == null || StringUtil.isBlank(userMap.get("openid"))){
			return null;
		}
		WeixinPeopleEntity weixinPeopleEntity = new WeixinPeopleEntity();
		//判断授权方式是否为:snsapi_userinfo,是否可获取到用户的详细信息
		if(!StringUtil.isBlank(userMap.get(OauthUtils.SCOPE)) && userMap.get(OauthUtils.SCOPE).toString().equals(OauthUtils.SCOPE_USERINFO)){
			weixinPeopleEntity.setPeopleUserNickName(userMap.get("nickname").toString());
			weixinPeopleEntity.setPeopleUserSex(Integer.parseInt(userMap.get("sex").toString()));
			weixinPeopleEntity.setWeixinPeopleProvince(userMap.get("province").toString());
			weixinPeopleEntity.setWeixinPeopleCity(userMap.get("city").toString());
			weixinPeopleEntity.setWeixinPeopleHeadimgUrl(userMap.get("headimgurl").toString());
		}
		weixinPeopleEntity.setWeixinPeopleState(WeixinPeopleEntity.WEIXIN_PEOPLE_OAUTH_WATCH);
		weixinPeopleEntity.setPeopleUserAppId(peopleAppId);
		weixinPeopleEntity.setPeopleAppId(peopleAppId);
		weixinPeopleEntity.setWeixinPeopleAppId(peopleAppId);
		weixinPeopleEntity.setWeixinPeopleWeixinId(weixinId);
		weixinPeopleEntity.setWeixinPeopleOpenId(userMap.get("openid").toString());
		weixinPeopleEntity.setPeopleDateTime(new Date());		
		return weixinPeopleEntity;		
	}	
}
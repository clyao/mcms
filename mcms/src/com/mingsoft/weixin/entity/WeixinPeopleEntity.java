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

package com.mingsoft.weixin.entity;

import com.mingsoft.people.entity.PeopleUserEntity;

/**
 * mswx-铭飞微信酒店预订平台
 * Copyright: Copyright (c) 2014 - 2015
 * Company:景德镇铭飞科技有限公司
 * @author 石超
 * @version 300-001-001
 * 版权所有 铭飞科技
 * Comments: 关联微信用户和微信号
 * Create Date:2013-12-23
 * Modification history:
 */
public class WeixinPeopleEntity extends PeopleUserEntity {
	
	/**
	 * 用户处于关注状态
	 */
	public final static int WEIXIN_PEOPLE_WATCH = 1;
	
	/**
	 * 该用户已取消关注
	 */
	public final static int WEIXIN_PEOPLE_CANCEL_WATCH = 2;
	
	/**
	 * 该用户通过授权访问
	 */
	public final static int WEIXIN_PEOPLE_OAUTH_WATCH = 3;
	
	/**
	 * 通过微信开放平台登录
	 */
	public final static int WEIXIN_PEOPLE_OPEN = 4;
	
	/**
	 * 关联微信Id
	 */
	private int weixinPeopleWeixinId;
	
	/**
	 * 微信Id
	 */
	private int weixinPeopleAppId;
	
	/**
	 * 用户在微信中的唯一标识
	 */
	private String weixinPeopleOpenId;

	/**
	 * 用户所在省份
	 */
	private String weixinPeopleProvince;
	
	/**
	 * 用户所在城市
	 */
	private String weixinPeopleCity;
	
	/**
	 * 用户头像链接地址
	 */
	private String weixinPeopleHeadimgUrl;	

	/**
	 * 用户关注状态
	 * 1.关注中用户(默认)
	 * 2.取消关注用户
	 */
	private int weixinPeopleState = 1;
	
	
	public int getWeixinPeopleWeixinId() {
		return weixinPeopleWeixinId;
	}

	public void setWeixinPeopleWeixinId(int weixinPeopleWeixinId) {
		this.weixinPeopleWeixinId = weixinPeopleWeixinId;
	}

	/**
	 * 获取weixinPeopleProvince
	 * @return  weixinPeopleProvince
	 */
	public String getWeixinPeopleProvince() {
		return weixinPeopleProvince;
	}

	/**
	 * 设置weixinPeopleProvince
	 * @param weixinPeopleProvince
	 */
	public void setWeixinPeopleProvince(String weixinPeopleProvince) {
		this.weixinPeopleProvince = weixinPeopleProvince;
	}

	/**
	 * 获取weixinPeopleCity
	 * @return  weixinPeopleCity
	 */
	public String getWeixinPeopleCity() {
		return weixinPeopleCity;
	}

	/**
	 * 设置weixinPeopleCity
	 * @param weixinPeopleCity
	 */
	public void setWeixinPeopleCity(String weixinPeopleCity) {
		this.weixinPeopleCity = weixinPeopleCity;
	}

	/**
	 * 获取weixinPeopleHeadimgUrl
	 * @return  weixinPeopleHeadimgUrl
	 */
	public String getWeixinPeopleHeadimgUrl() {
		return weixinPeopleHeadimgUrl;
	}

	/**
	 * 设置weixinPeopleHeadimgUrl
	 * @param weixinPeopleHeadimgUrl
	 */
	public void setWeixinPeopleHeadimgUrl(String weixinPeopleHeadimgUrl) {
		this.weixinPeopleHeadimgUrl = weixinPeopleHeadimgUrl;
	}

	/**
	 * 获取weixinPeopleAppId
	 * @return  weixinPeopleAppId
	 */
	public int getWeixinPeopleAppId() {
		return weixinPeopleAppId;
	}

	/**
	 * 设置weixinPeopleAppId
	 * @param weixinPeopleAppId
	 */
	public void setWeixinPeopleAppId(int weixinPeopleAppId) {
		this.weixinPeopleAppId = weixinPeopleAppId;
	}

	/**
	 * 获取weixinPeopleOpenId
	 * @return  weixinPeopleOpenId
	 */
	public String getWeixinPeopleOpenId() {
		return weixinPeopleOpenId;
	}

	/**
	 * 设置weixinPeopleOpenId
	 * @param weixinPeopleOpenId
	 */
	public void setWeixinPeopleOpenId(String weixinPeopleOpenId) {
		this.weixinPeopleOpenId = weixinPeopleOpenId;
	}
	
	/**
	 * 获取用户关注状态
	 * @return
	 */
	public int getWeixinPeopleState() {
		return weixinPeopleState;
	}
	
	/**
	 * 设置用户关注状态
	 * @param weixinPeopleState
	 */
	public void setWeixinPeopleState(int weixinPeopleState) {
		this.weixinPeopleState = weixinPeopleState;
	}
	
}
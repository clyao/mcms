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

import com.mingsoft.base.entity.BaseEntity;

/**
 * 微信证书实体
 * @Package com.mingsoft.weixin.entity 
 * @author 李书宇
 * @version 
 * 版本号：<br/>
 * 创建日期：@date 2015年9月10日<br/>
 * 历史修订：<br/>
 */
public class WeixinCertificateEntity extends BaseEntity{

	/**
	 * 微信证书id
	 */
	private int weixinCertificateId;
	
	/**
	 * 微信证书关联id
	 */
	private int weixinCertificateWeixinId;
	
	/**
	 * 微信证书关联appid
	 */
	private int weixinCertificateAppId;
	
	/**
	 * 微信证书秘钥
	 */
	private String weixinCertificateKey;
	
	/**
	 * 微信证书路径
	 */
	private String weixinCertificateUrl;
	
	/**
	 * 微信证书描述
	 */
	private String weixinCertificateDescription;
	
	/**
	 * 微信证书类型
	 */
	private int weixinCertificateType;

	public int getWeixinCertificateId() {
		return weixinCertificateId;
	}

	public void setWeixinCertificateId(int weixinCertificateId) {
		this.weixinCertificateId = weixinCertificateId;
	}

	public int getWeixinCertificateWeixinId() {
		return weixinCertificateWeixinId;
	}

	public void setWeixinCertificateWeixinId(int weixinCertificateWeixinId) {
		this.weixinCertificateWeixinId = weixinCertificateWeixinId;
	}

	public int getWeixinCertificateAppId() {
		return weixinCertificateAppId;
	}

	public void setWeixinCertificateAppId(int weixinCertificateAppId) {
		this.weixinCertificateAppId = weixinCertificateAppId;
	}

	public String getWeixinCertificateKey() {
		return weixinCertificateKey;
	}

	public void setWeixinCertificateKey(String weixinCertificateKey) {
		this.weixinCertificateKey = weixinCertificateKey;
	}

	public String getWeixinCertificateUrl() {
		return weixinCertificateUrl;
	}

	public void setWeixinCertificateUrl(String weixinCertificateUrl) {
		this.weixinCertificateUrl = weixinCertificateUrl;
	}

	public String getWeixinCertificateDescription() {
		return weixinCertificateDescription;
	}

	public void setWeixinCertificateDescription(String weixinCertificateDescription) {
		this.weixinCertificateDescription = weixinCertificateDescription;
	}

	public int getWeixinCertificateType() {
		return weixinCertificateType;
	}

	public void setWeixinCertificateType(int weixinCertificateType) {
		this.weixinCertificateType = weixinCertificateType;
	}

	
	
}
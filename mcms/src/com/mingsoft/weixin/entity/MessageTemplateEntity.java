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
 * 铭飞科技
 * Copyright: Copyright (c) 2014 - 2015
 * @author yangxy
 * Comments:自定义发送模板消息实体类
 * Create Date:2015-5-22
 * Modification history:
 */
public class MessageTemplateEntity extends BaseEntity{
	
	/**
	 * 消息模板id
	 */
	private int messageTemplateId;
	
	/**
	 * 消息状态
	 */
	private int messageTemplateStatus;
	
	/**
	 * 模板标题颜色
	 */
	private int messageTemplateTopcolor;
	
	/**
	 * 对应微信里面的消息id
	 */
	private String messageTemplateTemplateId;
	
	/**
	 * 微信消息模板所在的应用id
	 */
	private int messageTemplateAppId;
	
	/**
	 * 微信消息模板所在的微信ID
	 */
	private int messageTemplateWeixinId;
	

	/**
	 * 对应模块编号
	 */
	private int messageTemplateModelId;
	
	/**
	 * 模板消息标题
	 */
	private String messageTemplateTitle;
	
	/**
	 * 模板消息标题显示颜色
	 */
	private String messageTemplateTitleColor;
	
	/**
	 * 模板消息摘要
	 */
	private String messageTemplateRemark;
	
	/**
	 * 模板消息摘要显示颜色
	 */
	private String messageTemplateRemarkColor;
	
	/**
	 * 模板详细信息链接地址
	 */
	private String messageTemplateUrl;
	
	/**
	 * 模板消息关键字
	 */
	private String messageTemplateKeyword;
	
	
	

	public int getMessageTemplateId() {
		return messageTemplateId;
	}

	public void setMessageTemplateId(int messageTemplateId) {
		this.messageTemplateId = messageTemplateId;
	}

	public int getMessageTemplateStatus() {
		return messageTemplateStatus;
	}

	public void setMessageTemplateStatus(int messageTemplateStatus) {
		this.messageTemplateStatus = messageTemplateStatus;
	}

	public int getMessageTemplateAppId() {
		return messageTemplateAppId;
	}

	public void setMessageTemplateAppId(int messageTemplateAppId) {
		this.messageTemplateAppId = messageTemplateAppId;
	}

	public int getMessageTemplateModelId() {
		return messageTemplateModelId;
	}

	public void setMessageTemplateModelId(int messageTemplateModelId) {
		this.messageTemplateModelId = messageTemplateModelId;
	}

	public String getMessageTemplateTitle() {
		return messageTemplateTitle;
	}

	public void setMessageTemplateTitle(String messageTemplateTitle) {
		this.messageTemplateTitle = messageTemplateTitle;
	}

	public String getMessageTemplateTitleColor() {
		return messageTemplateTitleColor;
	}

	public void setMessageTemplateTitleColor(String messageTemplateTitleColor) {
		this.messageTemplateTitleColor = messageTemplateTitleColor;
	}

	public String getMessageTemplateRemark() {
		return messageTemplateRemark;
	}

	public void setMessageTemplateRemark(String messageTemplateRemark) {
		this.messageTemplateRemark = messageTemplateRemark;
	}

	public String getMessageTemplateRemarkColor() {
		return messageTemplateRemarkColor;
	}

	public void setMessageTemplateRemarkColor(String messageTemplateRemarkColor) {
		this.messageTemplateRemarkColor = messageTemplateRemarkColor;
	}

	public String getMessageTemplateUrl() {
		return messageTemplateUrl;
	}

	public void setMessageTemplateUrl(String messageTemplateUrl) {
		this.messageTemplateUrl = messageTemplateUrl;
	}

	public String getMessageTemplateTemplateId() {
		return messageTemplateTemplateId;
	}

	public void setMessageTemplateTemplateId(String messageTemplateTemplateId) {
		this.messageTemplateTemplateId = messageTemplateTemplateId;
	}

	public int getMessageTemplateTopcolor() {
		return messageTemplateTopcolor;
	}

	public void setMessageTemplateTopcolor(int messageTemplateTopcolor) {
		this.messageTemplateTopcolor = messageTemplateTopcolor;
	}

	public String getMessageTemplateKeyword() {
		return messageTemplateKeyword;
	}

	public void setMessageTemplateKeyword(String messageTemplateKeyword) {
		this.messageTemplateKeyword = messageTemplateKeyword;
	}


	public int getMessageTemplateWeixinId() {
		return messageTemplateWeixinId;
	}

	public void setMessageTemplateWeixinId(int messageTemplateWeixinId) {
		this.messageTemplateWeixinId = messageTemplateWeixinId;
	}
	
}
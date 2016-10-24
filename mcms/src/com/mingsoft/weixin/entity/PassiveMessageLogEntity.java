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

import java.util.Date;

import com.mingsoft.base.entity.BaseEntity;

/**
 * 铭飞科技-微信
 * Copyright: Copyright (c) 2014 - 2015
 * @author 成卫雄   QQ:330216230
 * Comments:被动回复消息日志
 * Create Date:2014-10-12
 * Modification history:
 */
public class PassiveMessageLogEntity extends BaseEntity{
 
	/**
	 * 自增长ID
	 */
	private int passiveMessageLogId;
	
	/**
	 * 关联日志所属应用ID
	 */
	private int passiveMessageLogAppId;
	
	/**
	 * 关联微信自增长ID
	 */
	private int passiveMessageLogWeixinId;
	
	/**
	 * 关联用户Id
	 */
	private int passiveMessageLogPeopleId;
	
	/**
	 * 关联被动消息回复Id
	 */
	private int passiveMessageId;
	
	/**
	 * 该回复属于分类中的事件ID
	 */
	private int passiveMessageLogEventId;
	
	/**
	 * 日志生成时间
	 */
	private Date passiveMessageLogTime = new Date();
	
	/**
	 * 回复关键字,不支持正则
	 */
	private String passiveMessageLogKey;
	
	public int getPassiveMessageLogWeixinId() {
		return passiveMessageLogWeixinId;
	}

	public void setPassiveMessageLogWeixinId(int passiveMessageLogWeixinId) {
		this.passiveMessageLogWeixinId = passiveMessageLogWeixinId;
	}

	/**
	 * 获取passiveMessageLogKey
	 * @return  passiveMessageLogKey
	 */
	public String getPassiveMessageLogKey() {
		return passiveMessageLogKey;
	}

	/**
	 * 设置passiveMessageLogKey
	 * @param passiveMessageLogKey
	 */
	public void setPassiveMessageLogKey(String passiveMessageLogKey) {
		this.passiveMessageLogKey = passiveMessageLogKey;
	}

	/**
	 * 获取passiveMessageLogId
	 * @return  passiveMessageLogId
	 */
	public int getPassiveMessageLogId() {
		return passiveMessageLogId;
	}

	/**
	 * 设置passiveMessageLogId
	 * @param passiveMessageLogId
	 */
	public void setPassiveMessageLogId(int passiveMessageLogId) {
		this.passiveMessageLogId = passiveMessageLogId;
	}

	/**
	 * 获取passiveMessageLogAppId
	 * @return  passiveMessageLogAppId
	 */
	public int getPassiveMessageLogAppId() {
		return passiveMessageLogAppId;
	}

	/**
	 * 设置passiveMessageLogAppId
	 * @param passiveMessageLogAppId
	 */
	public void setPassiveMessageLogAppId(int passiveMessageLogAppId) {
		this.passiveMessageLogAppId = passiveMessageLogAppId;
	}

	/**
	 * 获取passiveMessageLogPeopleId
	 * @return  passiveMessageLogPeopleId
	 */
	public int getPassiveMessageLogPeopleId() {
		return passiveMessageLogPeopleId;
	}

	/**
	 * 设置passiveMessageLogPeopleId
	 * @param passiveMessageLogPeopleId
	 */
	public void setPassiveMessageLogPeopleId(int passiveMessageLogPeopleId) {
		this.passiveMessageLogPeopleId = passiveMessageLogPeopleId;
	}

	/**
	 * 获取passiveMessageId
	 * @return  passiveMessageId
	 */
	public int getPassiveMessageId() {
		return passiveMessageId;
	}

	/**
	 * 设置passiveMessageId
	 * @param passiveMessageId
	 */
	public void setPassiveMessageId(int passiveMessageId) {
		this.passiveMessageId = passiveMessageId;
	}

	/**
	 * 获取passiveMessageLogEventId
	 * @return  passiveMessageLogEventId
	 */
	public int getPassiveMessageLogEventId() {
		return passiveMessageLogEventId;
	}

	/**
	 * 设置passiveMessageLogEventId
	 * @param passiveMessageLogEventId
	 */
	public void setPassiveMessageLogEventId(int passiveMessageLogEventId) {
		this.passiveMessageLogEventId = passiveMessageLogEventId;
	}

	/**
	 * 获取passiveMessageLogTime
	 * @return  passiveMessageLogTime
	 */
	public Date getPassiveMessageLogTime() {
		return passiveMessageLogTime;
	}

	/**
	 * 设置passiveMessageLogTime
	 * @param passiveMessageLogTime
	 */
	public void setPassiveMessageLogTime(Date passiveMessageLogTime) {
		this.passiveMessageLogTime = passiveMessageLogTime;
	}

}
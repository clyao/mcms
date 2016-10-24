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
import com.mingsoft.weixin.constant.e.PassiveMessageEventEnum;
import com.mingsoft.weixin.constant.e.PassiveMessageTypeEnum;

/**
 * 铭飞科技-微信
 * Copyright: Copyright (c) 2014 - 2015
 * @author 成卫雄   QQ:330216230
 * Comments:被动消息回复
 * 每个关键字只能有一个最终回复类型
 * Create Date:2014-10-12
 * Modification history:
 */
public class PassiveMessageEntity extends BaseEntity {

	/**
	 * 自增长ID
	 */
	private int passiveMessageId;
	
	/**
	 * 被动消息回复关联的素材
	 */
	private NewsEntity newsEntity;
	
	/**
	 * 关联应用ID
	 */
	private int passiveMessageAppId;
	
	/**
	 * 关联配置文件中的事件值
	 * 参考:PassiveMessageEventEnum
	 * @see PassiveMessageEventEnum
	 */
	private int passiveMessageEventId;
	
	/**
	 * 事件关键字.支持SQL正则表达式
	 */
	private String passiveMessageKey;
	
	/**
	 * 关联素材ID
	 */
	private int passiveMessageNewsId;
	
	/**
	 * 微信自定义模板消息
	 */
	private int passiveMessageMessageId;
	
	/**
	 * 检测是否是模板消息。回复消息时优先模板消息
	 * @return 
	 */
	public boolean isMessageTemplage() {
		return passiveMessageMessageId>0;
	}

	/**
	 * 被动回复的次数</br>
	 * 为1时表示用户第一次被动响应消息</br>
	 * 依次递增,当超出时取值为属性为最终回复的进行回复</br>
	 * 每个关键字只能有一个最终回复类型</br>
	 */
	private int passiveMessageRelyNum;

	/**
	 * 扩展标签，目前只是神扫功能需要，例如用户扫了一个连接，那么将返回图文消息并图文消息的连接就是用户扫的连接
	 */
	private String passiveMessageTag;
	
	/**
	 * 回复属性:</br>
	 * 	1.最终回复;达到迭代次数之后的回复消息,</br>
	 * 	2.拓展回复迭代回复(优先迭代回复)</br>
	 *  @see PassiveMessageTypeEnum
	 */
	private int passiveMessageType;
	
	
	/**
	 * 关联微信自增长Id
	 */
	private int passiveMessageWeixinId;
	
	
	public int getPassiveMessageWeixinId() {
		return passiveMessageWeixinId;
	}

	public void setPassiveMessageWeixinId(int passiveMessageWeixinId) {
		this.passiveMessageWeixinId = passiveMessageWeixinId;
	}

	/**
	 * 获取newsEntity
	 * @return  newsEntity
	 */
	public NewsEntity getNewsEntity() {
		return newsEntity;
	}

	/**
	 * 获取passiveMessageAppId
	 * @return  passiveMessageAppId
	 */
	public int getPassiveMessageAppId() {
		return passiveMessageAppId;
	}

	/**
	 * 获取passiveMessageEventId
	 * @return  passiveMessageEventId
	 */
	public int getPassiveMessageEventId() {
		return passiveMessageEventId;
	}

	/**
	 * 获取passiveMessageId
	 * @return  passiveMessageId
	 */
	public int getPassiveMessageId() {
		return passiveMessageId;
	}

	/**
	 * 获取passiveMessageKey
	 * @return  passiveMessageKey
	 */
	public String getPassiveMessageKey() {
		return passiveMessageKey;
	}

	/**
	 * 获取passiveMessageNewsId
	 * @return  passiveMessageNewsId
	 */
	public int getPassiveMessageNewsId() {
		return passiveMessageNewsId;
	}

	/**
	 * 获取passiveMessageRelyNum
	 * @return  passiveMessageRelyNum
	 */
	public int getPassiveMessageRelyNum() {
		return passiveMessageRelyNum;
	}

	public String getPassiveMessageTag() {
		return passiveMessageTag;
	}

	/**
	 * 获取passiveMessageType
	 * @return  passiveMessageType
	 */
	public int getPassiveMessageType() {
		return passiveMessageType;
	}

	/**
	 * 设置newsEntity
	 * @param newsEntity
	 */
	public void setNewsEntity(NewsEntity newsEntity) {
		this.newsEntity = newsEntity;
	}

	/**
	 * 设置passiveMessageAppId
	 * @param passiveMessageAppId
	 */
	public void setPassiveMessageAppId(int passiveMessageAppId) {
		this.passiveMessageAppId = passiveMessageAppId;
	}

	/**
	 * 设置passiveMessageEventId
	 * @param passiveMessageEventId
	 * @deprecated
	 */
	public void setPassiveMessageEventId(int passiveMessageEventId) {
		this.passiveMessageEventId = passiveMessageEventId;
	}
	
	/**
	 *设置消息事件类型
	 * @param event 
	 * @see PassiveMessageEventEnum
	 */
	public void setPassiveMessageEvent(PassiveMessageEventEnum event) {
		this.passiveMessageEventId = event.toInt();
	}

	/**
	 * 设置passiveMessageId
	 * @param passiveMessageId
	 */
	public void setPassiveMessageId(int passiveMessageId) {
		this.passiveMessageId = passiveMessageId;
	}

	/**
	 * 设置passiveMessageKey
	 * @param passiveMessageKey
	 */
	public void setPassiveMessageKey(String passiveMessageKey) {
		this.passiveMessageKey = passiveMessageKey;
	}


	/**
	 * 设置passiveMessageNewsId
	 * @param passiveMessageNewsId
	 */
	public void setPassiveMessageNewsId(int passiveMessageNewsId) {
		this.passiveMessageNewsId = passiveMessageNewsId;
	}

	/**
	 * 设置passiveMessageRelyNum
	 * @param passiveMessageRelyNum
	 */
	public void setPassiveMessageRelyNum(int passiveMessageRelyNum) {
		this.passiveMessageRelyNum = passiveMessageRelyNum;
	}

	public void setPassiveMessageTag(String passiveMessageTag) {
		this.passiveMessageTag = passiveMessageTag;
	}

	/**
	 * 设置passiveMessageType
	 * @param passiveMessageType
	 * @deprecated
	 */
	public void setPassiveMessageType(int passiveMessageType) {
		this.passiveMessageType = passiveMessageType;
	}
	
	/**
	 * 设置passiveMessageType
	 * @param passiveMessageType
	 */
	public void setPassiveMessageType(PassiveMessageTypeEnum passiveMessageType) {
		this.passiveMessageType = passiveMessageType.toInt();
	}
	
	public int getPassiveMessageMessageId() {
		return passiveMessageMessageId;
	}

	public void setPassiveMessageMessageId(int passiveMessageMessageId) {
		this.passiveMessageMessageId = passiveMessageMessageId;
	}	
	
}
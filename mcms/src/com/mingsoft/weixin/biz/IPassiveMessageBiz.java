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

package com.mingsoft.weixin.biz;

import java.util.List;
import com.mingsoft.base.biz.IBaseBiz;
import com.mingsoft.util.PageUtil;
import com.mingsoft.weixin.constant.e.PassiveMessageEventEnum;
import com.mingsoft.weixin.entity.PassiveMessageEntity;

/**
 * 铭飞科技-微信
 * Copyright: Copyright (c) 2014 - 2015
 * @author 成卫雄   QQ:330216230
 * Create Date:2014-10-12
 * Modification history:
 */
public interface IPassiveMessageBiz extends IBaseBiz{
	
	/**
	 * 查询事件回复消息,</br>
	 * 若超出回复次数则取属性类别为最终回复的进行回复</br>
	 * @param appId 应用编号
	 * @param weixinId 微信编号
	 * @param eventId 关联的事件ID
	 * @param passiveMessageKey 关键字,当不存在关键字时传入null
	 * @param passiveMessageReplyNum 回复次数
	 * @return 查询到的实体类
	 */
	public PassiveMessageEntity getEntityBySendMessage(int appId,int weixinId,int eventId,String passiveMessageKey,int passiveMessageReplyNum);
	
	
	/**
	 * 根据事件编号、应用编号、关键字查询被动消息回复集合
	 * @param event PassiveMessageEventEnum类型，事件编号,不存在时传入0
	 * @param appId 应用编号
	 * @param weixinId 微信编号
	 * @param key 关键字,当不存在关键字时传入null
	 * @param page 分页条件不进行分页时,直接传入null即可
	 * @return 被动消息回复集合
	 */
	public List<PassiveMessageEntity> queryListByEvent(PassiveMessageEventEnum event,int appId,int weixinId,String key,PageUtil page);

	/**
	 * 根据素材ID和微信ID查找被动回复关联素材是否存在
	 * @param passiveMessageNewsId 被动回复关联的素材ID
	 * @param weixinId 微信ID
	 * @return 与素材关联的被动回复列表
	 */
	public List<PassiveMessageEntity> queryListByNewsIdAndWeixinId(int passiveMessageNewsId,int weixinId);
	
	
	/**
	 * 保存关注回复内容
	 * @param passiveMessageEntity 被动回复实体
	 * @param replyType 回复类型
	 * @param content 回复内容
	 */
	public void saveSubscribePassiveMessage(PassiveMessageEntity passiveMessageEntity,int replyType,String content);
	
	/**
	 * 保存关键字回复内容	 
	 * @param passiveMessageEntity 被动回复实体
	 * @param replyType 回复类型
	 * @param content 回复内容
	 */
	public void saveKeyPassiveMessage(PassiveMessageEntity passiveMessageEntity,int replyType,String content);
	

	/**
	 * 查询该微信下的相关被动回复总数
	 * @param event 被动回复事件编号
	 * @param appId 应用编号
	 * @param weixinId 微信编号
	 * @param key 关键字
	 * @return 相关被动回复事件总数
	 */
	public int queryCountByCustom(int event,int appId,int weixinId,String key);
	
	/**
	 * 根据ID集合批量删除被动回复
	 * @param ids 需要删除的被动消息ID集合
	 */
	public void deleteByIds(int[] ids);

	/**
	 * 更新关注回复
	 * @param passiveMessageId 被动回复ID
	 * @param passiveMessageEntity 被动回复实体
	 * @param replyType 素材类型
	 * @param content 关注回复内容
	 */
	public void updateSubscribePassiveMessage(int passiveMessageId, PassiveMessageEntity passiveMessageEntity, int replyType, String content);

	/**
	 * 更新关键字回复
	 * @param passiveMessageId 被动回复ID
	 * @param PassiveMessageEntity 被动回复实体
	 * @param replyType 被动回复类型
	 * @param content 关键字回复内容
	 */
	public void updateKeyPassiveMessage(int passiveMessageId,PassiveMessageEntity passiveMessageEntity,int replyType, String content);

}
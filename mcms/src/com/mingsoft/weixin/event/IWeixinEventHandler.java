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

package com.mingsoft.weixin.event;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.mingsoft.util.StringUtil;
import com.mingsoft.weixin.biz.IMessageTemplateBiz;
import com.mingsoft.weixin.biz.INewsBiz;
import com.mingsoft.weixin.biz.IPassiveMessageBiz;
import com.mingsoft.weixin.biz.IPassiveMessageLogBiz;
import com.mingsoft.weixin.biz.IWeixinBiz;
import com.mingsoft.weixin.biz.IWeixinPeopleBiz;
import com.mingsoft.weixin.constant.QQFace;
import com.mingsoft.weixin.constant.e.NewsTypeEnum;
import com.mingsoft.weixin.constant.e.PassiveMessageEventEnum;
import com.mingsoft.weixin.entity.MessageTemplateEntity;
import com.mingsoft.weixin.entity.NewsEntity;
import com.mingsoft.weixin.entity.PassiveMessageEntity;
import com.mingsoft.weixin.entity.PassiveMessageLogEntity;
import com.mingsoft.weixin.entity.WeixinEntity;
import com.mingsoft.weixin.entity.WeixinPeopleEntity;
import com.mingsoft.weixin.util.MessageTemplateUtils;
import com.mingsoft.weixin.util.UserUtils;
import com.mingsoft.weixin.util.XmlUtils;

/**
 * >铭飞科技流量推广软件
 * Copyright: Copyright (c) 2013 - 2015
 * Company:景德镇铭飞科技有限公司
 * @author killfen
 * @version 100-000-000
 * 版权所有
 * Comments:处理微信接口抽象类
 * Create Date:2014-3-11
 * Modification history:暂无
 */
public abstract class IWeixinEventHandler implements IWeixinEventType {

	public Logger logger = Logger.getLogger(this.getClass());

	/**
	 * 微信所有者编号(不唯一,在微信系统中做唯一查询时请谨慎使用)
	 */
	protected int appId;

	/**
	 * 微信在系统中的唯一编号
	 */
	protected int weixinId;
	
	/**
	 * 原始微信信息
	 */
	protected String originalWeixinMsg;

	/**
	 * 消息类型
	 */
	protected String msgType = null;

	/**
	 * 事件类型
	 */
	protected String event = null;

	/**
	 * 用户openId编号
	 */
	protected String fromUserName = null;

	/**
	 * 微信所有者编号
	 */
	protected String toUserName = null;

	/**
	 * 返回参数:content:返回给微信的格式，详细参考微信的开发api,type:true成功 false:失败
	 */
	protected Map<String, Object> returnMap = null;

	/**
	 * 事件KEY值，是一个32位无符号整数，即创建二维码时的二维码scene_id
	 */
	protected String eventKey = null;

	/**
	 * 二维码扫描的ticket值
	 */
	protected String ticket = null;

	/**
	 * 普通文本的内容
	 */
	protected String msgContent;

	/**
	 * 图片链接地址
	 */
	protected String msgPicUrl;

	/**
	 * 注入素材业务层
	 */
	@Autowired
	protected INewsBiz newsBiz;

	/**
	 * 注入微信基本信息业务层
	 */
	@Autowired
	protected IWeixinBiz weixinBiz;

	/**
	 * 注入微信用户业务层
	 */
	@Autowired
	protected IWeixinPeopleBiz weixinPeopleBiz;

	/**
	 * 注入被动响应消息业务层
	 */
	@Autowired
	protected IPassiveMessageBiz passiveMessageBiz;

	/**
	 * 注入被动响应消息日志业务层
	 */
	@Autowired
	protected IPassiveMessageLogBiz passiveMessageLogBiz;
	
	
	/**
	 * 注入被动响应消息日志业务层
	 */
	@Autowired
	protected IMessageTemplateBiz messageTemplateBiz;
	

	/**
	 * 微信处理接口
	 * 
	 * @param params
	 * <br/>
	 *            weixin:微信平台返回的请求数据 <br/>
	 *            hotelId:酒店id
	 * @return map <br/>
	 *         content:返回内容<br/>
	 *         type:true成功 false:失败<br/>
	 *         如果不匹配就返回null
	 */
	public abstract Map<String, Object> execute(Map<String, Object> params);

	public IWeixinEventHandler() {

	}


	/**
	 * 被动消息回复
	 * @param passiveMessageKey 关键字
	 * @param event 事件类型
	 * @return 消息数据详细见微信开发手册
	 */
	protected List<String> sendPassiveMessage(String passiveMessageKey, String event) {
//		// 根据用户openId查询用户Id
//		WeixinPeopleEntity weixinPeople = this.weixinPeopleBiz.getEntityByOpenId(this.fromUserName, this.appId);
		//根据openId和微信唯一ID查询用户
		WeixinPeopleEntity weixinPeople = this.weixinPeopleBiz.getEntityByOpenIdAndWeixinId(this.fromUserName, this.weixinId);
		
		if (weixinPeople == null) {
			logger.debug("-----IWeixinEventHandler: weixinpeopleEntity is null(未查询到用户信息)");
			return null;
		}

		// 用户Id
		int peopleId = weixinPeople.getPeopleId();
		logger.debug("发送被动消息");
		// 事件Id
		int eventId = PassiveMessageEventEnum.getIdByWeixinEventKey(event);

		// 查询当前用户在该事件下被动消息回复的次数
		int peopleMessageNum = this.passiveMessageLogBiz.getCountBySendMessage(this.weixinId,peopleId, eventId, this.appId, passiveMessageKey);

		// 查询回复素材ID
		PassiveMessageEntity passiveMessage = this.passiveMessageBiz.getEntityBySendMessage(this.appId,this.weixinId, eventId, passiveMessageKey, peopleMessageNum);

		if (passiveMessage == null) {
			logger.debug("-----IWeixinEventHandler: passive is null(未查询到被动响应消息)");
			return null;
		}
		
		//判读是否是微信自定义消息模板
		if(passiveMessage.isMessageTemplage()) {
//			WeixinEntity weixin = this.weixinBiz.getEntityByAppId(this.appId);
			WeixinEntity weixin = this.weixinBiz.getEntityById(this.weixinId);
			MessageTemplateEntity mte = (MessageTemplateEntity) messageTemplateBiz.getEntity(passiveMessage.getPassiveMessageMessageId());
			new MessageTemplateUtils(weixin.getWeixinAppID(),weixin.getWeixinAppSecret()).sendToUser(this.fromUserName, mte.getMessageTemplateTemplateId(),mte.getMessageTemplateUrl(), "", mte.getMessageTemplateTitle(), mte.getMessageTemplateTitleColor(), mte.getMessageTemplateRemark(),  mte.getMessageTemplateRemarkColor(), mte.getMessageTemplateKeyword().split("\\|"));
			return null;
		}

		// 根据微信所有者ID和消息回复类型查询用户设置的关注回复消息
		List<String> returnNews = null;
		returnNews = new ArrayList<String>();
		// 获取素材Id
		int newsId = passiveMessage.getPassiveMessageNewsId();
		// 根据素材Id查询回复素材的类型
		NewsEntity news = this.newsBiz.getNewsByNewsId(newsId);
		String messageStr = "";

		logger.debug("重定向地址？:" + passiveMessage.getPassiveMessageTag() + ":" + passiveMessageKey);
		if (!StringUtil.isBlank(passiveMessage.getPassiveMessageTag())) {
			news.setNewsLink(passiveMessageKey);

		}
		logger.debug("NewsLink:" + news.getNewsLink());
		if (news.getNewsType() == NewsTypeEnum.SINGLE_NEWS.toInt()) {// 回复的单图文消息
			messageStr = XmlUtils.buildXmlNews(this.fromUserName, this.toUserName, news);
		} else if (news.getNewsType() == NewsTypeEnum.NEWS.toInt()) {// 回复的多图文消息
			messageStr = XmlUtils.buildXmlNews(this.fromUserName, this.toUserName, news);
		} else if (news.getNewsType() == NewsTypeEnum.TEXT.toInt()) {// 回复文本消息
			messageStr = XmlUtils.buildXmlText(this.fromUserName, this.toUserName, news.getNewsContent());
		} else if (news.getNewsType() == NewsTypeEnum.IMAGE.toInt()) {// 回复图片
			// 暂时不支持

		}

		logger.debug("--被动消息回复xml信息:--" + messageStr);
		returnNews.add(messageStr);

		// 持久化用户日志
		PassiveMessageLogEntity passiveMessageLog = new PassiveMessageLogEntity();
		passiveMessageLog.setPassiveMessageLogAppId(this.appId);
		passiveMessageLog.setPassiveMessageLogWeixinId(this.weixinId);
		passiveMessageLog.setPassiveMessageLogEventId(eventId);
		passiveMessageLog.setPassiveMessageLogPeopleId(peopleId);
		passiveMessageLog.setPassiveMessageId(passiveMessage.getPassiveMessageId());
		passiveMessageLog.setPassiveMessageLogKey(passiveMessageKey);
		this.passiveMessageLogBiz.saveEntity(passiveMessageLog);

		return returnNews;
	}

	/**
	 * 更新关注用户</br> 当数据库中不存在该用户信息时则新增</br> 当数据库存在该用户信息则更新该用户关注状态</br>
	 * 注:和sendPassiveMessagePeople方法同时使用时需要优先执行该方法</br>
	 * 
	 * @param weixinPeople
	 *            用户实体
	 * @return 用户在数据库中的ID
	 */
	protected int updatePeople() {
		WeixinPeopleEntity weixinPeople = new WeixinPeopleEntity();

		// 查询该微信号的appId和appSecret
//		WeixinEntity weixin = this.weixinBiz.getEntityByAppId(this.appId);
		WeixinEntity weixin = this.weixinBiz.getEntityById(this.weixinId);
		weixinPeople.setWeixinPeopleOpenId(this.fromUserName);
		weixinPeople.setWeixinPeopleAppId(this.appId);
		weixinPeople.setWeixinPeopleWeixinId(this.weixinId);

		UserUtils userUtil = new UserUtils(weixin.getWeixinAppID(), weixin.getWeixinAppSecret());
		Map<String, Object> userInfoMap = userUtil.syncUserInfo(this.fromUserName);
		
		// 构造用户基本信息
		if (userInfoMap != null) {
			weixinPeople.setPeopleUserSex(Integer.parseInt(userInfoMap.get("sex").toString()));
			weixinPeople.setWeixinPeopleCity(userInfoMap.get("city").toString());
			weixinPeople.setWeixinPeopleHeadimgUrl(userInfoMap.get("headimgurl").toString());
			weixinPeople.setPeopleUserNickName(userInfoMap.get("nickname").toString());
			weixinPeople.setWeixinPeopleProvince(userInfoMap.get("province").toString());
			weixinPeople.setPeopleAppId(this.appId);
			weixinPeople.setPeopleUserAppId(this.appId);
		} else {
			logger.error("获取用户基础信息失败!");
		}
		
		// 查询数据库中是否存在该用户
//		WeixinPeopleEntity weixinPeopleEntity = this.weixinPeopleBiz.getEntityByOpenId(weixinPeople.getWeixinPeopleOpenId(), weixinPeople.getWeixinPeopleAppId());
		WeixinPeopleEntity weixinPeopleEntity = this.weixinPeopleBiz.getEntityByOpenIdAndAppIdAndWeixinId(this.fromUserName,this.appId,this.weixinId);
		
		if (weixinPeopleEntity == null) {// 当不存在该用户时执行添加
			weixinPeople.setPeopleDateTime(new Date());
			this.weixinPeopleBiz.savePeopleUser(weixinPeople);
		} else {
			// 判断用户的关注状态,如果为取消关注状态则只更新状态如果为授权关注状态则全部更新
			if (weixinPeopleEntity.getWeixinPeopleState() == WeixinPeopleEntity.WEIXIN_PEOPLE_OAUTH_WATCH) {
				weixinPeople.setPeopleId(weixinPeopleEntity.getPeopleId());
				weixinPeopleEntity = weixinPeople;
			}
			this.weixinPeopleBiz.updatePeopleUser(weixinPeopleEntity);
			weixinPeopleEntity.setWeixinPeopleState(WeixinPeopleEntity.WEIXIN_PEOPLE_WATCH);
			this.weixinPeopleBiz.updatePeopleUser(weixinPeopleEntity);
		}
		int peopleId = weixinPeople.getPeopleId();
		logger.debug("-----最新关注用户id:" + peopleId);
		return peopleId;
	}

	/**
	 * 加载默认数据，在责任链中提供基础数据支持
	 * @param params
	 */
	public void init(Map<String, Object> params) {
		returnMap = new HashMap<String, Object>();
		//原始微信信息
		if (!StringUtil.isBlank(params.get("originalWeixinMsg"))) {
			originalWeixinMsg = params.get("originalWeixinMsg").toString();
		}
		
		//微信所属应用ID
		if (!StringUtil.isBlank(params.get("appId"))) {
			this.appId = Integer.parseInt(params.get("appId").toString());
		}
		
		//微信自增长ID
		if(!StringUtil.isBlank(params.get("weixinId"))){
			this.weixinId = Integer.parseInt(params.get("weixinId").toString());
		}
		
		//消息类型
		msgType = XmlUtils.getString(originalWeixinMsg, XML_MSG_TYPE_NODE);
		//时间类型
		event = XmlUtils.getString(originalWeixinMsg, XML_EVENT_NODE);
		
		if (event != null) {
			//将字符串中的字母全部转换为小写
			event = event.toLowerCase();
		}
		
		toUserName = XmlUtils.getString(originalWeixinMsg, XML_TO_USER_NAME);
		fromUserName = XmlUtils.getString(originalWeixinMsg, XML_FROM_USER_NAME);
		eventKey = XmlUtils.getString(originalWeixinMsg, XML_EVENTKEY);
		ticket = XmlUtils.getString(originalWeixinMsg, XML_TICKET);
	}
	
	/**
	 * 在内容中添加表情
	 * @param content 内容
	 * @param basePath 路径
	 * @return 新内容
	 */
	protected String inputFaceChain(String content, String basePath) {
		int index = 0;
		for (String f : QQFace.FACE) {
			content = content.replace(f, "<img src='" + basePath + "/images/qqface/" + index + ".png'/>");
			index++;
		}
		return content;
	}

}
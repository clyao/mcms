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

package com.mingsoft.weixin.event.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.mingsoft.cms.biz.IArticleBiz;
import com.mingsoft.util.StringUtil;
import com.mingsoft.weixin.event.IWeixinEventHandler;
import com.mingsoft.weixin.util.XmlUtils;

/**
 * 铭飞科技流量推广软件
 * Copyright: Copyright (c) 2013 - 2015
 * Company:景德镇铭飞科技有限公司
 * @author killfen
 * @version 100-000-000
 * 版权所有
 * Comments:用户点击微信菜单事件
 * Create Date:2014-3-11
 * Modification history:暂无
 */
public class MenuEventHandler extends IWeixinEventHandler {

	/**
	 * 注入文章业务层
	 */
	@Autowired
	protected IArticleBiz articleBiz;


	public Map<String, Object> execute(Map<String, Object> params) {
		// TODO Auto-generated method stub
		init(params);

		if (msgType != null && msgType.equals(EVENT) && menuEventType(event)) {
			logger.debug("用户点击菜单EVENT值" + event);
			menuSendMessage();
			return returnMap;
		}
		return null;
	}

	/**
	 * 判断菜单事件类型
	 * 
	 * @param menuEvent
	 *            传入的事件类型
	 * @return true:成功
	 */
	private Boolean menuEventType(String menuEvent) {
		for (int i = 0; i < MENU_EVENT_TYPE.length; i++) {
			if (menuEvent.equalsIgnoreCase(MENU_EVENT_TYPE[i].toString())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 通过判断底部菜单的事件来确定该事件是否会触发被动回复</br> 如果触发则通过事件和事件中的关键字查询素材进行回复</br>
	 */
	private void menuSendMessage() {
		String key = null;
		// 通过对事件的判断来确定取出关键字的方法
		if (this.event.equalsIgnoreCase(MENU_EVEN_SCANCODE_WAITMSG)) {// 底部菜单扫码推事件,既通过解析获取用户扫描的参数
			key = XmlUtils.getString(originalWeixinMsg, XML_SCANCODEINFO, XML_SCANRESULT);
			
			//通过key来判断用户扫描的是那种类型数据:一切有条形码与二维码都可以
			if (key.indexOf(",") > 0 && StringUtil.isExpressNo(key.split(",")[1])) {
				
				returnMap.put("content", this.sendPassiveMessage(key.split(",")[1], this.event));
				returnMap.put("type", true);
				return;
			}
			
//			// 查询文章中是否存在该关键字
//			List<Map> listArticle = this.articleBiz.queryListByArticleTitle(key, this.appId, ModelCode.EXPRESS_NO);
//			// 当文章唯一时实现订单绑定,关键字为文章分类Id。当绑定失败后不改变关键字
//			if (listArticle.size() == 1) {
// 
//				Map article = listArticle.get(0); 
//				if (Integer.valueOf(article.get("basicHit").toString())==1) { //如果该订单已经被人使用，临时使用basicHit替代
//					//此处应该回复已绑定提示,暂时不做处理
//					logger.debug("该快递单已经使用");
//					key="expressIsUse";
//				} else {
//					key = article.get("basicCategoryId").toString();
//					logger.debug("文章分类ID：" + key);
//					// 根据用户openId查询用户Id
//					WeixinPeopleEntity weixinPeople = this.weixinPeopleBiz.getEntityByOpenId(this.fromUserName, this.appId);
//					if (weixinPeople != null) {
//						// 用户Id
//						int peopleId = weixinPeople.getPeopleId();
//		
//						// 更具分类ID和用户ID查询用户订单 
//						List peopleOrder = this.orderBiz.queryByPeopleId(this.appId, (int) article.get("modelId"),(int) article.get("basicCategoryId"), peopleId, null);
//						this.goodsBiz.save(peopleOrder, (String) article.get("basicTitle"), Integer.valueOf(article.get("basicId").toString())); 
//
//					} else {
//						logger.debug("-----IWeixinEventHandler:未查询到该用户信息");
//					}
//				}
//			}
			
			
		} else if (this.event.equalsIgnoreCase(MENU_EVEN_CLICK)) {// 点击底部菜单推事件
			String eventKey = XmlUtils.getString(originalWeixinMsg, XML_EVENTKEY);
			key = eventKey.split("\\|")[0];
		}

		// 构建回调map
		if (!StringUtil.isBlank(key)) {
			logger.debug("调用菜单key值" + key);
			returnMap.put("content", this.sendPassiveMessage(key, this.event));
			returnMap.put("type", true);
		} else {
			returnMap.put("type", false);
		}
	}

}
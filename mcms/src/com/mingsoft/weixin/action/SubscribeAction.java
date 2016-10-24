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

package com.mingsoft.weixin.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mingsoft.basic.constant.Const;
import com.mingsoft.basic.constant.e.CookieConstEnum;
import com.mingsoft.util.StringUtil;
import com.mingsoft.weixin.biz.IPassiveMessageBiz;
import com.mingsoft.weixin.constant.ModelCode;
import com.mingsoft.weixin.constant.e.PassiveMessageEventEnum;
import com.mingsoft.weixin.constant.e.PassiveMessageTypeEnum;
import com.mingsoft.weixin.entity.PassiveMessageEntity;
import com.mingsoft.weixin.entity.WeixinEntity;

/**
 * 用户关注控制层
 * Copyright: Copyright (c) 2014 - 2015
 * @author 成卫雄  QQ:330216230
 * Comments:用户关注控制层
 * Create Date:2014-11-21
 * Modification history:
 */
@Controller
@RequestMapping("/${managerPath}/weixin/subscribe/")
public class SubscribeAction extends BaseAction{

	/**
	 * 注入微信被动消息回复业务层
	 */
	@Autowired
	private IPassiveMessageBiz passiveMessageBiz;
	
	/**
	 * 关注回复消息
	 * @param model
	 * @param request
	 * @return 关注回复页面
	 */
	@RequestMapping("/subscribe")
	public String subscribe(ModelMap model,HttpServletResponse response,HttpServletRequest request){
		int appId = this.getAppId(request);
		//取出微信实体,得到微信Id
		WeixinEntity weixin = this.getWeixinSession(request);
		//获取微信ID
		int weixinId = weixin.getWeixinId();
		//取出当前自动回复的消息
		List<PassiveMessageEntity> list = passiveMessageBiz.queryListByEvent(PassiveMessageEventEnum.SUBSCRIBE,appId,weixinId,null,null);
		if (list!=null && list.size()>0) {
			PassiveMessageEntity passiveMessage = list.get(0);
			model.addAttribute("passiveMessage", passiveMessage);
			model.addAttribute("news", passiveMessage.getNewsEntity());
		}
		model.addAttribute("appId", appId);
		return Const.VIEW+"/weixin/subscribe/subscribe_form";
	}
	
	
	/**
	 * 保存关注回复内容
	 * @param response
	 * @param request
	 */
	@RequestMapping("/save")
	@ResponseBody
	public void save(HttpServletResponse response,HttpServletRequest request){	
		//获取应用ID
		int appId = this.getAppId(request);
		//获取微信
		WeixinEntity weixin = this.getWeixinSession(request);
		//若微信不存在
		if(weixin == null || weixin.getWeixinId()<=0){
			this.outJson(response, ModelCode.WEIXIN_MESSAGE, false, this.getResString("weixin.not.found"));
			return;
		}
		//获取微信ID
		int weixinId = weixin.getWeixinId();
		//获取关注回复内容，若为图文，content为素材ID，将在biz的实现类里将其转化为int型
		String content = request.getParameter("content");
		if(StringUtil.isBlank(content)){
			this.outJson(response, null, false);
			return;	
		}
		String replyTypeStr = request.getParameter("replyType");
		//判断回复是否为空且其是否为数字
		if(!StringUtil.isInteger(replyTypeStr)){
			this.outJson(response, null, false);
			return;
		}
		//将回复类型转为int
		int replyTypeInt = Integer.valueOf(replyTypeStr);	
		//新建关注回复实体
		PassiveMessageEntity passiveMessageEntity = new PassiveMessageEntity();
		passiveMessageEntity.setPassiveMessageEvent(PassiveMessageEventEnum.SUBSCRIBE);//事件类型：关注回复：
		passiveMessageEntity.setPassiveMessageType(PassiveMessageTypeEnum.FINAL);//回复类型：最终回复
		passiveMessageEntity.setPassiveMessageAppId(appId);//应用编号
		passiveMessageEntity.setPassiveMessageWeixinId(weixinId);//微信编号
		//保存关注回复
		passiveMessageBiz.saveSubscribePassiveMessage(passiveMessageEntity,replyTypeInt,content);
		this.outJson(response, ModelCode.WEIXIN_NEWS, true,null);
	}
	
	
	/**
	 * 更新关注回复的内容
	 * @param passiveMessageId 被动回复Id
	 * @param response
	 * @param request
	 */
	@RequestMapping("/{passiveMessageId}/update")
	@ResponseBody
	public void update(@PathVariable int passiveMessageId,HttpServletResponse response,HttpServletRequest request){
		//如果自增长ID小于0
		if(passiveMessageId<=0){
			this.outJson(response, null, false);
			return;	
		}
		WeixinEntity weixin = this.getWeixinSession(request);
		//若微信不存在
		if(weixin == null|| weixin.getWeixinId()<=0){
			this.outJson(response, ModelCode.WEIXIN_MESSAGE, false, this.getResString("weixin.not.found"));
			return;
		}
		//获取微信id
		int weixinId = weixin.getWeixinId();
		//获取appId
		int appId = this.getAppId(request);
		//获取关注回复内容，若为图文，content为素材ID，将在biz的实现类里将其转化为int型
		String content = request.getParameter("content");
		if(StringUtil.isBlank(content)){
			this.outJson(response, null, false);
			return;	
		}
		//获取回复类型
		String replyTypeStr = request.getParameter("replyType");
		//关注回复的内容类型（图片，图文，文本）
		if(!StringUtil.isInteger(replyTypeStr)){
			this.outJson(response, null, false);
			return;
		}
		//转回复类型string为int
		int replyTypeInt = Integer.valueOf(replyTypeStr);
		//新建关注回复实体
		PassiveMessageEntity passiveMessageEntity = new PassiveMessageEntity();
		passiveMessageEntity.setPassiveMessageEvent(PassiveMessageEventEnum.SUBSCRIBE);//事件类型：关注回复：
		passiveMessageEntity.setPassiveMessageType(PassiveMessageTypeEnum.FINAL);//回复类型：最终回复
		passiveMessageEntity.setPassiveMessageAppId(appId);//应用编号
		passiveMessageEntity.setPassiveMessageWeixinId(weixinId);//微信编号
		//更新关注回复
		this.passiveMessageBiz.updateSubscribePassiveMessage(passiveMessageId,passiveMessageEntity,replyTypeInt, content);
		//读取更新后的跳转地址
		String url = this.getCookie(request, CookieConstEnum.BACK_COOKIE);
		this.outJson(response,ModelCode.WEIXIN_NEWS, true,null,url);
	}
}
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
import com.mingsoft.util.PageUtil;
import com.mingsoft.util.StringUtil;
import com.mingsoft.weixin.biz.INewsBiz;
import com.mingsoft.weixin.biz.IPassiveMessageBiz;
import com.mingsoft.weixin.constant.ModelCode;
import com.mingsoft.weixin.constant.e.PassiveMessageEventEnum;
import com.mingsoft.weixin.constant.e.PassiveMessageTypeEnum;
import com.mingsoft.weixin.entity.NewsEntity;
import com.mingsoft.weixin.entity.PassiveMessageEntity;
import com.mingsoft.weixin.entity.WeixinEntity;

/** 
 * 关键字回复控制层
 * @author  付琛  QQ:1658879747 
 * @version 1.0 
 * 创建时间：2015年11月15日 下午2:08:42  
 * 版本号：100-000-000<br/>
 * 历史修订<br/>
 */
@Controller
@RequestMapping("/${managerPath}/weixin/messagekey/")
public class MessageKeyAction extends BaseAction{
	
	/**
	 * 注入被动回复业务层
	 */
	@Autowired
	private IPassiveMessageBiz passiveMessageBiz;

	/**
	 * 注入素材业务层
	 */
	@Autowired
	private INewsBiz newsBiz;
	
	/**
	 * 保存关键字回复内容
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
		//若微信或者微信相关数据不存在
		if(weixin == null || weixin.getWeixinId()<=0){
			this.outJson(response, ModelCode.WEIXIN_MESSAGE, false, this.getResString("weixin.not.found"));
			return;
		}
		//获取微信ID
		int weixinId = weixin.getWeixinId();
		//获取回复类型
		String replyTypeStr = request.getParameter("replyType");
		//关键字回复的内容类型（图片，图文，文本）
		if(!StringUtil.isInteger(replyTypeStr)){
			this.outJson(response, null, false);
			return;
		}
		//转回复类型string为int
		int replyTypeInt = Integer.valueOf(replyTypeStr);
		//接收关键字回复内容，若为图文，其为素材ID，将在biz的实现类将其转化为Int
		String content = request.getParameter("content");
		if(StringUtil.isBlank(content)){
			this.outJson(response, ModelCode.WEIXIN_NEWS, false,null);
			return;
		}
		//获取关键字
		String passiveMessageKey = request.getParameter("passiveMessageKey");
		if(StringUtil.isBlank(passiveMessageKey)){
			this.outJson(response, null, false);
			return;
		}
		//若存在相同的关键字回复
		PassiveMessageEntity passiveMessage = passiveMessageBiz.getEntityBySendMessage(appId, weixinId, PassiveMessageEventEnum.TEXT.toInt(),passiveMessageKey, 0);
		if(passiveMessage !=null){
			this.outJson(response, null, false);
			return;
		}
		//新增关键字回复实体
		PassiveMessageEntity passiveMessageEntity = new PassiveMessageEntity();
		passiveMessageEntity.setPassiveMessageKey(passiveMessageKey);//保存关键字
		passiveMessageEntity.setPassiveMessageEvent(PassiveMessageEventEnum.TEXT);//被动回复事件类型：文本
		passiveMessageEntity.setPassiveMessageType(PassiveMessageTypeEnum.FINAL);//回复类型：最终回复
		passiveMessageEntity.setPassiveMessageAppId(appId);//应用编号
		passiveMessageEntity.setPassiveMessageWeixinId(weixinId);//微信编号
		//保存关键字回复
		passiveMessageBiz.saveKeyPassiveMessage(passiveMessageEntity,replyTypeInt, content);
		this.outJson(response, ModelCode.WEIXIN_NEWS, true,null);
	}
	
	/**
	 * 新增关键字回复
	 * @param mode
	 * @return 新增关键字回复管理页面
	 */
	@RequestMapping("/add")
	public String add(ModelMap mode){
		PassiveMessageEntity passiveMessage = new PassiveMessageEntity();
		mode.addAttribute("passiveMessage", passiveMessage);
		return Const.VIEW+"/weixin/messagekey/messagekey_form";
	}
	
	/**
	 * 取出关键字回复Id，根据其Id查找对应的素材
	 * @param passiveMessageId 关键字回复Id
	 * @param mode
	 * @return 编辑关键字回复页面
	 */
	@RequestMapping("{passiveMessageId}/edit")
	public String edit(@PathVariable int passiveMessageId,ModelMap mode){
		if(passiveMessageId > 0){
			//获取当前关键字回复实体
			PassiveMessageEntity passiveMessage = (PassiveMessageEntity) this.passiveMessageBiz.getEntity(passiveMessageId);
			//获取相关素材实体
			NewsEntity news = (NewsEntity) this.newsBiz.getNewsByNewsId(passiveMessage.getPassiveMessageNewsId());
			mode.addAttribute("news", news);
			mode.addAttribute("passiveMessage", passiveMessage);
		}
		return Const.VIEW+"/weixin/messagekey/messagekey_form";
	}
	
	
	/**
	 * 更新关键字回复
	 * @param passiveMessageId 关键字回复自增长ID
	 * @param response
	 * @param request
	 */
	@RequestMapping("/{passiveMessageId}/update")
	@ResponseBody
	public void update(@PathVariable int passiveMessageId,HttpServletResponse response,HttpServletRequest request){
		if(passiveMessageId<=0){
			this.outJson(response, null, false);
			return;
		}
		//获取应用ID
		int appId = this.getAppId(request);
		//获取微信
		WeixinEntity weixin = this.getWeixinSession(request);
		//若微信或者微信相关数据不存在
		if(weixin == null || weixin.getWeixinId()<=0){
			this.outJson(response, ModelCode.WEIXIN_MESSAGE, false, this.getResString("weixin.not.found"));
			return;
		}
		//获取微信ID
		int weixinId = weixin.getWeixinId();
		//获取回复类型
		String replyTypeStr = request.getParameter("replyType");
		//关键字回复的内容类型（图片，图文，文本）
		if(!StringUtil.isInteger(replyTypeStr)){
			this.outJson(response, null, false);
			return;
		}
		//转回复类型string为int
		int replyTypeInt = Integer.valueOf(replyTypeStr);
		//获取关键字
		String passiveMessageKey = request.getParameter("passiveMessageKey");
		if(StringUtil.isBlank(passiveMessageKey)){
			this.outJson(response, null, false);
			return;
		}
		//接收关键字回复内容，若为图文，其为素材ID，将在biz的实现类将其转化为Int
		String content = request.getParameter("content");
		if(StringUtil.isBlank(content) ){
			this.outJson(response, null, false);
			return;
		}
		//新增关键字回复实体
		PassiveMessageEntity passiveMessageEntity = new PassiveMessageEntity();
		passiveMessageEntity.setPassiveMessageKey(passiveMessageKey);//关键词
		passiveMessageEntity.setPassiveMessageEvent(PassiveMessageEventEnum.TEXT);//被动回复事件类型：文本
		passiveMessageEntity.setPassiveMessageType(PassiveMessageTypeEnum.FINAL);//回复类型：最终回复
		passiveMessageEntity.setPassiveMessageAppId(appId);//应用编号
		passiveMessageEntity.setPassiveMessageWeixinId(weixinId);//微信编号
		//更新关键字回复
		this.passiveMessageBiz.updateKeyPassiveMessage(passiveMessageId,passiveMessageEntity,replyTypeInt, content);
		//读取更新后返回的页面地址
		String url = this.getCookie(request, CookieConstEnum.BACK_COOKIE);
		this.outJson(response,null, true,null,url);
	}
	
	/**
	 * 批量删除关键字回复回复内容
	 * @param response
	 * @param request
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public void delete(HttpServletResponse response,HttpServletRequest request){		
		String[] keyMessageIds = request.getParameterValues("keyMessageIds");
		//判断数组是否为空
		if(StringUtil.isBlank(keyMessageIds)){
			this.outJson(response, null, false);
			return;
		}
		//判断字符串数组是否转成integer型数组
		if(!StringUtil.isIntegers(keyMessageIds)){
			this.outJson(response, null, false);
			return;
		}
		//得到ID数组并将字符串数组转化为int型数组
		int[] ids = StringUtil.stringsToInts(keyMessageIds);
		//根据ID批量删除
		passiveMessageBiz.deleteByIds(ids);
		//返回json数据
		this.outJson(response, null, true);
	}
	
	
	/**
	 * 关键字回复列表
	 * @param response
	 * @param request
	 * @param mode
	 * @return 关键字回复列表
	 */
	@RequestMapping("/list")
	public String list(HttpServletResponse response,HttpServletRequest request,ModelMap mode){
		//查询当前页码
		int pageNo = this.getPageNo(request);
		//查询应用ID
		int appId = this.getAppId(request);
		//取出微信实体,得到微信Id
		WeixinEntity weixin = this.getWeixinSession(request);
		//获取微信ID
		int weixinId = weixin.getWeixinId();
		//查询记录总数
		int recordCount = this.passiveMessageBiz.queryCountByCustom(PassiveMessageEventEnum.TEXT.toInt(),appId,weixinId, null);
		//分页查询
		PageUtil page=new PageUtil(pageNo,recordCount,"list.do?");
		//根据被动回复关键字查询列表
		List<PassiveMessageEntity> messageKeyList = this.passiveMessageBiz.queryListByEvent(PassiveMessageEventEnum.TEXT,appId,weixinId,null,page);
		//压入url地址
		this.setCookie(request, response, CookieConstEnum.BACK_COOKIE,"list.do?&pageNo="+pageNo);
		mode.addAttribute("messageKeyList",messageKeyList);
		mode.addAttribute("page",page);
		return Const.VIEW+"/weixin/messagekey/messagekey_list";
	}
}
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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.mingsoft.basic.constant.Const;
import com.mingsoft.util.StringUtil;
import com.mingsoft.weixin.biz.IMessageTemplateBiz;
import com.mingsoft.weixin.entity.MessageTemplateEntity;

/**
 * 
 * Copyright: Copyright (c) 2014 - 2015
 * Company:景德镇铭飞科技有限公司
 * @author  yangxy
 * @version 140-000-000
 * 版权所有 铭飞科技
 * @ClassName: MessageTemplateAction
 * @Description: 微信模板消息控制层
 * Comments:  
 * Creatr Date:2015-5-14
 * Modification history:暂无/manager/index.do
 */
@Controller
@RequestMapping("/${managerPath}/weixin/messageTemplate")
public class MessageTemplateAction extends BaseAction{
	/**
	 * 注入biz
	 */
	@Autowired
	private IMessageTemplateBiz messageTemplateBiz;
	
	/**
	 * 微信的信息发送的列表
	 * @param request
	 * @param response
	 * @return 微信短信列表
	 */
	@RequestMapping("/list")
	public String list(HttpServletRequest request,HttpServletResponse response){
		//全部微信短信的列表并返给页面
		List<MessageTemplateEntity> list = messageTemplateBiz.queryAllMessages();
		request.setAttribute("messageTemplateList", list);
		return view("/weixin/messagetemplate/messagetemplate_list");
	}
	
	
	/**
	 * 通过页面上checkbox框得到的微信模板id 得到微信模板实体
	 * @param messageTemplateId  微信模板id
	 * @param request
	 * @param response
	 */
	@RequestMapping("/getEntity")
	@ResponseBody
	public void getEntity(String messageTemplateId,HttpServletRequest request,HttpServletResponse response){
		if(!StringUtil.isInteger(messageTemplateId)){
			this.outJson(response, null,false);
			return;
		}
		int templateId = Integer.parseInt(messageTemplateId);
		//根据页面传来的微信短信ID查询单条实体
		MessageTemplateEntity messageTemplateEntity = (MessageTemplateEntity) messageTemplateBiz.getEntity(templateId);
		if(messageTemplateEntity == null){
			this.outJson(response, null,false);
			return;
		}		
		this.outJson(response,null,true,JSONObject.toJSONString(messageTemplateEntity));
	}
	
	
	/**
	 * 保存微信短信实体
	 * @param messageTemplateEntity 页面传入序列化实体
	 * @param request
	 * @param response
	 */
	@RequestMapping("/save")
	@ResponseBody
	public void save(@ModelAttribute MessageTemplateEntity messageTemplateEntity,HttpServletRequest request, HttpServletResponse response){
		//保存微信短信实体全部为必填项，判断传入实体中的值是否为空或者不合法
		if(
			messageTemplateEntity.getMessageTemplateStatus()<=0  //消息状态
			||StringUtil.isBlank(messageTemplateEntity.getMessageTemplateKeyword())//模板消息关键字
			||StringUtil.isBlank(messageTemplateEntity.getMessageTemplateRemark())//模板消息摘要
			||StringUtil.isBlank(messageTemplateEntity.getMessageTemplateRemarkColor())//模板消息摘要显示颜色
			||StringUtil.isBlank(messageTemplateEntity.getMessageTemplateTemplateId())//模板消息id
			||StringUtil.isBlank(messageTemplateEntity.getMessageTemplateTitle())//模板消息标题
			||StringUtil.isBlank(messageTemplateEntity.getMessageTemplateTitleColor())//模板消息标题显示颜色
			||StringUtil.isBlank(messageTemplateEntity.getMessageTemplateTopcolor())//模板标题颜色
			||StringUtil.isBlank(messageTemplateEntity.getMessageTemplateUrl())//模板详细信息链接地址
		){
			this.outJson(response,null,false);
			return;
		}
		messageTemplateEntity.setMessageTemplateAppId(this.getAppId(request));//保存appId
		//保存实体
		messageTemplateBiz.saveEntity(messageTemplateEntity);
		this.outJson(response,null,true);
	}
	
	/**
	 * 根据checkbox传来的id进行批量删除
	 * @param request
	 * @param response
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public void delete(HttpServletRequest request, HttpServletResponse response){
		String[] ids = request.getParameterValues("radioCategoryId");
		if (!StringUtil.isDoubles(ids)) {
			this.outJson(response,null,false,this.getResString("err"));	
			return ;
		}
		//删除多条评论
		messageTemplateBiz.deleteAll(ids);
		//返回json数据，结束
		this.outJson(response, null, true,"",this.redirectBack(request,false));
	}
	
	/**
	 * 更新方法
	 * @param messageTemplateEntity 页面传入序列化实体
	 * @param messageTemplateId  AJAX提供的微信id
	 * @param request
	 * @param response
	 */
	@RequestMapping("/{messageTemplateId}/update")
	public void update(@ModelAttribute MessageTemplateEntity messageTemplateEntity,@PathVariable int messageTemplateId,HttpServletRequest request, HttpServletResponse response){
		if(messageTemplateEntity == null || messageTemplateId<=0){
			this.outJson(response,null,false);
			return;
		}
		messageTemplateEntity.setMessageTemplateId(messageTemplateId);//模板消息ID
		//更新模板消息
		messageTemplateBiz.updateEntity(messageTemplateEntity);
		this.outJson(response,null,true);
	}
}
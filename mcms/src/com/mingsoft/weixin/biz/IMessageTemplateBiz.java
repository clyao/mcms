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
import com.mingsoft.weixin.entity.MessageTemplateEntity;

/**
 * 铭飞科技
 * Copyright: Copyright (c) 2014 - 2015
 * @author yangxy
 * Comments: 微信自定义消息模板
 * Create Date:2015-5-22
 * Modification history:
 */
public interface IMessageTemplateBiz extends IBaseBiz{
	
	/**
	 * 根据应用编号与模块编号获取自定义消息模板
	 * @param weixinId 微信编号
	 * @param modelId 模块编号
	 * @return 模板消息实体
	 */
	MessageTemplateEntity getByWeixinIdAndModelId(int weixinId,int modelId);
	
	/**
	 * 查询微信模板消息列表
	 * @return 实体列表
	 */
	List<MessageTemplateEntity> queryAllMessages();
	
	/**
	 * 多选删除
	 * @param ids 前端传来的勾选的checkbox勾选的id序列化
	 */
	void deleteAll(String[] ids);
}
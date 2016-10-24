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

package com.mingsoft.weixin.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.mingsoft.base.dao.IBaseDao;
import com.mingsoft.weixin.entity.MessageTemplateEntity;

/**
 * 铭飞科技
 * Copyright: Copyright (c) 2014 - 2015
 * @author yangxy
 * Comments: 微信自定义消息模板
 * Create Date:2015-4-1
 * Modification history:
 */
public interface IMessageTemplateDao extends IBaseDao{
	/**
	 * 通过微信id和模块id查询微信短信实体
	 * @param weixinId  微信ID
	 * @param modelId  模块id
	 * @return MessageTemplateEntity实体
	 */
	MessageTemplateEntity getByWeixinIdAndModelId(@Param("weixinId")int weixinId,@Param("messageTemplateModelId")int modelId);

	/**
	 * 查询所有，返回集合
	 * @return  实体集合
	 */
	List<MessageTemplateEntity> queryAllMessages();
	
	/**
	 * 批量删除短信
	 * @param ids  需要删除的模板信息ID数组
	 */
	void deleteAll(@Param("ids")String[] ids);
}
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
import org.apache.ibatis.annotations.Param;

import com.mingsoft.base.dao.IBaseDao;

/**
 * 铭飞科技-微信
 * Copyright: Copyright (c) 2014 - 2015
 * @author 成卫雄  QQ:330216230
 * Comments:被动关注回复日志持久化层接口
 * Create Date:2014-10-12
 * Modification history:
 */
public interface IPassiveMessageLogDao extends IBaseDao{
	
	/**
	 * 根据自定义字段查询日志数量
	 * @param appId 应用编号
	 * @param weixinId 微信自增长ID
	 * @param passiveMessageLogPeopleId 用户ID
	 * @param passiveMessageLogKey 关键字
	 * @param passiveMessageLogEventId 事件类型
	 * @return
	 */
	public int getCountByCustom(@Param("appId")Integer appId,@Param("weixinId")Integer weixinId,@Param("passiveMessageLogPeopleId")Integer passiveMessageLogPeopleId,@Param("passiveMessageLogKey")String passiveMessageLogKey,@Param("passiveMessageLogEventId")Integer passiveMessageLogEventId);
	
}
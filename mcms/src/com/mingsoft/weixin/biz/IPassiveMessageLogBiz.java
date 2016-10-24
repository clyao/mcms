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

import com.mingsoft.base.biz.IBaseBiz;

/**
 * 
 * 被动消息回复日志业务层接口
 * @author 成卫雄(qq:330216230)
 * @version 
 * 版本号：100-000-000<br/>
 * 创建日期：2015年8月26日 上午9:37:51<br/>
 * 历史修订：<br/>
 */
public interface IPassiveMessageLogBiz extends IBaseBiz{

	/**
	 * 根据用户ID,事件ID,应用ID</br>
	 * 查询该用户在当前事件下响应消息的条数
	 * @param weixinId 微信Id
	 * @param peopleId 用户ID
	 * @param eventId 事件ID
	 * @param appId 应用ID
	 * @param key 回复关键字
	 * @return 日志条数
	 */
	public int getCountBySendMessage(int weixinId,int peopleId,int eventId,int appId,String key);
	
}
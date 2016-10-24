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

import com.mingsoft.weixin.event.IWeixinEventHandler;

/**
 * 
 * 已关注用户扫描带参数的二维码
 * @author 成卫雄(qq:330216230)
 * @version 
 * 版本号：100-000-000<br/>
 * 创建日期：2015年8月26日 下午7:16:20<br/>
 * 历史修订：<br/>
 */
public class QrcodeSubscribeEventHandler extends IWeixinEventHandler{

	@Override
	public Map<String, Object> execute(Map<String, Object> params) {
		// TODO Auto-generated method stub
		init(params);
		
		//判断事件类型
		if(msgType != null && msgType.equals(EVENT) && event.equals(SUBSCRIBED_SCAN) && ticket!=null){
			logger.debug("已关注用户,带参数二维码事件,二维码中的值:"+eventKey);
			returnMap.put("content",this.sendPassiveMessage(eventKey,SUBSCRIBED_SCAN));
			returnMap.put("type", true);
			return returnMap;			
		}
		return null;
	}
	
}
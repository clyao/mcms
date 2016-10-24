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
 * 铭飞科技-微信
 * Copyright: Copyright (c) 2013 - 2015
 * Company:景德镇铭飞科技有限公司
 * @author 成卫雄
 * @version 100-000-000
 * 版权所有
 * Comments:未关注用户扫面带参数二维码
 * Create Date:2014-3-14
 * Modification history:判断ticket不为空成立
 */
public class QrcodeNoSubscribeEventHandler extends IWeixinEventHandler {
	
	@Override
	public Map<String, Object> execute(Map<String, Object> params) {
		// TODO Auto-generated method stub
		init(params);
		//当用户没有关注的时候event为subscribe,而在eventKey里面会有qrscene_场景值
		if(msgType != null && msgType.equals(EVENT) && event.equals(EVENT_SUBSCRIBE) && ticket!=null) {
			this.updatePeople();//持久化新关注用户信息
			eventKey = eventKey.split("_")[1];
			logger.debug("未关注用户扫描带参数的二维码值：" + eventKey);
			returnMap.put("content",this.sendPassiveMessage(eventKey, EVENT_SUBSCRIBE+"_"+SUBSCRIBED_SCAN));
			returnMap.put("type",true);
			return returnMap;
		}
		return null;
	}

}
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
 * 铭飞科技流量推广软件
 * Copyright: Copyright (c) 2013 - 2015
 * Company:景德镇铭飞科技有限公司
 * @author killfen
 * @version 100-000-000
 * 版权所有
 * Comments:用户关注</br>
 * 通过点击关注</br>
 * 扫描关注二维码关注</br>
 * 搜索帐号关注</br>
 * Create Date:2014-3-11
 * Modification history:暂无
 */
public class SubscribeEventHandler extends IWeixinEventHandler {

	public Map<String, Object> execute(Map<String, Object> params) {
		// TODO Auto-generated method stub
		init(params);
		if ( msgType != null && msgType.equals(EVENT) && event.equals(EVENT_SUBSCRIBE) && eventKey.indexOf("qrscene_")<0) {
			this.updatePeople();
			returnMap.put("content",this.sendPassiveMessage(null,EVENT_SUBSCRIBE));
			returnMap.put("type", true);
			return returnMap;
		}
		return null;
	}
	
}
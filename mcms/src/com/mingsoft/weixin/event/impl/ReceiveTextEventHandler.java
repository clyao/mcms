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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.mingsoft.weixin.event.IWeixinEventHandler;
import com.mingsoft.weixin.util.XmlUtils;

/**
 * 铭飞科技流量推广软件
 * Copyright: Copyright (c) 2013 - 2015
 * Company:景德镇铭飞科技有限公司
 * @author killfen
 * @version 100-000-000
 * 版权所有
 * Comments:接收文本处理
 * Create Date:2014-3-11
 * Modification history:暂无
 */
public class ReceiveTextEventHandler extends IWeixinEventHandler {
	
	public Map<String, Object> execute(Map<String, Object> params) {
		init(params);
		if (null != msgType && msgType.equals(MSGTYPE_TEXT)) {
			String content = XmlUtils.getString(originalWeixinMsg, XML_MSG_CONTENT);
			logger.debug("--执行"+MSGTYPE_TEXT+"事件--接受到的文本信息:"+content);
			returnMap.put("content", getContent(content));
			returnMap.put("type", true);
			return returnMap;
		} 
		return null;
	}
	
	/**
	 * 获取返回消息，如果存在关键字就直接返回关键字绑定消息
	 * @param content　原始内容
	 * @return　
	 */
	private  List<String> getContent(String content) {
		List list = this.sendPassiveMessage(content,MSGTYPE_TEXT);
		if(list!=null) { 
			return list;
		} else {
			list = new ArrayList();
			list.add(XmlUtils.buildDuoKeFu(this.fromUserName,this.toUserName));
		}
			
		return list;
	}
}
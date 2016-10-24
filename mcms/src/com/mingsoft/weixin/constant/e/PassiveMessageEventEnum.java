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

package com.mingsoft.weixin.constant.e;

import com.mingsoft.base.constant.e.BaseEnum;

/**
 * 铭飞科技
 * Copyright: Copyright (c) 2014 - 2015
 * @author killfen
 * Comments:微信EVENT事件编号
 * Create Date:2015-2-26
 * Modification history:
 */
public enum PassiveMessageEventEnum implements BaseEnum {
	/**
	 * 用户关注事件
	 */
	SUBSCRIBE(1, "新关注", "subscribe"),
	/**
	 * 已关注用户扫描带参数二维码事件
	 */
	SUBSCRIBE_SCAN(2, "二维码扫描", "scan"),
	/**
	 * 未关注用户扫描二维码
	 */
	NO_SUBSCRIBE_SCAN(5, "未关注＆二维码扫描", "subscribe_scan"),
	/**
	 * 点击推事件
	 */
	CLICK(6, "点击事件", "click"),
	/**
	 * 用户文本消息关键字回复
	 */
	TEXT(4, "文本消息", "text"),
	/**
	 * 扫码推事件且弹出“消息接收中”提示框
	 */
	SCANCODE_WAITMSG(3, "二维码扫描&提示框", "scancode_waitmsg");

	/**
	 * 
	 * @param id 平台自定义编号
	 * @param code　平台自定义文本
	 * @param weixinEventKey 微信平台定义的event值，参考微信官方平台开发文档
	 */
	PassiveMessageEventEnum(int id, Object code, String weixinEventKey) {
		this.id = id;
		this.code = code;
		this.weixinEventKey = weixinEventKey;
	}

	private int id;
	private Object code;
	private String weixinEventKey;

	@Override
	public int toInt() {
		// TODO Auto-generated method stub
		return id;
	}

	public String toString() {
		return code.toString();
	}
	
	public String getWeixinEventKey() {
		return weixinEventKey;
	}
	
	public static int getIdByWeixinEventKey(String eventkey) {
		PassiveMessageEventEnum[] pmee = PassiveMessageEventEnum.values();
		for (PassiveMessageEventEnum _pmee:pmee) {
			if(_pmee.getWeixinEventKey().equals(eventkey)) {
				return _pmee.toInt();
			}
		}		
		return 0;
	}

}
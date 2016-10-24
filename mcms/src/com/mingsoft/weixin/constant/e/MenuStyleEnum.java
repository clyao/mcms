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
 * @author 王天培 QQ:78750478
 * Comments:点击菜单后给用户推送内容的类型
 * Create Date:2015-1-19
 * Modification history:
 * * 1、文本</br> 2、图文</br> 3、语音</br> 4、外链</br>  5、应用商城</br> 6、关键词触发</br>
 */
public enum MenuStyleEnum implements BaseEnum {
	/**
	 *文本
	 */
	TEXT(1), 
	/**
	 * 图文
	 */
	PIC_ARTICLE(2), 
	/**
	 *语音
	 */
	VOICE(3), 
	/**
	 * 外链
	 */
	LINK(4),
	/**
	 * 应用商城
	 */
	APPLICATION_SHOP(5), 
	/**
	 * 关键词触发
	 */
	KEYWORD(6);
	
	MenuStyleEnum(Object code) {
		this.code = code;
	}

	private Object code;

	@Override
	public int toInt() {
		// TODO Auto-generated method stub
		return Integer.valueOf(code + "");
	}

}
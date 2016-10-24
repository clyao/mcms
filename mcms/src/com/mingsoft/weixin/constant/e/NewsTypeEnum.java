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
 * @author 史爱华
 * Comments:消息回复类型
 * Create Date:2015-1-19
 * Modification history:
 * *0、单图文 </br>  1、多图文</br> 2、文本</br> 3、图片</br>
 */
public enum NewsTypeEnum implements BaseEnum {
	/**
	 *单图文
	 */
	SINGLE_NEWS(0,"单图文"), 
	/**
	 * 多图文
	 */
	NEWS(1,"多图文"), 
	/**
	 *文本
	 */
	TEXT(2,"文本"), 
	/**
	 * 图片
	 */
	IMAGE(3,"图片");
	NewsTypeEnum(int id,Object code) {
		this.id = id;
		this.code = code;
	}

	private Object code;
	
	private int id;

	@Override
	public int toInt() {
		// TODO Auto-generated method stub
		return id;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return code.toString();
	}

}
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
 * Comments:回复消息类型枚举
 * Create Date:2015-2-26
 * Modification history:
 * </p>
 */
public enum PassiveMessageTypeEnum implements BaseEnum{
	/**
	 * 回复类型</br>
	 * 最终回复</br>
	 * 每个关键字只能有一个最终回复类型</br>
	 */
	FINAL(1,"最终回复"),
	/**
	 * 回复类型</br>
	 * 拓展回复类型</br>
	 */
	ADD (2,"拓展回复"),
	/**
	 * 回复模版消息</br>
	 */
	MODEL(3,"回复模版");
	
	PassiveMessageTypeEnum(int id,Object code) {
		this.id = id;
		this.code = code;
	}

	private int id;
	private Object code;
	@Override
	public int toInt() {
		// TODO Auto-generated method stub
		return id;
	}
	public String toString() {
		return code.toString();
	}

}
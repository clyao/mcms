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
 * @author 王天培  QQ:78750478
 * Comments:微支付方式枚举类
 * Create Date:2015-1-20
 * Modification history:
 */
public enum WeixinPayEnum implements BaseEnum{
	JSAPI("JSAPI"),
	NATIVE("NATIVE"),
	APP("APP");
	WeixinPayEnum(Object code) {
		this.code = code;
	}
	
	private Object code ;
	@Override
	public int toInt() {
		// TODO Auto-generated method stub
		return 0;
	}
	public String toString() {
		// TODO Auto-generated method stub
		return code.toString();
	}
}
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
 * 
 * 类的描述  微信类型的枚举类
 * @author fuchen
 * @version 
 * 项目名：ms-mweixin
 * 创建日期：:2015年10月8日
 * 历史修订：2015年10月8日
 */
public enum WeixinTypeEnum implements BaseEnum {
	
		/**
		 * 服务号
		 */
		WEIXIN_SERVIECE(0,"服务号"), 
		/**
		 * 订阅号
		 */
		WEIXIN_Subscribe(1,"订阅号"), 
		/**
		 * 微信开发平台
		 */
		WEIXIN_OPEN(2,"微信开放平台");
		
		WeixinTypeEnum(int id,Object code) {
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
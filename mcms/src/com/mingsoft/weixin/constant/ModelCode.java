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

package com.mingsoft.weixin.constant;

import com.mingsoft.base.constant.e.BaseEnum;

/**
 * 模块编号<br/>
 * 模块编号说明：八位整型数据<br/>
 *          项目编号(2位)+模块编号(2位)+功能编号(2位)+子功能编号(2位)<br/>
 *          如：01(微信项目编号)01(微页面模块编号)01(模版管理编号)01(添加模版)<br/>
 *          若为：01010100则代表整个模块管理功能模块<br/>
 * @author 王天培QQ:78750478
 * @version 
 * 版本号：100-000-000<br/>
 * 创建日期：2012-03-15<br/>
 * 历史修订：<br/>
 */
public enum ModelCode implements BaseEnum{

	
	/**
	 * 微信图文素材
	 */
	WEIXIN_NEWS("05030100"),

	/**
	 * 微信图片素材
	 */
	WEIXIN_NEWS_IMAGE("05030200"),
	
	/**
	 * 微信文本素材
	 */
	WEIXIN_NEWS_TEXT("05030300"),
	
	/**
	 * 微信消息模块
	 */
	WEIXIN_MESSAGE("05050000"),
	
	/**
	 * 微信网页2.0授权管理
	 */
	WEIXIN_OAUTH("05070000");
	

	
	/**
	 * 设置modelCode的常量
	 * @param code 常量
	 */
	ModelCode(String code) {
		this.code = code;
	}

	private String code;

	/**
	 * 返回该modelCode常量的字符串表示
	 * @return 字符串
	 */
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return code;
	}

	/**
	 * 返回该modelCode常量的整型表示
	 * @return 整型
	 */
	public int toInt() {
		// TODO Auto-generated method stub
		return Integer.parseInt(code);
	}
}
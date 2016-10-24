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
 * 上传素材是否显示封面 0显示 1不显示
 * @author  付琛  QQ:1658879747 
 * @version 1.0 
 * 创建时间：2015年11月22日 下午4:31:21  
 * 版本号：100-000-000<br/>
 * 历史修订<br/>
 */
public enum UploadNewsBeanCoverPicEnum implements BaseEnum{
	/**
	 * 不显示封面
	 */
	HIDE_COVER_PIC(0,"不显示封面"),
	
	/**
	 * 显示封面
	 */
	SHOW_COVER_PIC(1,"显示封面");
	
	UploadNewsBeanCoverPicEnum(int id,Object code){
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
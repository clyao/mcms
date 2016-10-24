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

package com.mingsoft.weixin.biz;

import java.util.List;


import com.mingsoft.base.biz.IBaseBiz;
import com.mingsoft.util.PageUtil;
import com.mingsoft.weixin.entity.OauthEntity;

/**
 * 铭飞科技-微信
 * Copyright: Copyright (c) 2014 - 2015
 * @author 成卫雄   QQ:330216230
 * Comments:微信网页2.0授权业务层接口
 * Create Date:2014-10-7
 * Modification history:
 */
public interface IOauthBiz extends IBaseBiz{
	
	
	/**
	 * 根据微信ID查找列表
	 * @param weixinId 微信ID
	 * @param page 分页
	 * @return 授权集合
	 */
	public List<OauthEntity> queryList(int appId,int weixinId,PageUtil page);
	
	
	/**
	 * 批量删除
	 * @param ids 需要删除的授权id数组
	 */
	public void deleteByIds(int[] ids);
	
	/**
	 * 根据应用ID和微信ID查找总数
	 * @param appId 应用ID
	 * @param weixinId 微信ID
	 * @return 授权总数
	 */
	public int queryCount(int appId,int weixinId);
	
}
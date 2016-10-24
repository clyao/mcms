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

package com.mingsoft.weixin.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.mingsoft.base.dao.IBaseDao;
import com.mingsoft.util.PageUtil;
import com.mingsoft.weixin.entity.WeixinQrcodeEntity;

/** 
 * 微信二维码持久层接口
 * @author  付琛  QQ:1658879747 
 * @version 1.0 
 * 创建时间：2015年11月13日 下午3:57:20  
 * 版本号：100-000-000<br/>
 * 历史修订<br/>
 */
public interface IWeixinQrcodeDao extends IBaseDao{

	/**
	 * 根据应用Id和微信ID查询二维码总数
	 * @param appId 应用ID
	 * @param weixinId 微信ID
	 * @return 二维码总数
	 */
	public int queryCount(@Param("appId")int appId,@Param("weixinId")int weixinId);
	
	/**
	 * 批量删除
	 * @param ids 二维码id集合
	 */
	public void deleteByIds(@Param("ids")int[] ids);
	
	/**
	 * 根据应用ID和微信ID分页查询
	 * @param appId 应用ID
	 * @param weixinId 微信ID
	 * @param page 分页参数
	 * @return 二维码集合
	 */
	public List<WeixinQrcodeEntity> queryList(@Param("appId")int appId,@Param("weixinId")int weixinId,@Param("page")PageUtil page);
}
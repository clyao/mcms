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

package com.mingsoft.weixin.biz.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mingsoft.base.biz.impl.BaseBizImpl;
import com.mingsoft.base.dao.IBaseDao;
import com.mingsoft.util.PageUtil;
import com.mingsoft.weixin.biz.IWeixinQrcodeBiz;
import com.mingsoft.weixin.dao.IWeixinQrcodeDao;
import com.mingsoft.weixin.entity.WeixinQrcodeEntity;
/** 
 * 微信二维码实现类
 * @author  付琛  QQ:1658879747 
 * @version 1.0 
 * 创建时间：2015年11月13日 下午3:59:47  
 * 版本号：100-000-000<br/>
 * 历史修订<br/>
 */
@Service("weixinQrcodeBiz")
public class WeixinQrcodeBizImpl extends BaseBizImpl implements IWeixinQrcodeBiz  {
	/**
	 * 注入微信二维码持久层接口
	 */
	@Autowired
	private IWeixinQrcodeDao weixinQrcodeDao;
	
	@Override
	protected IBaseDao getDao() {
		// TODO Auto-generated method stub
		return weixinQrcodeDao;
	}
	
	/**
	 * 查询微信二维码总数
	 * @param weixinId 微信ID
	 */
	@Override
	public int queryCount(int appId,int weixinId) {
		// TODO Auto-generated method stub 
		return weixinQrcodeDao.queryCount(appId,weixinId);
	}
	
	/**
	 * 批量删除微信二维码
	 * @param ids 需要删除的微信二维码ID集合
	 */
	@Override
	public void deleteByIds(int[] ids) {
		// TODO Auto-generated method stub
		this.weixinQrcodeDao.deleteByIds(ids);
	}
	
	/**
	 * 获取微信二维码列表
	 * @param weixinId 微信ID
	 * @param appId 应用ID
	 * @param page 分页对象
	 * @return 微信二维码列表
	 */
	@Override
	public List<WeixinQrcodeEntity> queryList(int appId,int weixinId,PageUtil page) {
		// TODO Auto-generated method stub
		return this.weixinQrcodeDao.queryList(appId,weixinId, page);
	}
	
}
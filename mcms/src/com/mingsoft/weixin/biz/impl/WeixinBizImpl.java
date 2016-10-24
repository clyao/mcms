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
import com.mingsoft.weixin.biz.IWeixinBiz;
import com.mingsoft.weixin.dao.IWeixinDao;
import com.mingsoft.weixin.entity.WeixinEntity;

/**
 * mswx-铭飞微信酒店预订平台
 * Copyright: Copyright (c) 2014 - 2015
 * Company:景德镇铭飞科技有限公司
 * @author 石超   
 * @version 300-001-001
 * 版权所有 铭飞科技
 * Comments:微信公众帐号基础信息biz层实现类
 * Create Date:2013-12-23
 * Modification history:
 */
@Service("weixinBiz")
public class WeixinBizImpl extends BaseBizImpl implements IWeixinBiz {

	/**
	 * 注入微信持久化层
	 */
	@Autowired
	private IWeixinDao weixinDao;
	
	@Override
	public IBaseDao getDao() {
		return this.weixinDao;
	}

	/**
	 * 根据公众号原始id获取的微信实体
	 * @param weixinOriginId 微信原始id
	 * @param weixinId 微信ID
	 * @return 微信基础信息
	 */
	@Override
	public WeixinEntity getWeixinEntityByWeixinOriginIdAndWeixinId(String weixinOriginId,int weixinId) {
		return this.weixinDao.getEntity(null,weixinId,weixinOriginId);
	}

	/**
	 * 根据自增长Id查询微信基础信息
	 * @param weixinId 自增长Id
	 * @return 微信基础信息
	 */
	@Override
	public WeixinEntity getEntityById(int weixinId) {
		return this.weixinDao.getEntity(null,weixinId,null);
	}
	
	
	/**
	 * 根据微信ID查找微信实体集合
	 * @param appId 应用编号
	 * @param page 分页条件
	 * @return 微信实体集合
	 */
	@Override
	public List<WeixinEntity> queryAllByAppId(int appId,PageUtil page) {
		List<WeixinEntity> weixinlist  = weixinDao.queryList(appId,page);
		return weixinlist;
	}
	

	/**
	 * 根据应用ID查询该应用下的微信数量
	 * @param appId 应用ID
	 * @return 微信数量
	 */
	public int getCountByAppId(int appId){
		return this.weixinDao.getCountByAppId(appId);
	}
	
	/**
	 * 根据微信ID集合批量删除微信
	 * @param ids 微信ID集合
	 */
	@Override
	public void deleteByIds(int[] ids) {
		weixinDao.deleteByIds(ids);
	}
}
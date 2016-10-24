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
import com.mingsoft.weixin.biz.IOauthBiz;
import com.mingsoft.weixin.dao.IOauthDao;
import com.mingsoft.weixin.entity.OauthEntity;

/**
 * 铭飞科技-微信
 * Copyright: Copyright (c) 2014 - 2015
 * @author 成卫雄  QQ:330216230
 * Comments:微信网页2.0授权业务层
 * Create Date:2014-10-7
 * Modification history:
 */
@Service("oauthBiz")
public class OauthBizImpl extends BaseBizImpl implements IOauthBiz{

	/**
	 * 注入网页授权持久化层
	 */
	@Autowired
	private IOauthDao oauthDao;

	@Override
	public IBaseDao getDao() {
		return this.oauthDao;
	}
	
	
	/**
	 * 根据id批量删除授权
	 * @param ids 需要删除的授权ID数组
	 */
	@Override
	public void deleteByIds(int[] ids) {
		// TODO Auto-generated method stub
		this.oauthDao.deleteByIds(ids);
	}
	

	/**
	 * 根据微信ID和分页参数查询授权列表
	 * @param weixinId 微信ID
	 * @param page 分页参数
	 */
	@Override
	public List<OauthEntity> queryList(int appId,int weixinId,PageUtil page) {
		// TODO Auto-generated method stub
		return this.oauthDao.queryList(appId,weixinId, page);
	}
	

	/**
	 * 根据微信ID查询授权总数
	 * @param appId 应用ID
	 * @param weixinId 微信ID
	 */
	@Override
	public int queryCount(int appId,int weixinId) {
		// TODO Auto-generated method stub
		return this.oauthDao.queryCount(appId,weixinId);
	}
}
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
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.mingsoft.base.dao.IBaseDao;
import com.mingsoft.util.PageUtil;
import com.mingsoft.weixin.entity.PassiveMessageEntity;

/**
 * 铭飞科技-微信
 * Copyright: Copyright (c) 2014 - 2015
 * @author 成卫雄   QQ:330216230
 * Comments:被动回复消息持久化层接口
 * Create Date:2014-10-12
 * Modification history:
 */
public interface IPassiveMessageDao extends IBaseDao{

	/**
	 * 根据自定义字段查询实体
	 * @param passiveMessageId 自增长ID
	 * @param whereMap 查询条件 key: 自定义字段的名(和数据库中保持一致) value:值</br>
	 * @return 查询到的被动消息回复实体
	 */
	public PassiveMessageEntity getEntityByCustom(@Param("whereMap")Map<String,Object> whereMap);
	
	/**
	 * 自定义字段查询被动消息集合</br>
	 * @param appId 应用编号
	 * @param weixinId 微信编号
	 * @param whereMap 查询条件 key:需要查询字段在数据库中的名称 value:值</br>
	 * @param orderMap 排序条件不需要时传入null即可 key：排序字段名称 value: true(desc) false(asc)</br>
	 * @param page 分页条件,传入null值时不进行分页
	 * @return 被动消息列表
	 */
	public List<PassiveMessageEntity> queryListByCustom(@Param("whereMap")Map<String,Object> whereMap,@Param("orderMap")Map<String,Boolean> orderMap,@Param("page")PageUtil page);
	
	/**
	 * 根据素材ID和微信ID查找被动回复关联素材是否存在
	 * @param passiveMessageNewsId 被动回复对应的素材ID
	 * @param weixinId 微信ID
	 * @return 被动消息集合
	 */
	public List<PassiveMessageEntity> queryListByNewsIdAndWeixinId(@Param("passiveMessageNewsId")Integer passiveMessageNewsId,@Param("weixinId")int weixinId);
	
	/**
	 * 根据ID集合批量删除被动回复
	 * @param ids ID集合
	 */
	void deleteByIds(@Param("ids")int[] ids);
	
	/**
	 * 根据自定义字段查询被动回复总数
	 * @param appId 应用ID
	 * @param weixinId 微信ID
	 * @param whereMap 查询条件
	 * @return 被动消息总数
	 */
	public int queryCountByCustom(@Param("whereMap")Map<String,Object> whereMap);
}
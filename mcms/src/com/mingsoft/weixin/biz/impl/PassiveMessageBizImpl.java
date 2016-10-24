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

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.mingsoft.base.biz.impl.BaseBizImpl;
import com.mingsoft.base.dao.IBaseDao;
import com.mingsoft.util.PageUtil;
import com.mingsoft.util.StringUtil;
import com.mingsoft.weixin.biz.INewsBiz;
import com.mingsoft.weixin.biz.IPassiveMessageBiz;
import com.mingsoft.weixin.constant.e.NewsTypeEnum;
import com.mingsoft.weixin.constant.e.PassiveMessageEventEnum;
import com.mingsoft.weixin.constant.e.PassiveMessageTypeEnum;
import com.mingsoft.weixin.dao.INewsDao;
import com.mingsoft.weixin.dao.IPassiveMessageDao;
import com.mingsoft.weixin.entity.NewsEntity;
import com.mingsoft.weixin.entity.PassiveMessageEntity;

/**
 * 铭飞科技-微信
 * Copyright: Copyright (c) 2014 - 2015
 * @author 成卫雄  QQ:330216230
 * Comments:被动消息回复业务层,实现接口IPassiveMessageBiz
 * Create Date:2014-10-12
 * Modification history:
 */
@Service("passiveMessageBiz")
public class PassiveMessageBizImpl extends BaseBizImpl implements IPassiveMessageBiz{
    
	/**
	 * 注入被动消息回复持久化层
	 */
	@Autowired
	private IPassiveMessageDao passiveMessageDao;
	
	/**
	 * 注入素材持久层
	 */
	@Autowired
	private INewsDao newsDao;
	
	/**
	 * 注入素材业务层
	 */
	@Autowired
	private INewsBiz newsBiz;
	
	@Override
	protected IBaseDao getDao() {
		// TODO Auto-generated method stub
		return this.passiveMessageDao;
	}

	
	/**
	 * 查询事件回复消息,</br>
	 * 若超出回复次数则取属性类别为最终回复的进行回复</br>
	 * @param appId 应用编号
	 * @param weixinId 微信编号
	 * @param eventId 关联的事件ID
	 * @param passiveMessageKey 关键字,当不存在关键字时传入null
	 * @param passiveMessageReplyNum 回复次数
	 * @return 查询到的实体类
	 */
	public PassiveMessageEntity getEntityBySendMessage(int appId,int weixinId,int eventId,String passiveMessageKey,int passiveMessageReplyNum){
		//查询条件
		Map<String,Object> whereMap = new HashMap<String,Object>();
		whereMap.put("passiveMessageAppId", appId);
		whereMap.put("passiveMessageWeixinId", weixinId);
		whereMap.put("passiveMessageEventId", eventId);
		if(!StringUtil.isBlank(passiveMessageKey)){
			whereMap.put("passiveMessageKey", passiveMessageKey);
		}
		whereMap.put("passiveMessageRelyNum", passiveMessageReplyNum);
		whereMap.put("passiveMessageType", PassiveMessageTypeEnum.ADD.toInt());
		PassiveMessageEntity passiveMessageEntity = passiveMessageDao.getEntityByCustom(whereMap);
		if(passiveMessageEntity != null){
			return passiveMessageEntity;
		}
		LOG.debug("PassiveMessageEntity" +passiveMessageEntity);
		//当迭代回复不满足条件时查询最终回复
		Map<String,Object> _whereMap = new HashMap<String,Object>();
		_whereMap.put("passiveMessageAppId", appId);
		_whereMap.put("passiveMessageWeixinId", weixinId);
		_whereMap.put("passiveMessageEventId", eventId);
		if(!StringUtil.isBlank(passiveMessageKey)){
			_whereMap.put("passiveMessageKey", passiveMessageKey);
		}
		_whereMap.put("passiveMessageType",PassiveMessageTypeEnum.FINAL.toInt());
		PassiveMessageEntity _passiveMessageEntity = passiveMessageDao.getEntityByCustom(_whereMap);
		if(_passiveMessageEntity != null){
			return _passiveMessageEntity;
		}
		return null;
	}
	
	
	
	/**
	 * 根据事件编号、应用编号、关键字查询被动消息回复列表
	 * @param event PassiveMessageEventEnum类型，事件编号,不存在时传入0
	 * @param appId 应用编号
	 * @param weixinId 微信编号
	 * @param key 关键字,当不存在关键字时传入null
	 * @param page 分页条件不进行分页时,直接传入null即可
	 * @return 被动消息回复列表
	 */
	public List<PassiveMessageEntity> queryListByEvent(PassiveMessageEventEnum event,int appId,int weixinId,String key,PageUtil page){
		//查询条件
		Map<String,Object> whereMap = new HashMap<String,Object>();
		whereMap.put("passiveMessageAppId", appId);
		whereMap.put("passiveMessageWeixinId", weixinId);
		if(event != null){
			whereMap.put("passiveMessageEventId", event.toInt()); //被动回复时间ID
		}
		if(!StringUtil.isBlank(key)){
			whereMap.put("passiveMessageKey", key);//被动回复关键字
		}	
		//排序条件
		Map<String,Boolean> orderMap = new HashMap<String,Boolean>();
		orderMap.put("passiveMessageId",true);	
		//根据上述查询条件和排序条件对相关被动回复列表进行查找和排序 
		List<PassiveMessageEntity> passiveMessageList = this.passiveMessageDao.queryListByCustom(whereMap, orderMap, page);
		if(passiveMessageList != null){
			//新建被动回复数组
			List<PassiveMessageEntity> list = new ArrayList<PassiveMessageEntity>();
			//根据被动消息关联的素材ID查询对应的素材
			for(int i=0;i<passiveMessageList.size();i++){
				//动态将查询得到的被动回复列表第i个实体转化成一个新的被动回复实体
				PassiveMessageEntity passiveMessageEntity = passiveMessageList.get(i);
				//查询被动消息关联的素材
				NewsEntity newsEntity = newsBiz.getNewsByNewsId(passiveMessageEntity.getPassiveMessageNewsId());
				//若关联的素材不存在
				if(newsEntity != null){
					//保存所查到的素材
					passiveMessageEntity.setNewsEntity(newsEntity);
				}
				list.add(passiveMessageEntity);
			}
			return list;
		}		
		return null;
	}	
	
	
	/**
	 * 保存关注回复
	 * @param passiveMessageEntity 被动回复实体
	 * @param replyType 被动回复类型
	 * @param content 被动回复内容
	 */
	@Override
	public void saveSubscribePassiveMessage(PassiveMessageEntity passiveMessageEntity,int replyType,String content){
		//判断回复的内容的类型
		if(replyType==NewsTypeEnum.TEXT.toInt()){  //文本
			//创建空的素材实体
			NewsEntity news = new NewsEntity();
			news.setNewsWeixinId(passiveMessageEntity.getPassiveMessageWeixinId());//微信编号
			news.setNewsAppId(passiveMessageEntity.getPassiveMessageAppId());//应用编号
			news.setNewsDateTime(new Timestamp(System.currentTimeMillis()));//保存时间
			news.setNewsType(NewsTypeEnum.TEXT);//素材类型：文本
			news.setNewsContent(content);//文本内容
			//保存素材
			newsDao.saveEntity(news);
			//保存关注回复关联素材ID
			passiveMessageEntity.setPassiveMessageNewsId(news.getNewsId());
			//保存关注回复
			passiveMessageDao.saveEntity(passiveMessageEntity);			
		}
		if(replyType==NewsTypeEnum.NEWS.toInt() || replyType==NewsTypeEnum.SINGLE_NEWS.toInt()){//图文				
			passiveMessageEntity.setPassiveMessageNewsId(Integer.valueOf(content));//被动回复消息事件消息的id
			//保存关注回复
			passiveMessageDao.saveEntity(passiveMessageEntity);
		}
	}
	
	/**
	 * 更新关注回复
	 * @param passiveMessageId 被动回复ID
	 * @param passiveMessageEntity 被动回复实体
	 * @param replyType 素材类型
	 * @param content 关注回复内容
	 */
	@Override
	public void updateSubscribePassiveMessage(int passiveMessageId,PassiveMessageEntity passiveMessageEntity,int replyType,String content){
			//获取关注回复实体对象
			PassiveMessageEntity passiveMessage = (PassiveMessageEntity) this.getEntity(passiveMessageId);
			//判断回复的内容的类型
			if(replyType==NewsTypeEnum.TEXT.toInt()){  //文本
				//新建素材实体
				NewsEntity news = new NewsEntity();
				news.setNewsWeixinId(passiveMessageEntity.getPassiveMessageWeixinId());//微信编号
				news.setNewsAppId(passiveMessageEntity.getPassiveMessageAppId());//应用编号
				news.setNewsDateTime(new Timestamp(System.currentTimeMillis()));//保存时间
				news.setNewsType(NewsTypeEnum.TEXT);//素材类型
				news.setNewsContent(content);//文本内容
				if(passiveMessage!=null){
					//获取更新前的素材
					NewsEntity oldNews = (NewsEntity) this.newsDao.getEntity(passiveMessage.getPassiveMessageNewsId());
					//更新前后素材类型均为文本,仅需更新素材表即可
					if(oldNews.getNewsType() == replyType){
						news.setNewsId(passiveMessage.getPassiveMessageNewsId());//素材ID
						this.newsDao.updateEntity(news);
					//图文或其他类型转文本，需要保存素材并更新被动回复所关联新的素材ID
					}else{
						//更新素材
						this.newsDao.saveEntity(news);
						passiveMessageEntity.setPassiveMessageNewsId(news.getNewsId());//关联素材ID
						//关注回复只能保留一条
						this.passiveMessageDao.deleteEntity(passiveMessageId);
						this.passiveMessageDao.saveEntity(passiveMessageEntity);
					}		
					return;
				}			
			}
			if(replyType==NewsTypeEnum.NEWS.toInt() || replyType==NewsTypeEnum.SINGLE_NEWS.toInt()){//图文					
				if(passiveMessage!=null){
					passiveMessageEntity.setPassiveMessageNewsId(Integer.valueOf(content));//关联素材ID
					//关注回复只能保留一条
					this.passiveMessageDao.deleteEntity(passiveMessageId);					
					this.passiveMessageDao.saveEntity(passiveMessageEntity);
					return;
				}
			}
	}
	
	/**
	 * 保存关键字自动回复
	 * @param passiveMessageEntity 被动回复实体
	 * @param replyType 被动回复类型
	 * @param content 被动回复内容
	 */
	@Override
	public void saveKeyPassiveMessage(PassiveMessageEntity passiveMessageEntity,int replyType,String content){
		NewsEntity news = new NewsEntity();
		//判断回复的内容的类型
		if(replyType==NewsTypeEnum.TEXT.toInt()){  //文本
			news.setNewsWeixinId(passiveMessageEntity.getPassiveMessageWeixinId());//微信ID
			news.setNewsAppId(passiveMessageEntity.getPassiveMessageAppId());//应用编号
			news.setNewsDateTime(new Timestamp(System.currentTimeMillis()));//素材新增时间
			news.setNewsType(NewsTypeEnum.TEXT);//素材类型文本
			news.setNewsContent(content);//文本内容
			//保存素材
			newsDao.saveEntity(news);			
			passiveMessageEntity.setPassiveMessageNewsId(news.getNewsId());//保存关键字回复关联素材ID
			//保存被动回复
			passiveMessageDao.saveEntity(passiveMessageEntity);				
		}
		if(replyType==NewsTypeEnum.NEWS.toInt() || replyType==NewsTypeEnum.SINGLE_NEWS.toInt()){//图文				
			passiveMessageEntity.setPassiveMessageNewsId(Integer.valueOf(content));//保存关键字回复关联素材ID
			//保存关键字回复
			passiveMessageDao.saveEntity(passiveMessageEntity);
		}
	}
	
	/**
	 * 更新关键字被动回复
	 * @param passiveMessageId 被动回复ID
	 * @param passiveMessageEntity 被动回复实体
	 * @param replyType 被动回复类型
	 * @param content 被动回复内容
	 */
	@Override
	public void updateKeyPassiveMessage(int passiveMessageId,PassiveMessageEntity passiveMessageEntity,int replyType,String content){
		//获取关注回复实体对象
		PassiveMessageEntity passiveMessage = (PassiveMessageEntity) this.getEntity(passiveMessageId);
		//判断回复的内容的类型
		if(replyType==NewsTypeEnum.TEXT.toInt()){  //文本
			//新建素材实体
			NewsEntity news = new NewsEntity();
			news.setNewsWeixinId(passiveMessageEntity.getPassiveMessageWeixinId());//保存微信ID
			news.setNewsAppId(passiveMessageEntity.getPassiveMessageAppId());//应用编号
			news.setNewsDateTime(new Timestamp(System.currentTimeMillis()));//保存时间
			news.setNewsType(NewsTypeEnum.TEXT);//素材类型：文本
			news.setNewsContent(content);//素材内容
			if(passiveMessage!=null){
				//获取更新前的素材
				NewsEntity oldNews = (NewsEntity) this.newsDao.getEntity(passiveMessage.getPassiveMessageNewsId());
				//更新前后素材类型均为文本,需更新素材内容
				if(oldNews.getNewsType() == replyType){
					news.setNewsId(passiveMessage.getPassiveMessageNewsId());//保存素材ID
					passiveMessageEntity.setPassiveMessageNewsId(passiveMessage.getPassiveMessageNewsId());//关联素材ID	
					//更新素材
					newsDao.updateEntity(news);
				//图文或其他类型转文本，需要保存文本素材
				}else{
					//新增素材
					newsDao.saveEntity(news);	
					passiveMessageEntity.setPassiveMessageNewsId(news.getNewsId());//关联素材ID	
				}		
				passiveMessageEntity.setPassiveMessageId(passiveMessageId);//被动回复Id
				//更新关键字回复
				passiveMessageDao.updateEntity(passiveMessageEntity);
				return;
			}				
		}
		if(replyType==NewsTypeEnum.NEWS.toInt() || replyType==NewsTypeEnum.SINGLE_NEWS.toInt()){					
			if(passiveMessage!=null){
				passiveMessageEntity.setPassiveMessageId(passiveMessageId);//被动回复ID
				passiveMessageEntity.setPassiveMessageNewsId(Integer.valueOf(content));//关联素材ID
				//更新关键字回复
				passiveMessageDao.updateEntity(passiveMessageEntity);
				return;
			}
		}
	}
	
	/**
	 * 查询总记录数
	 * @param event 被动回复事件编号
	 * @param appId 应用编号
	 * @param weixinId 微信ID
	 * @param key 被动回复关键字
	 * @return 被动回复总数
	 */
	@Override
	public int queryCountByCustom(int event,int appId,int weixinId,String key) {
		// TODO Auto-generated method stub
		//查询条件
		Map<String,Object> whereMap = new HashMap<String,Object>();
		if(event != 0){
			whereMap.put("passiveMessageEventId", event);
		}
		whereMap.put("passiveMessageAppId", appId);
		whereMap.put("passiveMessageWeixinId", weixinId);
		if(!StringUtil.isBlank(key)){
			whereMap.put("passiveMessageKey", key);
		}
		return this.passiveMessageDao.queryCountByCustom(whereMap);
	}

	/**
	 * 根据素材ID和微信ID查找关联素材是否存在
	 * @param passiveMessageNewsId 被动回复所关联素材ID
	 * @param weixinId 微信ID
	 * @return 关联素材的被动消息列表
	 */
	@Override
	public List<PassiveMessageEntity> queryListByNewsIdAndWeixinId(int passiveMessageNewsId, int weixinId) {
		// TODO Auto-generated method stub
		return this.passiveMessageDao.queryListByNewsIdAndWeixinId(passiveMessageNewsId, weixinId);
	}

	
	/**
	 * 批量删除
	 * @param ids 需要删除的被动回复ID集合
	 */
	@Override
	public void deleteByIds(int[] ids) {
		// TODO Auto-generated method stub
		passiveMessageDao.deleteByIds(ids);
	}

}
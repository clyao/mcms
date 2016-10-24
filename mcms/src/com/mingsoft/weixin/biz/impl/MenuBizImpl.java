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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.mingsoft.base.biz.impl.BaseBizImpl;
import com.mingsoft.base.dao.IBaseDao;
import com.mingsoft.util.StringUtil;
import com.mingsoft.weixin.biz.IMenuBiz;
import com.mingsoft.weixin.biz.IPassiveMessageBiz;
import com.mingsoft.weixin.constant.e.MenuStyleEnum;
import com.mingsoft.weixin.constant.e.MenuTypeEnum;
import com.mingsoft.weixin.constant.e.NewsTypeEnum;
import com.mingsoft.weixin.constant.e.PassiveMessageEventEnum;
import com.mingsoft.weixin.constant.e.PassiveMessageTypeEnum;
import com.mingsoft.weixin.dao.IMenuDao;
import com.mingsoft.weixin.dao.INewsDao;
import com.mingsoft.weixin.dao.IPassiveMessageDao;
import com.mingsoft.weixin.entity.MenuEntity;
import com.mingsoft.weixin.entity.NewsEntity;
import com.mingsoft.weixin.entity.PassiveMessageEntity;

/**
 * 
 * 微信底部菜单持久化层|继承:BaseBizImpl|实现：
 * @author 成卫雄(qq:330216230)
 * @version 
 * 版本号：100-000-000<br/>
 * 创建日期：2015年8月26日 上午11:00:46<br/>
 * 历史修订：<br/>
 */
@Service("menuBiz")
public class MenuBizImpl extends BaseBizImpl implements IMenuBiz{

	/**
	 * 注入菜单持久化层
	 */
	@Autowired
	private IMenuDao menuDao;
	
	/**
	 * 注入消息持久层
	 */
	@Autowired
	private INewsDao newsDao;
	
	/**
	 * 注入被动消息回复持久层
	 */
	@Autowired
	private IPassiveMessageDao passiveMessageDao;
	
	/**
	 * 注入被动消息回复业务层
	 */
	@Autowired
	private IPassiveMessageBiz passiveMessagebiz;
	
	@Override
	protected IBaseDao getDao() {
		// TODO Auto-generated method stub
		return this.menuDao;
	}
	
	/**
	 * 根据微信ID查询菜单集合
	 * @param appId 应用ID
	 * @param weixinId 菜单对应的微信ID 
	 * @return 菜单集合
	 */
	@Override
	public List<MenuEntity> queryListById(int appId,int weixinId) {
		List<MenuEntity> listMenu = menuDao.queryList(appId,weixinId,null);
		return listMenu;
	}

	
	/**
	 * 查询父菜单集合
	 * @param appId 应用id
	 * @param weixinId 微信ID
	 * @param menuMenuId 父菜单id
	 * @return 菜单集合
	 */
	@Override
	public List<MenuEntity> queryByParentId(int appId,int weixinId,Integer menuMenuId) {
		// TODO Auto-generated method stub
		List<MenuEntity> listMenu = menuDao.queryList(appId,weixinId,menuMenuId);
		return listMenu;
	}

	
	/**
	 * 保存或更新外连接
	 * @param menu 菜单
	 * @param sourceUrl  连接地址
	 */
	@Override
	public void updateOrCreateLink(MenuEntity menu,String sourceUrl) {
		menu.setMenuUrl(sourceUrl); //保存菜单外连接
		menu.setMenuType(MenuTypeEnum.VIEW); //保存菜单类型为跳转
		//根据菜单id判断是新增还是更新
		if(menu.getMenuId()==0){
			//保存菜单
			menuDao.saveEntity(menu);
		}else{
			menu.setMenuId(menu.getMenuId());//菜单ID
			//更新菜单
			menuDao.updateEntity(menu);
		}
	}
	
	/**
	 * 保存或更新菜单文本
	 * @param menu 菜单
	 * @param replyText 文本内容
	 * @param appId  应用编号
	 */
	@Override
	public void updateOrCreateText(MenuEntity menu,String replyText,int appId) {
		menu.setMenuType(MenuTypeEnum.CLICK);//点击文本菜单一定为click推事件
		//创建素材实体
		NewsEntity news = new NewsEntity();
		news.setNewsContent(replyText);//设置回复文本的内容
		news.setNewsType(NewsTypeEnum.TEXT);//设置素材类型
		news.setNewsWeixinId(menu.getMenuWeixinId());//保存微信ID
		news.setNewsAppId(appId);//保存应用ID 
		//获取被动回复对象
		PassiveMessageEntity passiveMessageEntity =new PassiveMessageEntity();
		passiveMessageEntity.setPassiveMessageAppId(appId);//保存appId	
		passiveMessageEntity.setPassiveMessageEvent(PassiveMessageEventEnum.CLICK);//被动回复消息事件类型
		passiveMessageEntity.setPassiveMessageType(PassiveMessageTypeEnum.FINAL);//设置被动消息回复为最终回复
		passiveMessageEntity.setPassiveMessageWeixinId(menu.getMenuWeixinId());//保存被动回复消息所对应的微信ID，无论新增or更新，都需要对应weixinId
		//根据菜单id判断是新增还是更新
		if(menu.getMenuId()==0){
			//保存文本素材
			newsDao.saveEntity(news);
			menu.setMenuUrl(StringUtil.timeForString());//菜单外连接,用于被动回复关键字
			passiveMessageEntity.setPassiveMessageKey(menu.getMenuUrl());//将系统时间作为被动回复的关键字
			passiveMessageEntity.setPassiveMessageNewsId(news.getNewsId());//被动回复消息事件类型的id
			//保存菜单
			menuDao.saveEntity(menu);
			//保存被动回复
			passiveMessageDao.saveEntity(passiveMessageEntity);
		}else{
			//后取更新前的菜单
			MenuEntity oldMenu = (MenuEntity) this.menuDao.getEntity(menu.getMenuId());
			//获取更新前的被动回复
			PassiveMessageEntity passiveMessage = this.passiveMessagebiz.getEntityBySendMessage(appId,oldMenu.getMenuWeixinId(),PassiveMessageEventEnum.CLICK.toInt(),oldMenu.getMenuUrl(),0);
			//获取更新前的素材
			NewsEntity newsEntity = (NewsEntity) this.newsDao.getEntity(passiveMessage.getPassiveMessageNewsId());
			menu.setMenuUrl(oldMenu.getMenuUrl());//菜单外连接,用于被动回复关键字
			passiveMessageEntity.setPassiveMessageKey(oldMenu.getMenuUrl());//被动回复关键字
			//判断更新菜单文本时菜单类型是否与更新前一致，若不一致不可直接更新
			if(oldMenu.getMenuStyle()==MenuStyleEnum.TEXT.toInt()){			
				news.setNewsId(newsEntity.getNewsId());
				//更新qianh
				this.newsDao.updateEntity(news);
			}else{
				//保存素材
				this.newsDao.saveEntity(news);
				passiveMessageEntity.setPassiveMessageId(passiveMessage.getPassiveMessageId());//被动回复ID
				passiveMessageEntity.setPassiveMessageNewsId(news.getNewsId());//被动回复消息事件类型的id
				//更新被动回复
				this.passiveMessageDao.updateEntity(passiveMessageEntity);				
			}
			//更新菜单
			this.menuDao.updateEntity(menu);
		}
	}
	
	
	/**
	 * 保存或更新菜单图文
	 * @param menu 菜单
	 * @param picId 素材ID
	 * @param appId  应用编号
	 */
	@Override
	public void updateOrCreatePic(MenuEntity menu,String picId,int appId){		
		if(!StringUtil.isBlank(picId)){
			menu.setMenuType(MenuTypeEnum.CLICK);//菜单类型，点击推事件
			//新建被动回复对象
			PassiveMessageEntity passiveMessageEntity =new PassiveMessageEntity();
			passiveMessageEntity.setPassiveMessageAppId(appId);//应用ID		
			passiveMessageEntity.setPassiveMessageEvent(PassiveMessageEventEnum.CLICK);//被动回复消息事件类型
			passiveMessageEntity.setPassiveMessageType(PassiveMessageTypeEnum.FINAL);//被动回复为最终回复
			passiveMessageEntity.setPassiveMessageWeixinId(menu.getMenuWeixinId());//保存被动回复消息所对应的微信ID，无论新增or更新，都需要对应weixinId
			//根据菜单id判断是新增还是更新
			if(menu.getMenuId()==0){
				menu.setMenuUrl(StringUtil.timeForString());//菜单外连接,用于被动回复关键字
				passiveMessageEntity.setPassiveMessageKey(menu.getMenuUrl());//被动回复的关键字
				passiveMessageEntity.setPassiveMessageNewsId(Integer.valueOf(picId));//被动回复消息事件类型的id
				//保存菜单
				menuDao.saveEntity(menu);
				//保存被动回复
				passiveMessageDao.saveEntity(passiveMessageEntity);
			}else{
				//后取更新前的菜单
				MenuEntity oldMenu = (MenuEntity) this.menuDao.getEntity(menu.getMenuId());
				menu.setMenuUrl(oldMenu.getMenuUrl());//菜单外连接,用于被动回复关键字
				passiveMessageEntity.setPassiveMessageKey(oldMenu.getMenuUrl());//被动回复关键字
				//判断更新菜单类型前后是否一致
				if(oldMenu.getMenuStyle()!=MenuStyleEnum.PIC_ARTICLE.toInt()){
					PassiveMessageEntity passiveMessage = this.passiveMessagebiz.getEntityBySendMessage(appId, oldMenu.getMenuWeixinId(),PassiveMessageEventEnum.CLICK.toInt(),oldMenu.getMenuUrl(),0);
					passiveMessageEntity.setPassiveMessageId(passiveMessage.getPassiveMessageId());//被动回复ID
					passiveMessageEntity.setPassiveMessageNewsId(Integer.valueOf(picId));//被动回复消息事件类型的id
					//更新被动回复
					this.passiveMessageDao.updateEntity(passiveMessageEntity);					
				}
				//更新菜单
				menuDao.updateEntity(menu);
			}
		}
	}
	
	
	/**
	 * 保存或更新菜单关键字
	 * @param menu 菜单
	 * @param passiveMessageId 被动回复实体id
	 * @param keyword 关键字
	 */
	@Override
	public void updateOrCreateKeyword(MenuEntity menu, String passiveMessageId,String keyword) {
		// TODO Auto-generated method stub
		if(!StringUtil.isBlank(passiveMessageId)){
			menu.setMenuUrl(String.valueOf(passiveMessageId));//将消息id保存到菜单表中
			menu.setMenuType(MenuTypeEnum.CLICK);//菜单类型为点击推事件
			//新建被动回复实体
			PassiveMessageEntity passiveMessage = new PassiveMessageEntity();
			//根据ID查找被动回复实体
			Map<String,Object> whereMap = new HashMap<String,Object>();
			//查询条件
			whereMap.put("passiveMessageId", passiveMessageId);
			passiveMessage = this.passiveMessageDao.getEntityByCustom(whereMap);
			passiveMessage.setPassiveMessageKey(keyword);//关键字
			//根据菜单id判断是新增还是更新
			if(menu.getMenuId()==0){
				//保存被动回复
				passiveMessageDao.saveEntity(passiveMessage);
				//保存菜单
				menuDao.saveEntity(menu);
			}else{
				//后取更新前的菜单
				MenuEntity oldMenu = (MenuEntity) this.menuDao.getEntity(menu.getMenuId());
				if(oldMenu.getMenuStyle()==MenuStyleEnum.KEYWORD.toInt()){//若为关键字
					//获取被动回复实体
					passiveMessage  = (PassiveMessageEntity) this.passiveMessageDao.getEntity(Integer.valueOf(oldMenu.getMenuUrl()));
				}
				//更新被动回复
				passiveMessageDao.updateEntity(passiveMessage);
				//更新菜单
				menuDao.updateEntity(menu);
			}
		}
	}

	/**
	 * 查询指定app下的所有菜单</br>
	 * 	并按照父子的格式排列好</br>
	 * @param appId 系统应用ID
	 * @param weixinId 关联系统中微信的唯一ID
	 * @return 菜单列表
	 */	
	@Override
	public List<MenuEntity> queryMenu(int appId,int weixinId) {
		//查询所有的父菜单
		List<MenuEntity> listMenu = menuDao.queryList(appId,weixinId,0);
		//查询对应父菜单下的子菜单
		for(int i = 0;i<listMenu.size();i++){
			int menuMenuId = listMenu.get(i).getMenuId();
			//查询父菜单的子菜单
			List<MenuEntity> listSonMenu = menuDao.queryList(appId,weixinId,menuMenuId);
			listMenu.get(i).setChildsMenu(listSonMenu);
		}
		return listMenu;
	}
	
	/**
	 * 根据父菜单 微信id 和appId查询菜单列表
	 * @param appId 系统应用ID
	 * @param weixinId 菜单微信ID
	 * @param menuMenuId 父菜单id			
	 * @return 菜单列表
	 */
	@Override
	public List<MenuEntity> queryByMenumenuId(int appId,int weixinId,Integer menuMenuId) {
		List<MenuEntity> listMenu = menuDao.queryList(appId,weixinId,menuMenuId);
		return listMenu;
	}

	/**
	 * 查询菜单列表
	 * @param menuUrl 菜单地址
	 * @param appId 应用编号
	 * @param weixinId 微信ID
	 * @return 菜单列表
	 */
	@Override
	public List<MenuEntity> queryListByMenuUrl(String menuUrl,int appId,int weixinId) {
		// TODO Auto-generated method stub
		return this.menuDao.queryListByMenuUrl(menuUrl,appId,weixinId);
	}

	
	
}
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

package com.mingsoft.weixin.entity;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.mingsoft.base.entity.BaseEntity;
import com.mingsoft.basic.entity.AppEntity;
import com.mingsoft.basic.entity.CategoryEntity;
import com.mingsoft.cms.entity.ArticleEntity;
import com.mingsoft.weixin.constant.e.NewsTypeEnum;

/**
 * 铭飞CMS-铭飞内容管理系统
 * Copyright: Copyright (c) 2014 - 2015
 * Company:景德镇铭飞科技有限公司
 * @author 刘继平
 * @version 300-001-001
 * 版权所有 铭飞科技
 * Comments: 微信素材实体，继承BaseEntity
 * Create Date:2014-9-25
 * Modification history:
 */
public class NewsEntity extends BaseEntity {

	/**
	 * 微信素材编号
	 */
	private int newsId;

	/**
	 * 微信素材类别编号 1.图文 2.文本 3.图片
	 */
	private int newsType;
	
	/**
	 * 微信素材封面
	 */
	private int newsMasterArticleId;

	/**
	 * 微信图文素材主消息
	 */
	private ArticleEntity newsMasterArticle;

	/**
	 * 微信素材多图文，文章编号集合，以逗号隔开
	 */
	private String newsChildArticleIds;

	/**
	 * 素材发布时间
	 */
	private Timestamp newsDateTime = new Timestamp(System.currentTimeMillis());

	/**
	 * 素材所属appID
	 */
	private int newsAppId;
	
	/**
	 * 主素材连接地址
	 */
	private String newsLink;
	
	/**
	 * 素材所属微信ID
	 */
	private int newsWeixinId;
	
	
	public String getNewsLink() {
		return newsLink;
	}

	public void setNewsLink(String newsLink) {
		this.newsLink = newsLink;
	}

	/**
	 * 应用地址
	 */
	private AppEntity app;

	public AppEntity getApp() {
		return app;
	}

	public void setApp(AppEntity app) {
		this.app = app;
	}

	/**
	 * 素材内容
	 */
	private String newsContent;

	/**
	 * 栏目分类与newsCategoryId冗余，主要是便于读取
	 */
	private CategoryEntity newsCategory;

	/**
	 * 素材分类id，与category冗余，主要是便于保存
	 */
	private Integer newsCategoryId;

	/**
	 * 应用请求绝对地址
	 */
	private String appHostUrl;

	/**
	 * 多图文素材关联的子文章
	 */
	private List<ArticleEntity> newsChildsArticle = new ArrayList<ArticleEntity>();

	public String getAppHostUrl() {
		return appHostUrl;
	}

	public List<ArticleEntity> getChilds() {
		return newsChildsArticle;
	}

	public int getNewsAppId() {
		return newsAppId;
	}

	public CategoryEntity getNewsCategory() {
		return newsCategory;
	}

	public int getNewsCategoryId() {
		return newsCategoryId==null?0:newsCategoryId;
	}

	public String getNewsChildArticleIds() {
		return newsChildArticleIds;
	}

	/**
	 * 获取素材内容
	 * 
	 * @return
	 */
	public String getNewsContent() {
		return newsContent;
	}

	public Timestamp getNewsDateTime() {
		return newsDateTime;
	}

	public int getNewsId() {
		return newsId;
	}

	public ArticleEntity getNewsMasterArticle() {
		return newsMasterArticle;
	}

	public int getNewsMasterArticleId() {
		return newsMasterArticleId;
	}

	public int getNewsType() {
		return newsType;
	}
	
	private boolean isText = false;
	/**
	 * 检测是否是文本消息
	 * @return
	 */
	public boolean getIsText() {
		return this.newsType==NewsTypeEnum.TEXT.toInt();
	}
	
	
	private boolean isNews = false;
	
	/**
	 * 检测是否是图文消息
	 * @return
	 */
	public boolean getIsNews() {
		return this.newsType==NewsTypeEnum.NEWS.toInt();
	}

	public void setAppHostUrl(String appHostUrl) {
		this.appHostUrl = appHostUrl;
	}

	public void setChilds(List<ArticleEntity> childs) {
		this.newsChildsArticle = childs;
	}

	public void setNewsAppId(int newsAppId) {
		this.newsAppId = newsAppId;
	}

	public void setNewsCategory(CategoryEntity newsCategory) {
		this.newsCategory = newsCategory;
	}

	public void setNewsCategoryId(int newsCategoryId) {
		this.newsCategoryId = newsCategoryId;
	}

	public void setNewsChildArticleIds(String newsChildArticleIds) {
		this.newsChildArticleIds = newsChildArticleIds;
	}

	/**
	 * 设置素材内容
	 * 
	 * @param newsContent
	 */
	public void setNewsContent(String newsContent) {
		this.newsContent = newsContent;
	}

	public void setNewsDateTime(Timestamp newsDateTime) {
		this.newsDateTime = newsDateTime;
	}

	public void setNewsId(int newsId) {
		this.newsId = newsId;
	}

	public void setNewsMasterArticle(ArticleEntity newsMasterArticle) {
		this.newsMasterArticle = newsMasterArticle;
	}

	public void setNewsMasterArticleId(int newsMasterArticleId) {
		this.newsMasterArticleId = newsMasterArticleId;
	}

	/**
	 * 推荐使用：枚举类型参数方法
	 * @param newsType
	 */
	@Deprecated
	public void setNewsType(int newsType) {
		this.newsType = newsType;
	}
	
	public void setNewsType(NewsTypeEnum newsType) {
		this.newsType = newsType.toInt();
	}

	public int getNewsWeixinId() {
		return newsWeixinId;
	}

	public void setNewsWeixinId(int newsWeixinId) {
		this.newsWeixinId = newsWeixinId;
	}
	
	
	
}
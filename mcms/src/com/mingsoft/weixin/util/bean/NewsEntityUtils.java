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

package com.mingsoft.weixin.util.bean;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.mingsoft.cms.entity.ArticleEntity;
import com.mingsoft.parser.IParserRegexConstant;
import com.mingsoft.util.StringUtil;
import com.mingsoft.weixin.bean.NewsBean;
import com.mingsoft.weixin.bean.UploadNewsBean;
import com.mingsoft.weixin.constant.e.NewsTypeEnum;
import com.mingsoft.weixin.constant.e.UploadNewsBeanCoverPicEnum;
import com.mingsoft.weixin.entity.NewsEntity;
import com.mingsoft.weixin.entity.WeixinEntity;
import com.mingsoft.weixin.util.UploadDownUtils;

/** 
 * 素材实体类型转化工具类
 * @author  付琛  QQ:1658879747 
 * @version 1.0 
 * 创建时间：2015年11月22日 上午2:55:00  
 * 版本号：100-000-000<br/>
 * 历史修订<br/>
 */
public class NewsEntityUtils extends BaseBeanUtils{
	
	/**
	 * 将素材实体转化为发送被动消息响应实体
	 * @param news 素材实体
	 * @param url 域名地址：.
	 * @return 微信发送被动消息响应实体
	 */
	public static List<NewsBean> newsEntityToNewsBeanList(String url,NewsEntity news,int appId){
		if(StringUtil.isBlank(url)){
			return null;
		}
		//创建图文实体的arrayList数组，动态添加发送失败的微信用户
		List<NewsBean> newsBeanList = new ArrayList<NewsBean>();
		//创建newsBean实体
		NewsBean newsBean = new NewsBean();
		//保存图文封面相关信息,并赋给newsBean
		newsBean.setTitle(news.getNewsMasterArticle().getBasicTitle());//封面标题
		newsBean.setPicurl(url+news.getNewsMasterArticle().getBasicThumbnails());//封面缩略图
		newsBean.setDescription(news.getNewsMasterArticle().getBasicDescription());//封面描述
		newsBean.setUrl(url+File.separator+IParserRegexConstant.HTML_SAVE_PATH+File.separator+appId+File.separator+IParserRegexConstant.MOBILE+File.separator+news.getNewsMasterArticle().getArticleUrl());//封面跳转地址
		//无论多图文还是单图文，都必须保存封面图文,对单图文而言，封面图文与其一样，故只考虑多图文
		newsBeanList.add(newsBean);
		//若为多图文，要保存其子图文
		if(news.getNewsType() == NewsTypeEnum.NEWS.toInt()){
			//获取子图文素材集合
			List<ArticleEntity> articleList = news.getChilds();
			if(articleList == null || articleList.size() == 0){
				return null;
			}
			for(int i=1;i<=articleList.size();i++){		
				//动态创建空的newsBean,增加newBeanList的长度，以用于赋值
				newsBeanList.add(new NewsBean());
				newsBeanList.get(i).setPicurl(url+articleList.get(i-1).getBasicThumbnails()); //子图文缩略图
				newsBeanList.get(i).setTitle(articleList.get(i-1).getBasicTitle()); //字图文标题
				newsBeanList.get(i).setDescription(articleList.get(i-1).getBasicDescription()); //子图文描述
				newsBeanList.get(i).setUrl(url+File.separator+IParserRegexConstant.HTML_SAVE_PATH+File.separator+appId+File.separator+IParserRegexConstant.MOBILE+File.separator+articleList.get(i-1).getArticleUrl()); //子图文链接地址  待确定
			}
		}
		return newsBeanList;
	}
	
	/**
	 * 将素材实体转化为微信上传图文消息实体
	 * @param thumb_media_id 封面图片上传后返回的唯一ID
	 * @param news 素材实体
	 * @return 微信上传图文消息实体
	 */
	public static List<UploadNewsBean> newsEnttiyToUploadNewsBeanList(WeixinEntity weixin,NewsEntity news,HttpServletRequest request){
		UploadDownUtils uploadUtil = new UploadDownUtils(weixin.getWeixinAppID(), weixin.getWeixinAppSecret());	
		//创建uploadNewsBean实体,并赋予上传素材封面必要的参数
		UploadNewsBean uploadNewsBean = new UploadNewsBean(news.getNewsMasterArticle().getBasicTitle(), news.getNewsMasterArticle().getArticleContent());
		//得到单图文or多图文的封面图片唯一ID
		String thumb_media_id = uploadUtil.uploadMedia(UploadDownUtils.TYPE_IMAGE, news.getNewsMasterArticle().getBasicThumbnails(),request);
		//保存图文封面相关信息,并赋给uploadNewsBean
		uploadNewsBean.setThumb_media_id(thumb_media_id);
		uploadNewsBean.setAuthor(news.getNewsMasterArticle().getArticleAuthor()); //图文作者
	//	uploadNewsBean.setContent_source_url(news.getNewsMasterArticle().getArticleUrl()); //在图文消息页面点击“阅读原文”后的页面
		uploadNewsBean.setDigest(news.getNewsMasterArticle().getBasicDescription()); //封面描述
		uploadNewsBean.setShow_cover_pic(UploadNewsBeanCoverPicEnum.SHOW_COVER_PIC); //显示封面
		//创建图文实体列表,动态添加上传图文素材实体
		List<UploadNewsBean> uploadNewsBeanList = new ArrayList<UploadNewsBean>();
		//无论多图文还是单图文，都必须保存封面图文,对单图文而言，封面图文与其一样，故只考虑多图文
		uploadNewsBeanList.add(uploadNewsBean);
		//若为多图文
		if(news.getNewsType() == NewsTypeEnum.NEWS.toInt()){
			//获取字子图文素材集合
			List<ArticleEntity> articleList = news.getChilds();
			if(articleList == null || articleList.size() ==0){
				return null;
			}
			for(int i=1;i<=articleList.size();i++){		
				//动态创建空的实体,增加newBeanList的长度，以用于赋值   子图文标题  子图文内容
				uploadNewsBeanList.add(new UploadNewsBean(articleList.get(i-1).getBasicTitle(),articleList.get(i-1).getArticleContent()));			
				//保存子图文相关信息,并赋给uploadNewsBean
				String tm_id = uploadUtil.uploadMedia(UploadDownUtils.TYPE_IMAGE,articleList.get(i-1).getBasicThumbnails(),request); //每个子图文的缩略图唯一ID
				uploadNewsBeanList.get(i).setThumb_media_id(tm_id); //字图文缩略图唯一ID
				uploadNewsBeanList.get(i).setAuthor(articleList.get(i-1).getArticleAuthor()); //子图文作者
		//		uploadNewsBeanList.get(i).setContent_source_url(articleList.get(i-1).getArticleUrl()); //子图文链接地址
				uploadNewsBeanList.get(i).setDigest(articleList.get(i-1).getBasicDescription()); //子图文描述
				uploadNewsBeanList.get(i).setShow_cover_pic(UploadNewsBeanCoverPicEnum.SHOW_COVER_PIC); //显示封面
			}
		}
		return uploadNewsBeanList;
	}
	
}
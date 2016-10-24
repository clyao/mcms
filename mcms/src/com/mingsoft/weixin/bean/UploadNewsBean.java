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

package com.mingsoft.weixin.bean;

import com.mingsoft.weixin.constant.e.UploadNewsBeanCoverPicEnum;

/** 
 * 
 * 微信群发需要上传的素材实体
 * @author  付琛  QQ:1658879747 
 * @version 1.0 
 * 创建时间：2015年11月19日 上午1:48:44  
 * 版本号：100-000-000<br/>
 * 历史修订<br/>
 */
public class UploadNewsBean extends BaseBean{
	
	/**
	 * 图文消息缩略图的media_id，可以在基础支持-上传多媒体文件接口中获得
	 */
	private String thumb_media_id;
	
	/**
	 * 图文消息的作者
	 */
	private String author;
	
	/**
	 * 标题
	 */
	private String title;
	
	/**
	 * 在图文消息页面点击“阅读原文”后的页面
	 */
	private String content_source_url;
	
	/**
	 * 图文消息页面的内容，支持HTML标签。具备微信支付权限的公众号，可以使用a标签，其他公众号不能使用
	 */
	private String content;
	
	/**
	 * 图文消息的描述
	 */
	private String digest;
	

	/**
	 * 是否显示封面，1为显示，0为不显示
	 */
	private String show_cover_pic;
	
	
	public String getThumb_media_id() {
		return thumb_media_id;
	}

	public void setThumb_media_id(String thumb_media_id) {
		this.thumb_media_id = thumb_media_id;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent_source_url() {
		return content_source_url;
	}

	public void setContent_source_url(String content_source_url) {
		this.content_source_url = content_source_url;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getDigest() {
		return digest;
	}

	public void setDigest(String digest) {
		this.digest = digest;
	}

	public String getShow_cover_pic() {
		return show_cover_pic;
	}
	
	@Deprecated
	public void setShow_cover_pic(String show_cover_pic) {
		this.show_cover_pic = show_cover_pic;
	}
	
	public void setShow_cover_pic(UploadNewsBeanCoverPicEnum uploadCoverEnum){
		this.show_cover_pic = uploadCoverEnum.toString();
	}
	
	
	/**
	 * 上传图文时必须传的值
	 * @param thumb_media_id
	 * @param title
	 * @param content
	 */
	public UploadNewsBean(String title,String content){
		this.title = title;
		this.content = content;
	}
	
}
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

package com.mingsoft.weixin.event;

/**
 * 铭飞科技流量推广软件
 * Copyright: Copyright (c) 2013 - 2015
 * Company:景德镇铭飞科技有限公司
 * @author killfen
 * @version 100-000-000
 * 版权所有
 * Comments:微信关注事件类型 ,提供给IWeixinEventHandler使用，后续微信的所有的事件类型都由这个类来管理
 * Create Date:2014-3-11
 * Modification history:暂无
 */
public interface IWeixinEventType {

	/**
	 * 事件类型
	 */
	final String EVENT = "event";

	/**
	 * 微信xml msgtype节点
	 */
	final String XML_MSG_TYPE_NODE = "MsgType";

	/**
	 * 微信xml event节点
	 */
	final String XML_EVENT_NODE = "Event";

	/**
	 * 开发者
	 */
	final String XML_FROM_USER_NAME = "FromUserName";

	/**
	 * 菜单的点击事件类型</br>
	 *			1、click：点击推事件</br>
	 * 			2、view：跳转URL</br>
	 *			3、scancode_push：扫码推事件</br>
	 * 			4、scancode_waitmsg：扫码推事件且弹出“消息接收中”提示框</br>
	 * 			5、pic_sysphoto：弹出系统拍照发图</br>
	 * 			6、pic_photo_or_album：弹出拍照或者相册发图</br>
	 *			7、pic_weixin：弹出微信相册发图器</br>
	 *			8、location_select：弹出地理位置选择器</br>
	 */
	final String[] MENU_EVENT_TYPE = {"click","view","scancode_push","scancode_waitmsg","pic_sysphoto","pic_photo_or_album","pic_weixin","location_select"};
	
	/**
	 * 底部菜单</br>
	 * 扫码推事件</br>
	 */
	final String MENU_EVEN_SCANCODE_WAITMSG = "scancode_waitmsg";
	
	/**
	 * 点击底部菜单推事件
	 */
	final String MENU_EVEN_CLICK="click";
	
	/**
	 * 接受者
	 */
	final String XML_TO_USER_NAME = "ToUserName";

	/**
	 * 新关注
	 */
	final String EVENT_SUBSCRIBE = "subscribe";
	
	/**
	 * 取消关注
	 */
	final String EVENT_UNSUBSCRIBE = "unsubscribe";
	
	/**
	 * 地理位置事件
	 */
	final String EVENT_LOCATION = "LOCATION";
	
	/**
	 * 普通文本消息
	 */
	final String MSGTYPE_TEXT = "text";
	
	/**
	 * 关注用户发送的text型的内容
	 */
	final String XML_MSG_CONTENT = "Content";
	
	/**
	 * 素材编号
	 */
	final String XML_MEDIAID = "MediaId";
	
	/**
	 * 关注用户发送的图片链接地址
	 */
	final String XML_MSG_PICURL = "PicUrl";
	
	/**
	 * 普通图文消息
	 */
	final String MSGTYPE_IMAGE = "image";	
	
	/**
	 *语音消息
	 */
	final String MSGTYPE_VOICE = "voice";		
	
	/**
	 *视频消息
	 */
	final String MSGTYPE_VIDEO = "video";
	
	/**
	 *地理位置信息
	 */
	final String MSGTYPE_LOCATION = "location";
	
	/**
	 *链接消息
	 */
	final String MSGTYPE_LINK = "link";	
	
	/**
	 * 已关注的用户扫描二维码事件
	 */
	final String SUBSCRIBED_SCAN="SCAN";
	
	/**
	 * 微信xml EventKey节点
	 */
	final String XML_EVENTKEY= "EventKey";
	
	/**
	 * 微信xml 底部菜单扫描二维码</br>
	 * 二维码中的信息父节点</br>
	 */
	final String XML_SCANCODEINFO = "ScanCodeInfo";
	
	/**
	 * 微信xml 底部菜单扫描二维码</br>
	 * 二维码中的信息子节点</br>
	 */
	final String XML_SCANRESULT = "ScanResult";
	
	/**
	 * 微信xml EventKey节点
	 */
	final String XML_TICKET= "Ticket";
	
}
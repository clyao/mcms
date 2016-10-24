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

package com.mingsoft.weixin.action;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mingsoft.basic.constant.Const;
import com.mingsoft.basic.constant.e.CookieConstEnum;
import com.mingsoft.util.PageUtil;
import com.mingsoft.util.StringUtil;
import com.mingsoft.weixin.biz.IWeixinQrcodeBiz;
import com.mingsoft.weixin.constant.ModelCode;
import com.mingsoft.weixin.constant.e.QrcodeTypeEnum;
import com.mingsoft.weixin.entity.WeixinEntity;
import com.mingsoft.weixin.entity.WeixinQrcodeEntity;

/** 
 * 微信二维码控制层
 * @author  付琛  QQ:1658879747 
 * @version 1.0 
 * 创建时间：2015年11月13日 下午3:54:20  
 * 版本号：100-000-000<br/>
 * 历史修订<br/>
 */

@Controller
@RequestMapping("/${managerPath}/weixin/qrcode/")
public class WeixinQrcodeAction extends BaseAction{
	/**
	 * 注入微信二维码业务层
	 */
	@Autowired
	private IWeixinQrcodeBiz weixinQrcodeBiz;
	
	/**
	 * 保存微信二维码
	 * @param response
	 * @param request
	 */
	@RequestMapping("/save")
	@ResponseBody
	public void save(@ModelAttribute WeixinQrcodeEntity qrcodeEntity,HttpServletResponse response,HttpServletRequest request){	
		//若二维码不存在
		if(qrcodeEntity == null){
			this.outJson(response, null, false);
			return;
		}
		//获取微信
		WeixinEntity weixin = this.getWeixinSession(request);
		//若微信不存在
		if(weixin == null|| weixin.getWeixinId()<=0){
			this.outJson(response, ModelCode.WEIXIN_MESSAGE, false, this.getResString("weixin.not.found"));
			return;
		}
		//获取微信ID
		int weixinId = weixin.getWeixinId();
		//获取appId
		int appId = this.getAppId(request);
		qrcodeEntity.setQrcodeAppId(appId);//保存应用ID
		qrcodeEntity.setQrcodeWeixinId(weixinId);//保存微信ID
		qrcodeEntity.setQrcodeTime(new Date());//保存时间
		//若二维码为临时二维码
		if(qrcodeEntity.getQrcodeType()!=0 && QrcodeTypeEnum.OCASSION.toInt() == qrcodeEntity.getQrcodeType()){
			//设置过期时间
			qrcodeEntity.setQrcodeExpireTime(1800);
		}
		//保存二维码
		weixinQrcodeBiz.saveEntity(qrcodeEntity);
		this.outJson(response, null, true);
	}
	
	
	/**
	 * 批量删除
	 * @param response
	 * @param request
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public void delete(HttpServletResponse response,HttpServletRequest request){		
		//获取要删除的二维码ID字符串数组
		String[] qrcodeIds = request.getParameterValues("qrcodeIds");
		//判断数组是否为空
		if(StringUtil.isBlank(qrcodeIds)){
			//返回json数据
			this.outJson(response, null, false);
			return;
		}
		//判断字符串数组是否转成integer型数组
		if(!StringUtil.isIntegers(qrcodeIds)){
			//返回json数据
			this.outJson(response, null, false);
			return;
		}
		//得到ID数组并将字符串数组转化为int型数组
		int[] ids = StringUtil.stringsToInts(qrcodeIds);
		//根据ID批量删除
		weixinQrcodeBiz.deleteByIds(ids);
		this.outJson(response, null, true);
	}
	
	/**
	 * 更新二维码
	 * @param qrcodeEntity 二维码实体
	 * @param qrcodeId 二维码ID
	 * @param response
	 * @param request
	 */
	@RequestMapping("/{qrcodeId}/update")
	public void update(@ModelAttribute WeixinQrcodeEntity qrcodeEntity,@PathVariable int qrcodeId,HttpServletResponse response,HttpServletRequest request){
		//若二维码不存在
		if(qrcodeEntity == null || qrcodeId<=0){
			this.outJson(response, null, false);
			return;
		}
		//获取微信
		WeixinEntity weixin = this.getWeixinSession(request);
		//若微信不存在
		if(weixin == null|| weixin.getWeixinId()<=0){
			this.outJson(response, ModelCode.WEIXIN_MESSAGE, false, this.getResString("weixin.not.found"));
			return;
		}
		//获取微信ID
		int weixinId = weixin.getWeixinId();
		//获取appId
		int appId = this.getAppId(request);
		qrcodeEntity.setQrcodeAppId(appId);//保存应用ID
		qrcodeEntity.setQrcodeWeixinId(weixinId);//保存微信ID
		qrcodeEntity.setQrcodeTime(new Date());//保存时间
		//若二维码为临时二维码
		if(qrcodeEntity.getQrcodeType()!=0 && QrcodeTypeEnum.OCASSION.toInt() == qrcodeEntity.getQrcodeType()){
			//设置过期时间
			qrcodeEntity.setQrcodeExpireTime(1800);
		}
		//读取更新后的需要跳转的地址
		String url = this.getCookie(request, CookieConstEnum.BACK_COOKIE);
		//更新二维码
		weixinQrcodeBiz.updateEntity(qrcodeEntity);
		this.outJson(response,null, true,null,url);
	}
	
	/**
	 * 二维码列表页
	 * @param response
	 * @param request
	 * @param mode
	 * @return 二维码集合
	 */
	@RequestMapping("list")
	public String list(HttpServletResponse response,HttpServletRequest request,ModelMap mode){
		//查询当前页码
		int pageNo = this.getPageNo(request);
		//查询appId
		int appId = this.getAppId(request);
		//取出微信实体,得到微信Id
		WeixinEntity weixin = this.getWeixinSession(request);
		//获取微信ID
		int weixinId = weixin.getWeixinId();
		//查询记录总数
		int recordCount = this.weixinQrcodeBiz.queryCount(appId,weixinId);
		//分页查询
		PageUtil page=new PageUtil(pageNo,recordCount, this.getUrl(request)+"/manager/weixin/qrcode/list.do");
		//根据微信ID，appID，及分页参数查询列表
		List<WeixinQrcodeEntity> qrCodeList = this.weixinQrcodeBiz.queryList(appId,weixinId,page); 
		//压入url地址
		this.setCookie(request, response, CookieConstEnum.BACK_COOKIE,"list.do?&pageNo="+pageNo);
		mode.addAttribute("qrCodeList",qrCodeList);
		mode.addAttribute("page",page);
		return view("/weixin/qrcode/qrcode_list");
	}
}
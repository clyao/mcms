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

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mingsoft.basic.constant.Const;
import com.mingsoft.basic.constant.e.CookieConstEnum;
import com.mingsoft.util.PageUtil;
import com.mingsoft.util.StringUtil;
import com.mingsoft.weixin.biz.IOauthBiz;
import com.mingsoft.weixin.constant.ModelCode;
import com.mingsoft.weixin.entity.OauthEntity;
import com.mingsoft.weixin.entity.WeixinEntity;

/**
 * 
 * 网页2.0授权控制层
 * @author 成卫雄(qq:330216230)
 * @version 
 * 版本号：100-000-000<br/>
 * 创建日期：2015年8月19日 下午2:47:34<br/>
 * 历史修订：<br/>
 */
@Controller
@RequestMapping("/${managerPath}/weixin/oauth")
public class OauthAction extends BaseAction{

	/**
	 * 注入授权2.0业务层
	 */
	@Autowired
	private IOauthBiz oauthBiz;
	
	/**
	 * 授权链接列表
	 * @param request
	 * @param response
	 * @return 授权列表
	 */
	@RequestMapping("/list")
	public String list(ModelMap model,HttpServletRequest request,HttpServletResponse response){
		WeixinEntity weixin = this.getWeixinSession(request);
		//获取微信Id
		int weixinId = weixin.getWeixinId();
		//获取appId
		int appId = this.getAppId(request);
		//获取当前页码
		int pageNo = this.getPageNo(request);
		//列表总数
		int count = this.oauthBiz.queryCount(appId,weixinId);
		//创建分页对象
		PageUtil page = new PageUtil(pageNo,count, this.getUrl(request)+"/manager/weixin/oauth/list.do");
		//查询授权列表
		List<OauthEntity> listOauth = this.oauthBiz.queryList(appId,weixinId, page);
		//压入url地址
		this.setCookie(request, response, CookieConstEnum.BACK_COOKIE,"list.do?&pageNo="+pageNo);
		model.addAttribute("listOauth", listOauth);
		model.addAttribute("page", page);
		return view("/weixin/oauth/oauth_list");
	}

	/**
	 * 持久化新增授权
	 * @param oauthEntity 授权实体
	 * @param request
	 * @param response
	 */
	@RequestMapping("/save")
	@ResponseBody
	public void save(@ModelAttribute OauthEntity oauthEntity,HttpServletRequest request,HttpServletResponse response){
		//若授权实体为空
		if(oauthEntity == null){
			this.outJson(response, ModelCode.WEIXIN_OAUTH,false,null);
			return ;
		}
		WeixinEntity weixin = this.getWeixinSession(request);
		//若微信或者微信相关数据不存在
		if(weixin == null || weixin.getWeixinId()<=0){
			this.outJson(response, ModelCode.WEIXIN_OAUTH, false, this.getResString("weixin.not.found"));
			return;
		}
		//获取微信ID
		int weixinId = weixin.getWeixinId();
		//获取应用ID
		int appId = this.getAppId(request);
		oauthEntity.setOauthAppId(appId);//应用ID
		oauthEntity.setOauthWeixinId(weixinId);//微信ID
		//保存授权
		this.oauthBiz.saveEntity(oauthEntity);
		this.outJson(response, ModelCode.WEIXIN_OAUTH,true,null);
	}
	
	/**
	 * 更新授权信息
	 * @param oauthEntity 授权实体
	 * @param request
	 * @param response
	 */
	@RequestMapping("/update")
	@ResponseBody
	public void update(@ModelAttribute OauthEntity oauthEntity,HttpServletRequest request,HttpServletResponse response){
		//授权若为空或者授权ID为0
		if(oauthEntity == null || oauthEntity.getOauthId() <= 0){
			this.outJson(response, ModelCode.WEIXIN_OAUTH,false,null);
			return ;			
		}
		//更新授权信息
		this.oauthBiz.updateEntity(oauthEntity);
		//读取cookie
		String url = this.getCookie(request, CookieConstEnum.BACK_COOKIE);
		this.outJson(response, ModelCode.WEIXIN_OAUTH,true,url);
	}

	
	/**
	 * 删除授权
	 * @param response
	 * @param request
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public void delete(HttpServletResponse response,HttpServletRequest request){	
		//得到需要批量删除的授权ID数组集合
		String[] oauthIds = request.getParameterValues("oauthIds");
		//判断数组是否为空
		if(StringUtil.isBlank(oauthIds)){
			this.outJson(response, ModelCode.WEIXIN_OAUTH,false,null);
			return;
		}
		//判断字符串数组是否转成integer型数组
		if(!StringUtil.isIntegers(oauthIds)){
			this.outJson(response, ModelCode.WEIXIN_OAUTH,false,null);
			return;
		}
		//得到ID数组并将字符串数组转化为int型数组
		int[] ids = StringUtil.stringsToInts(oauthIds);
		//根据ID批量删除
		oauthBiz.deleteByIds(ids);
		//返回json数据
		this.outJson(response, ModelCode.WEIXIN_OAUTH,true,null);
	}
	
}
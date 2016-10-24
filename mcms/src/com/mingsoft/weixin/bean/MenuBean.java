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

import java.util.ArrayList;
import java.util.List;

/**
 * >铭飞科技
 * Copyright: Copyright (c) 2014 - 2015
 * @author 史爱华
 * Comments:
 * @see http://mp.weixin.qq.com/wiki/13/43de8269be54a0a6f64413e4dfa94f39.html    
 * 数据格式：{
     "button":[
     {	
          "type":"click",
          "name":"今日歌曲",
          "key":"V1001_TODAY_MUSIC"
      },
      {
           "name":"菜单",
           "sub_button":[
           {	
               "type":"view",
               "name":"搜索",
               "url":"http://www.soso.com/"
            },
            {
               "type":"view",
               "name":"视频",
               "url":"http://v.qq.com/"
            },
            {
               "type":"click",
               "name":"赞一下我们",
               "key":"V1001_GOOD"
            }]
       }]
 }
 * Create Date:2015-4-1
 * Modification history:
 */
public class MenuBean {
	
	private List button = new ArrayList();
	
	public List getButton() {
		return button;
	}

	public void setButton(List button) {
		this.button = button;
	}
	
	
	/**
	 * 含有“key“的参数类型
	 */
	class Label{
		
		private String key;
		
		
		private String url;
		/**
		 * 菜单的名称
		 */
		private String name;
		/**
		 * 菜单的类型
		 */
		private String type;
		
		public Label(String key,String name,String type,String url){
			
		}

		public String getKey() {
			return key;
		}
		public void setKey(String key) {
			this.key = key;
		}
		public String getUrl() {
			return url;
		}
		public void setUrl(String url) {
			this.url = url;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getType() {
			return type;
		}
		public void setType(String type) {
			this.type = type;
		}
		
		
		
	}
	
	
	
	/**
	 * 含有子菜单的菜单类型
	 */
	class SubMenuBean {
		/**
		 * 菜单的子菜单
		 */
		private List sub_button = new ArrayList();
		/**
		 * 菜单的名称
		 */
		private String name;
		
		public SubMenuBean(String name){
		}
		
		public List getSub_button() {
			return sub_button;
		}

		public void setSub_button(List sub_button) {
			this.sub_button = sub_button;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
	}
	
}
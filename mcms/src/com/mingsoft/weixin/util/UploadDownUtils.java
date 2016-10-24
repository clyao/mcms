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

package com.mingsoft.weixin.util;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mingsoft.util.StringUtil;
import com.mingsoft.weixin.http.HttpClientConnectionManager;


/**
 * mswx-铭飞微信酒店预订平台</b>
 * Copyright: Copyright (c) 2014 - 2015
 * Company:景德镇铭飞科技有限公司
 * @author wangtp
 * @version 300-001-001
 * 版权所有 铭飞科技
 * Comments: 微信上传下载接口
 * Create Date:2014-5-12
 * Modification history:
 */
public class UploadDownUtils extends BaseUtils{
	/**
	 * 上传图文素材
	 */
	public final static String UPLOAD_NEWS_MEDIA ="https://api.weixin.qq.com/cgi-bin/media/uploadnews?access_token=";

	/** 日志 */
	public static final transient Logger log = Logger.getLogger(UploadDownUtils.class);

	/**
	 * 上传、下载的文件类型为图片</br>
	 * 注意:图片类型只支持jpg格式,最大为128K</br>
	 */
	public static final String TYPE_IMAGE = "image";
	
	/**
	 *  语音（voice）</br>
	 *  最大:256K，播放长度不超过60s</br>
	 *  支持AMRMP3格式 </br>
	 */
	public static final String TYPE_VOICE = "voice";
	
	/**
	 * 视频</br>
	 * 最大:1MB，支持MP4格式</br>
	 */
	public static final String TYPE_VIDEO = "video";
	
	/**
	 * 缩略图（thumb，主要用于视频与音乐格式的缩略图）</br>
	 * 最大64KB，支持JPG格式</br>
	 */
	public static final String TYPE_THUMB = "thumb";
	
	/**
	 * @param appid 微信应用编号
	 * @param secret 微信授权编号
	 */
	public UploadDownUtils(String appid, String secret) {
		super(appid, secret);
	}

	/*
	 * 注意事项 上传的多媒体文件有格式和大小限制，如下： 图片（image）: 128K，支持JPG格式 
	 * 语音（voice）：256K，播放长度不超过60s，支持AMRMP3格式 视频（video）：1MB，支持MP4格式 
	 * 缩略图（thumb）：64KB，支持JPG格式
	 * 媒体文件在后台保存时间为3天，即3天后media_id失效。对于需要重复使用的多媒体文件，可以每3天循环上传一次，更新media_id。
	 */

	/**
	 * 调用微信公共平台 多媒体上传接口 上传文件
	 * @param access_token 调用接口凭证
	 * @param msgType 媒体文件类型，分别有图片（image）、语音（voice）、视频（video）和缩略图（thumb）
	 * @param localFile 文件路径
	 * @return
	 */
	@Deprecated
	public static String uploadMedia(String access_token, String msgType,String localFile) {
		String media_id = null;
		String url = "http://file.api.weixin.qq.com/cgi-bin/media/upload?access_token="+ access_token + "&type=" + msgType;
		String local_url = localFile;
		try {
			File file = new File(local_url);
			if (!file.exists() || !file.isFile()) {
				log.error("文件路径错误==" + local_url);
				return null;
			}
			URL urlObj = new URL(url);
			HttpURLConnection con = (HttpURLConnection) urlObj.openConnection();
			con.setRequestMethod("POST"); // 以Post方式提交表单，默认get方式
			con.setDoInput(true);
			con.setDoOutput(true);
			con.setUseCaches(false); // post方式不能使用缓存
			// 设置请求头信息
			con.setRequestProperty("Connection", "Keep-Alive");
			con.setRequestProperty("Charset", "UTF-8");

			// 设置边界
			String BOUNDARY = "----------" + System.currentTimeMillis();
			con.setRequestProperty("content-type",
					"multipart/form-data; boundary=" + BOUNDARY);
			// con.setRequestProperty("Content-Type",
			// "multipart/mixed; boundary=" + BOUNDARY);
			// con.setRequestProperty("content-type", "text/html");
			// 请求正文信息

			// 第一部分：
			StringBuilder sb = new StringBuilder();
			sb.append("--"); // ////////必须多两道线
			sb.append(BOUNDARY);
			sb.append("\r\n");
			sb.append("Content-Disposition: form-data;name=\"file\";filename=\""+ file.getName() + "\"\r\n");
			sb.append("Content-Type:application/octet-stream\r\n\r\n");
			byte[] head = sb.toString().getBytes("utf-8");
			// 获得输出流
			OutputStream out = new DataOutputStream(con.getOutputStream());
			out.write(head);

			// 文件正文部分
			DataInputStream in = new DataInputStream(new FileInputStream(file));
			int bytes = 0;
			byte[] bufferOut = new byte[1024];
			while ((bytes = in.read(bufferOut)) != -1) {
				out.write(bufferOut, 0, bytes);
			}
			in.close();
			// 结尾部分
			byte[] foot = ("\r\n--" + BOUNDARY + "--\r\n").getBytes("utf-8");// 定义最后数据分隔线
			out.write(foot);
			out.flush();
			out.close();
			/**
			 * 读取服务器响应，必须读取,否则提交不成功
			 */
			// con.getResponseCode();
			try {
				// 定义BufferedReader输入流来读取URL的响应
				StringBuffer buffer = new StringBuffer();
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(con.getInputStream(), "UTF-8"));
				String line = null;
				while ((line = reader.readLine()) != null) {
					// System.out.println(line);
					buffer.append(line);
				}
				String respStr = buffer.toString();
				log.debug("==respStr==" + respStr);
				try {
					JSONObject dataJson = JSONObject.parseObject(respStr);
					
					media_id = dataJson.getString("media_id");
				} catch (Exception e) {
					log.error("==respStr==" + respStr, e);
					try {
						JSONObject dataJson = JSONObject.parseObject(respStr);
						return dataJson.getString("errcode");
					} catch (Exception e1) {
					}
				}
			} catch (Exception e) {
				log.error("发送POST请求出现异常！" + e);
			}
		} catch (Exception e) {
			log.error("调用微信多媒体上传接口上传文件失败!文件路径=" + local_url);
			log.error("调用微信多媒体上传接口上传文件失败!", e);
		} finally {
		}
		return media_id;
	}
	
	/**
	 * 上传图文素材
	 * @param newsUploadBeanList
	 * @return 上传图文返回的json数据 {"type":"news","media_id":"CsEf3ldqkAYJAU6EJeIkStVDSvffUJ54vqbThMgplD-VJXXof6ctX5fI6-aYyUiQ","created_at":1391857799}
	 * type:媒体文件类型;media_id:媒体文件/图文消息上传后获取的唯一标识;created_at:媒体文件上传时间
	 */
	public String uploadNews(String newsUploadJson){
		String accessToken = getAccessToken();
		if(!StringUtil.isBlank(accessToken)){
			HttpPost httpPost = HttpClientConnectionManager.getPostMethod(UPLOAD_NEWS_MEDIA +accessToken);
			try {
				httpPost.setEntity(new StringEntity(newsUploadJson, "UTF-8"));
				HttpResponse response  = HTTPCLIENT.execute(httpPost);
				String jsonStr  = EntityUtils.toString(response.getEntity(),"utf-8");
				logger.debug(jsonStr);
				JSONObject object = JSON.parseObject(jsonStr);			
				return object.getString("media_id");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		}			
		return null;
	}
	
	
	/**
	 * 调用微信公共平台 多媒体上传接口 上传文件
	 * @param access_token 调用接口凭证
	 * @param msgType 媒体文件类型，分别有图片（image）、语音（voice）、视频（video）和缩略图（thumb）
	 * @param localFile 文件路径
	 * @return 错误码
	 */
	public String uploadMedia(String msgType,String localFile,HttpServletRequest request) {
		String media_id = null;
		String url = "http://file.api.weixin.qq.com/cgi-bin/media/upload?access_token="+ getAccessToken() + "&type=" + msgType;
		String local_url = this.getRealPath(request, localFile);
//		String local_url = localFile;
		try {
			File file = new File(local_url);
			if (!file.exists() || !file.isFile()) {
				log.error("文件路径错误==" + local_url);
				return null;
			}
			URL urlObj = new URL(url);
			HttpURLConnection con = (HttpURLConnection) urlObj.openConnection();
			con.setRequestMethod("POST"); // 以Post方式提交表单，默认get方式
			con.setDoInput(true);
			con.setDoOutput(true);
			con.setUseCaches(false); // post方式不能使用缓存
			// 设置请求头信息
			con.setRequestProperty("Connection", "Keep-Alive");
			con.setRequestProperty("Charset", "UTF-8");

			// 设置边界
			String BOUNDARY = "----------" + System.currentTimeMillis();
			con.setRequestProperty("content-type","multipart/form-data; boundary=" + BOUNDARY);
			// 第一部分：
			StringBuilder sb = new StringBuilder();
			sb.append("--"); // ////////必须多两道线
			sb.append(BOUNDARY);
			sb.append("\r\n");
			sb.append("Content-Disposition: form-data;name=\"file\";filename=\""+ file.getName() + "\"\r\n");
			sb.append("Content-Type:application/octet-stream\r\n\r\n");
			byte[] head = sb.toString().getBytes("utf-8");
			// 获得输出流
			OutputStream out = new DataOutputStream(con.getOutputStream());
			out.write(head);

			// 文件正文部分
			DataInputStream in = new DataInputStream(new FileInputStream(file));
			int bytes = 0;
			byte[] bufferOut = new byte[1024];
			while ((bytes = in.read(bufferOut)) != -1) {
				out.write(bufferOut, 0, bytes);
			}
			in.close();
			// 结尾部分
			byte[] foot = ("\r\n--" + BOUNDARY + "--\r\n").getBytes("utf-8");// 定义最后数据分隔线
			out.write(foot);
			out.flush();
			out.close();
			/**
			 * 读取服务器响应，必须读取,否则提交不成功
			 */
			// con.getResponseCode();
			try {
				// 定义BufferedReader输入流来读取URL的响应
				StringBuffer buffer = new StringBuffer();
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(con.getInputStream(), "UTF-8"));
				String line = null;
				while ((line = reader.readLine()) != null) {
					// System.out.println(line);
					buffer.append(line);
				}
				String respStr = buffer.toString();
				log.debug("==respStr==" + respStr);
				try {
					JSONObject dataJson = JSONObject.parseObject(respStr);
					
					media_id = dataJson.getString("media_id");
				} catch (Exception e) {
					log.error("==respStr==" + respStr, e);
					try {
						JSONObject dataJson = JSONObject.parseObject(respStr);
						return dataJson.getString("errcode");
					} catch (Exception e1) {
					}
				}
			} catch (Exception e) {
				log.error("发送POST请求出现异常！" + e);
			}
		} catch (Exception e) {
			log.error("调用微信多媒体上传接口上传文件失败!文件路径=" + local_url);
			log.error("调用微信多媒体上传接口上传文件失败!", e);
		} finally {
		}
		return media_id;
	}	
	
	
	/**
	 * 调用微信公共平台 下载多媒体文件
	 * 
	 * @return
	 */
	@Deprecated
	public static String downMedia(String access_token, String msgType, String media_id,String path) {
		String localFile = null;
//		SimpleDateFormat df = new SimpleDateFormat("/yyyyMM/");
		try {
			String url = "http://file.api.weixin.qq.com/cgi-bin/media/get?access_token="+ access_token + "&media_id=" + media_id;
			// log.error(path);
			// 图片未保存 下载保存
			URL urlObj = new URL(url);
			HttpURLConnection conn = (HttpURLConnection) urlObj.openConnection();
			conn.setRequestMethod("GET");
			conn.setConnectTimeout(5000);
			String xx = conn.getHeaderField("Content-disposition");
			try {
				log.debug("===调用微信公共平台 下载多媒体文件+==返回文件信息==" + xx);
				if (xx == null) {
					InputStream in = conn.getInputStream();
					BufferedReader reader = new BufferedReader(
							new InputStreamReader(in, "utf-8"));
					String line = null;
					String result = null;
					while ((line = reader.readLine()) != null) {
						if (result == null) {
							result = line;
						} else {
							result += line;
						}
					}
					System.out.println(result);
					JSONObject dataJson = JSONObject.parseObject(result);
					return dataJson.getString("errcode");
				}
			} catch (Exception e) {
			}
			if (conn.getResponseCode() == 200) {
				String Content_disposition = conn.getHeaderField("Content-disposition");
				InputStream inputStream = conn.getInputStream();
				// // 文件大小
				// Long fileSize = conn.getContentLengthLong();
				// 文件夹 根目录+相对路径
				String savePath = path +"/"+ msgType;
				// 文件名
				String fileName = StringUtil.getDateSimpleStr() + Content_disposition.substring(Content_disposition.lastIndexOf(".")).replace("\"", "");
				// 创建文件夹
				File saveDirFile = new File(savePath);
				if (!saveDirFile.exists()) {
					saveDirFile.mkdirs();
				}
				// 检查目录写权限
				if (!saveDirFile.canWrite()) {
					log.error("目录没有写权限，写入文件失败");
					throw new Exception();
				}
				// System.out.println("------------------------------------------------");
				// 文件保存目录路径
				File file = new File(saveDirFile+"/"+fileName);
				FileOutputStream outStream = new FileOutputStream(file);
				int len = -1;
				byte[] b = new byte[1024];
				while ((len = inputStream.read(b)) != -1) {
					outStream.write(b, 0, len);
				}
				outStream.flush();
				outStream.close();
				inputStream.close();
				// 服务器访问路径
				localFile = fileName;
			}
		} catch (Exception e) {
			log.error("调用微信公共平台 下载多媒体文件失败!", e);
		} finally {

		}
		return localFile;
	}


	/**
	 * 调用微信公共平台 下载多媒体文件
	 * 
	 * @return
	 */
	public String downMedia(String msgType, String media_id,String path) {
		String localFile = null;
//		SimpleDateFormat df = new SimpleDateFormat("/yyyyMM/");
		try {
			String url = "http://file.api.weixin.qq.com/cgi-bin/media/get?access_token="+ getAccessToken() + "&media_id=" + media_id;
			// log.error(path);
			// 图片未保存 下载保存
			URL urlObj = new URL(url);
			HttpURLConnection conn = (HttpURLConnection) urlObj.openConnection();
			conn.setRequestMethod("GET");
			conn.setConnectTimeout(5000);
			String xx = conn.getHeaderField("Content-disposition");
			try {
				log.debug("===调用微信公共平台 下载多媒体文件+==返回文件信息==" + xx);
				if (xx == null) {
					InputStream in = conn.getInputStream();
					BufferedReader reader = new BufferedReader(
							new InputStreamReader(in, "utf-8"));
					String line = null;
					String result = null;
					while ((line = reader.readLine()) != null) {
						if (result == null) {
							result = line;
						} else {
							result += line;
						}
					}
					System.out.println(result);
					JSONObject dataJson = JSONObject.parseObject(result);
					return dataJson.getString("errcode");
				}
			} catch (Exception e) {
			}
			if (conn.getResponseCode() == 200) {
				String Content_disposition = conn.getHeaderField("Content-disposition");
				InputStream inputStream = conn.getInputStream();
				// // 文件大小
				// Long fileSize = conn.getContentLengthLong();
				// 文件夹 根目录+相对路径
				String savePath = path +"/"+ msgType;
				// 文件名
				String fileName = StringUtil.getDateSimpleStr() + Content_disposition.substring(Content_disposition.lastIndexOf(".")).replace("\"", "");
				// 创建文件夹
				File saveDirFile = new File(savePath);
				if (!saveDirFile.exists()) {
					saveDirFile.mkdirs();
				}
				// 检查目录写权限
				if (!saveDirFile.canWrite()) {
					log.error("目录没有写权限，写入文件失败");
					throw new Exception();
				}
				// System.out.println("------------------------------------------------");
				// 文件保存目录路径
				File file = new File(saveDirFile+"/"+fileName);
				FileOutputStream outStream = new FileOutputStream(file);
				int len = -1;
				byte[] b = new byte[1024];
				while ((len = inputStream.read(b)) != -1) {
					outStream.write(b, 0, len);
				}
				outStream.flush();
				outStream.close();
				inputStream.close();
				// 服务器访问路径
				localFile = fileName;
			}
		} catch (Exception e) {
			log.error("调用微信公共平台 下载多媒体文件失败!", e);
		} finally {

		}
		return localFile;
	}
	
}
<!DOCTYPE html>
<html lang="en">
<head>
	<#include "${managerViewPath}/include/meta.ftl"/>
	<style>
		.ztree{
			margin-top:5px;
		}
		body .ms-content-body{
			width:85%;
		}
		.ms-content-body-title{
			overflow:hidden;
		}
		body .ms-content-body-panel{
			margin:0;
			padding:0;
			overflow-y: hidden;
		}
	</style>
</head>
<body>
	<@ms.content>
		<@ms.contentMenu>
			<div class="ms-content-body-title">
        		<span>${weixinName?default('暂无')}</span>    
			</div>
		 	<ul id="menuTree" class="ztree">
			</ul> 
			<!-- 树形模块菜单结束 -->
		</@ms.contentMenu>
		<@ms.contentBody style="width:85%">
			<@ms.contentPanel>
				<iframe src="" style="width:100%;maring:0;padding:0;border:none;height:100%;background-image: url(${static}/skin/manager/${manager_ui}/images/loading.gif);  background-repeat: no-repeat;  background-position: center;" id="listFrame" target="listFrame" ></iframe>
			</@ms.contentPanel>
		</@ms.contentBody>
	</@ms.content>
	
</body>
</html>
<script>
	$(function(){
		$.fn.zTree.init($("#menuTree"),setting,nodes);
	})
	
	/******设置节点信息开始******/
	//设置信息
	var setting = {
		edit: {
			enable: false,
			showRemoveBtn: false,
			showRenameBtn: false,
			drag: {
				prev: true,
				next: true,
				inner: true,
				isMove: false
			}
		},
		data: {
			simpleData: {
				enable: true
			}
		},
		view: {
			showLine: false,
			showIcon: false
		}
	};
	var nodes = [
		{ "id":0, "name":"微信设置", "url":"", "click":"$('#listFrame').attr('src','${managerPath}/weixin/'+${weixinId}+'/edit.do')"},
		{ "id":1, "name":"微信菜单", "url":"", "click":"$('#listFrame').attr('src','${managerPath}/weixin/menu/list.do')"},
		{ "id":1, "name":"素材列表", "url":"", "click":"$('#listFrame').attr('src','${managerPath}/weixin/news/list.do')"},
		{ "id":1, "name":"微信用户", "url":"", "click":"$('#listFrame').attr('src','${managerPath}/weixin/weixinPeople/list.do')"},
		{ "id":1, "name":"群发消息", "url":"", "click":"$('#listFrame').attr('src','${managerPath}/weixin/message/index.do')"},
		{ "id":1, "name":"关注回复", "url":"", "click":"$('#listFrame').attr('src','${managerPath}/weixin/subscribe/subscribe.do')"},
		{ "id":1, "name":"关键字回复", "url":"", "click":"$('#listFrame').attr('src','${managerPath}/weixin/messagekey/list.do')"}
	]
	/******设置节点信息结束******/
</script>
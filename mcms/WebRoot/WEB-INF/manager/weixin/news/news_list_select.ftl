<!DOCTYPE html>
<html lang="en">
<head>
<title>铭飞管理系统</title>
<#include "${managerViewPath}/include/meta.ftl"/>
<style>
	body{
		padding:0px;
		margin:0px;
	}
	body .row{
		padding:0 15px;
	}
	.pagination{
		margin: 0 70px;
		float: right;
	}
	#first_col{
		display: inline-block; 
		zoom: 1; 
		*display: inline; 
	}
	#second_col{
		margin-left: 15px;
		display: inline-block; 
		zoom: 1; 
		*display: inline; 
	}
	ul{
		padding: 0;
		margin: 0;
	}
	li{
		list-style-type: none;
	}
	.sub-msg-item {
		padding: 12px 14px;
		overflow: hidden;
		zoom: 1;
		border-top: 1px solid #c6c6c6;
	}
	.thumb {
		float: right;
		font-size: 0;
	}
	.thumb .default-tip {
		font-size: 16px;
		color: #c0c0c0;
		width: 70px;
		line-height: 70px;
		border: 1px solid #b2b8bd;
	}
	.thumb img {
		width: 70px;
		height: 70px;
		border: 1px solid #b2b8bd;
	}
	.sub-msg-item .msg-t {
		margin-left: 0;
		margin-right: 85px;
		margin-top: 0;
		padding-left: 4px;
		padding-top: 12px;
		line-height: 24px;
		max-height: 58px;
		font-size: 14px;
		overflow: hidden;
	}
	.msg-item-wrapper{
		position:relative;
		margin-bottom:26px;
		border:1px solid #b8b8b8;
		background-color:#f4f4f4;
		box-shadow:0 2px 2px rgba(0,0,0,0.1);
		border-radius:5px;
		-webkit-border-radius:5px;
		-moz-border-radius:5px;
	}
	.msg-item{
		padding:2px 0;
		background-color:#fff;
		border-bottom:1px solid #ccc;
		
	}
	.msg-preview .msg-item{
		border-radius:5px;
		box-shadow:none;
		moz-border-radius:5px;
		moz-box-shadow:none;
	
	}
	.msg-t{
		margin:6px 0 0;
		padding-left:14px;
		line-height:28px;
		max-height:56px;
		overflow:hidden;
		font-size:16px;
		font-weight:400;
		letter-spacing:1px;
		font-weight:bold;
	}
	.msg-meta{
		margin:0 14px 6px;
		font-size:13px;
	}
	.cover{
		margin:0 14px 12px;
		position:relative;
		font-size:0;
		height:160px;
		overflow:hidden;
	}
	.default-tip{
		display:block;
		text-align:center;
		background-color:#ececec;
		text-shadow:0 1px 0 #fff;
		-webkit-text-shadow:0 1px 0 #fff;
		-moz-text-shadow:0 1px 0 #fff;
	}
	.cover .default-tip{
		font-size:22px;
		color:#aaa;
		line-height:160px;
	}
	.cover img{width:100%;height:100%}
	.msg-text{
		margin:0 14px;
		font-size:14px;
		line-height:1.6;
		padding-bottom:8px;
		text-align:left;
	}
	.msg-preview .msg-opr{display:none;}
	.msg-preview .msg-opr{display:none;}
	.msg-opr-list{
		padding:10px 0;
		letter-spacing:-5px;
		border-radius:0 0 5px 5px;
		-webkit-border-radius:0 0 5px 5px;
		-moz-border-radius:0 0 5px 5px;
	}
	.opr-item{
		width:50%;font-size:14px;letter-spacing:normal
	}
	.edit-btn{border-right:1px solid #a3a3a3;}
	.opr-btn{padding:2px 0;}
	.opr-btn:hover .edit-icon{background-position:-28px -139px;}
	.opr-btn:hover .del-icon{background-position:-28px -195px;}
	.block{display:block;}
	.b-dib{display:inline-block;zoom:1;*display:inline;}
	.opr-icon{width:18px;height:18px;background:transparent url('http://wxj.weixinjia.net/image/admin/icon18.png') no-repeat;display:block;cursor:pointer;}
	.edit-icon{background-position:0 -139px;}
	.del-icon{background-position:0 -195px;}
	.tc{text-align:center;}
	.th{line-height:150px;overflow:hidden;}
	.vm{vertical-align:middle;}
	.msg-hover-mask{
		display:none;
		position:absolute;
		top:0;
		width:100%;
		height:100%;
		background:rgba(0,0,0,0.6)!important;
		filter:progid:dximagetransform.microsoft.gradient(GradientType=0,startColorstr='#60000000',endColorstr='#60000000');
	}
	.msg-mask{display:none;position:absolute;top:0;width:100%;height:100%;background:rgba(0,0,0,0.6)!important;filter:progid:dximagetransform.microsoft.gradient(GradientType=0,startColorstr='#60000000',endColorstr='#60000000');}
	.msg-selected-tip{
		width:100%;height:100%;
		position:absolute;
		background:transparent url('${base}/images/news/selected-icon.png') no-repeat 50% 50%;
	}
	.dib{display:inline-block;}
	.msg-editer-wrapper{position:relative;}
	.msg-editer{
		background-color:#f8f8f8;
		border:1px solid #b8b8b8;
		border-radius:5px;
		box-shadow:0 2px 2px rgba(0,0,0,0.1);
		moz-border-radius:5px;
		moz-box-shadow:0 2px 2px rgba(0,0,0,0.1);
		padding:14px 0 20px 20px;
		webkit-border-radius:5px;
		webkit-box-shadow:0 2px 2px rgba(0,0,0,0.1);
	}
	.msg-arrow{
		border-style:dashed solid dashed dashed;
		font-size:0;height:0;width:0;
	}
	.a-out{
		border-color:transparent #b8b8b8 transparent transparent;
		border-width:12px 14px 12px 0;
		left:-13px;
		top:43px;
	}
	.a-in{
		border-color:transparent #f8f8f8 transparent transparent;
		border-width:11px 13px 11px 0;
		left:-12px;
		top:44px;
	}
	.abs{position:absolute;}
	.rel{position:relative;}
	.msg-input,.cover-area,.msg-txta{
		background-color:#fff;
		border:1px solid #d3d3d3;
		color:#666;
		max-width:480px;
		padding:2px 8px;
		width:480px;
		-webkit-border-radius:4px;
		-moz-border-radius:4px;
		border-radius:4px;
	}
	.cover-area{max-width:496px;padding:0;width:496px;position:relative;}
	.cover-hd{padding:2px 8px 3px;position:relative;}
	.upload-tip{color:#666;font-size:12px;line-height:28px;text-align:right;position:absolute;top:3px;right:8px;}
	.cover-bd{border-top:1px solid #ccc;font-size:0;overflow:hidden;padding:8px;}
	.cover-bd img{width:100px;}
	.cover-del{font-size:14px;margin-left:5px;}
	.url-link{margin-bottom:6px;}
	.container{margin:0 auto;width:760px;}
	.containerBox{
		margin-top:50px;
		box-shadow:0 3px 3px #DDD;
		-moz-box-shadow:0 3px 3px #ddd;
		-webkit-box-shadow:0 3px 3px #DDD;
		border-radius:3px;
		-moz-border-radius:3px;
		-webkit-border-radius:3px;
		background-color:white;
		border:1px solid lightGrey;
	}
	.containerBox .boxHeader{
		height:30px;
		padding-left:20px;
		border-bottom:1px solid lightGrey;
		background-color:whiteSmoke;
	}
	.containerBox .boxHeader h4{
		margin-top:0;padding-top:5px;
	}
	.containerBox .sideBar{
		width:180px;float:left;padding:10px 0;
	}
	.containerBox .content{
		width:737px;
		float:right;
		margin-left:-2px;
		border-left:1px solid lightGrey;
		padding-left:20px;
	}
	.page-bd{padding-top:20px;}
	.menu{
		display:block;
		bottom:0;
		z-index:7;
		overflow:auto;
	}
	.clr{clear:both;}
	.vt{vertical-align:top;}
	.msg-col{width:350px;font-size:14px;}
	.msg-hover .msg-hover-mask{display:block;}
	.msg-hover-mask{
		display:none;
		position:absolute;
		top:0;
		width:100%;
		height:100%;
		background:rgba(0,0,0,0.6)!important;
		filter:progid:dximagetransform.microsoft.gradient(GradientType=0,startColorstr='#60000000',endColorstr='#60000000');
	}
	.msg-selected .msg-mask{display:block;}
	.msg-item-wrapper{
		position:relative;
		margin-bottom:26px;
		border:1px solid #B8B8B8;
		background-color:#F4F4F4;
		box-shadow:0 2px 2px rgba(0,0,0,0.1);
		-webkit-box-shadow:0 2px 2px rgba(0,0,0,0.1);
		-moz-box-shadow:0 2px 2px rgba(0,0,0,0.1);
		border-radius:5px;
		-webkit-border-radius:5px;
		-moz-border-radius:5px;
	}
	.add-access{
		margin:0 2px 26px;
		line-height:150px;
		border:3px dotted #B8B8B8;
		border-radius:5px;
		-webkit-border-radius:5px;
		-moz-border-radius:5px;
		background-color:white;
	}
	.page-sub-hd{
		font-size:16px;
		font-weight:700;
	}
	.msg-opr-list{margin-bottom:0;}
	.group::after{content:"\200B";display:block;height:0;clear:both;}
	.page-nav{margin-top:-40px;zoom:1;}
	.sub-msg-item{padding:12px 14px;overflow:hidden;zoom:1;border-top:1px solid #c6c6c6;}
	.thumb{float:right;font-size:0;}
	.thumb .default-tip{font-size:16px;color:#c0c0c0;width:70px;line-height:70px;border:1px solid #b2b8bd;}
	.thumb img{width:70px;height:70px;border:1px solid #b2b8bd;}
	.add-btn{
		height:90px;
		line-height:90px;
		margin:0 18px;
		color:#b5b5b5;
		background:transparent url('${base}/images/news/appmsg-icon.png') no-repeat 50% -242px;
	}
	.multi-access{background-position:50% -342px;}	
	.form-control{width:auto;}
</style>
</head>
<body>
		<!--列表开始-->
		<div class="row" id="newsListContainer">
			<div class="tool_bar">				
			</div>
			<h3 class="page-sub-hd">图文列表(共${count}条)</h3>
			<div class="page-bd">
   				<div class="tj msg-list">
   					<!-- 偶数内容 -->
   					<div id="first_col" class="b-dib vt msg-col">
   						<div class="tc add-access">
	   						<a id="addArticle" href="javascript:(0)" class="dib vm add-btn">+单图文消息</a>         
							<a id="addMulArticle" href="javascript:(0)" class="dib vm add-btn multi-access">+多图文消息</a>
   						</div>
   						<#if newsList?has_content>
   							<#list newsList as list>		
   								<#if list.newsType==0>
		   						<!-----单图文内容开始------>
				   				<div class="msg-item-wrapper" data-id="${list.newsId}">              
					   				<div class="msg-item multi-msg">
					   					<div class="appmsgItem">
				   							<h4 class="msg-t"><a href="" class="i-title" target="_blank"><#if list.newsMasterArticle?has_content>${list.newsMasterArticle.basicTitle?default('')}</#if></a></h4>           
				   							<p class="msg-meta"><span class="msg-date">${list.newsDateTime?string('MM月dd日')}</span></p>
											<div class="cover">
												<p class="default-tip" style="display:none">封面图片</p>
												<img src="${list.newsMasterArticle.basicThumbnails?default('')}" class="i-img" style="">
											</div>
											<p class="msg-text">${list.newsMasterArticle.basicDescription?default('')}</p>
											<div class="msg-hover-mask"></div> 
											<div class="msg-mask"><span class="dib msg-selected-tip"></span></div>          
										</div>
			   						</div>               
								</div>
								</#if>
							</#list>
						</#if>
						<!-----单图文内容结束------>
   					</div>
   					<!-- 奇数内容 -->
   					<div id="second_col" class="b-dib vt msg-col">
   						<#if newsList?has_content>
   							<#list newsList as news>		
   								<#if news.newsType==1 &&news.newsMasterArticle?has_content>
				   				<div class="msg-item-wrapper" data-id="${news.newsId}">
					   				<div class="msg-item multi-msg">
					   					<div class="appmsgItem">
				   							<h4 class="msg-t">
		                                    	<a href="" class="i-title" target="_blank">${news.newsMasterArticle.basicTitle}</a>
		                                    </h4>           
				   							<p class="msg-meta"><span class="msg-date">${news.newsDateTime?string('MM月dd日')}</span></p>
											<div class="cover">
												<p class="default-tip" style="display:none">封面图片</p>
												<img src="${news.newsMasterArticle.basicThumbnails?default('')}" class="i-img" style="">
											</div>
											<p class="msg-text"></p>   
											<div class="msg-hover-mask"></div> 
											<div class="msg-mask"><span class="dib msg-selected-tip"></span></div>       
										</div>
										<#if news.childs?has_content>
				       						<#list news.childs as listChild>
											<div class="rel sub-msg-item appmsgItem">              
												<span class="thumb"><img src="${listChild.basicThumbnails?default('')}" class="i-img" style=""></span>                 
												<h4 class="msg-t"><a href="#" target="_blank" class="i-title">${listChild.basicTitle?default('')}</a></h4>                
											</div>
										</#list>
				       					</#if>
			   						</div>    
								</div>
								<!-- 奇数内容结束 -->
								</#if>
							</#list>
						</#if>		
	   				</div>  				               
				</div>
   			</div>
   		</div>
   		<#if page?has_content>
			<@showPage page=page/>
		</#if>
</body>
</html>
<script>

$(function(){
	
	//点击添加进行单图文链接
	$("#addArticle").click(function(){
		parent.showAddMulArticle();
	});
	
	//点击添加进行多图文链接
	$("#addMulArticle").click(function(){
		parent.showAddArticle();
	});
	
	$(".msg-item-wrapper").hover(function(){
		$(this).addClass("msg-hover");	
	} , function(){
		$(this).removeClass("msg-hover");
	});
	
	$(".msg-item-wrapper").click(function(){
		$(".msg-item-wrapper").removeClass("msg-selected");
		$(this).addClass("msg-selected");
	});
	
	
});
//获取被选中图文的id
function getSelected(){
	if($(".msg-selected").length == 0){
		return null;
	}else{
		return $(".msg-selected").attr("data-id");
	}
}
</script>
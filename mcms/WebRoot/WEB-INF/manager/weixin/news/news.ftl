<!DOCTYPE html>
<html lang="en">
<head>
<#include "${managerViewPath}/include/meta.ftl"/>
</head>
<style>
	.font-size60{
		font-size:60px;
		font-family:Arial, Helvetica, sans-serif;
		color:#CCCCCC;
	}
	.font-size30{
		font-size:60px;
		font-family:Arial, Helvetica, sans-serif;
		color:#CCCCCC;
	}	
	.container{margin:0;padding:0;width:auto}
	.margin20{margin-top:20px;}
	.row {margin-left:0;margin-right:0;padding-bottom:4px;}
	hr{margin:0;padding:0;}
	b{
		display:block;
		font-weight: 400;
		font-style: normal;
	}
	i, cite, em, var, address, dfn {
		font-style:normal;
	}
	.title{
		font-weight: 400;
		font-style: normal;
		font-family: "Helvetica Neue","Hiragino Sans GB","Microsoft YaHei","微软雅黑","黑体",Arial,sans-serif;
		color: #222;
		font-size: 14px;
	}
	.nul{
		height:40px;
	}
	.out .newsMenu a{
		vertical-align: middle;
		margin-left: 10px;
		margin-right: 10px;
		color: #d9d9d9;
		font-size: 14px;
		line-height: normal;
		text-decoration: none;
	}
	.newsMenu{
		border: 2px dotted #d9dadc;
		text-align: center;
		margin-bottom: 20px;
		height: 85px;
		
	}
	.newsMenu_cen{
		display:block;
		width:295px;
		height:44px;
		position:absolute;
	}
	.singleNews{
		float:left;
		position:relative;
		top:13px;
		left:60px;
	}
	.manyNews{
		float:right;
		position:relative;
		top:15px;
		right:60px;
	}
	
	
	#heen a:hover{
		color: #b3b3b3;
		text-decoration: underline;
	}
	.newsMenu:hover{
		border-color: #b3b3b3;
	}
	
	.add_png1{
		width: 42px;
		height: 40px;
		vertical-align: middle;
		display: inline-block;
		overflow: hidden;
		background: url(../image/new.png);
		background-position: -70px -307px;
	}
	.only{
		
		height:223px;
		overflow: hidden;
		margin-bottom: 20px;
		border: 1px solid #e7e7eb;
		background-color: #fff;
		color: #666;
		display:none;
	}
	.onl_tit{
		overflow: hidden;
		margin-left: 15px;
		height: 34px;
		line-height: 33px;
		font-weight: 400;
		font-family: "Helvetica Neue","Hiragino Sans GB","Microsoft YaHei","微软雅黑","黑体",Arial,sans-serif;
		color: #222;
		font-size: 14px;
	}
	.onl_name{
		
		height: 160px;
		display: block;
		color: #c0c0c0;
		text-align: center;
		line-height: 160px;
		font-weight: 400;
		font-style: normal;
		background-color: #ececec;
		font-size: 16px;
		margin: 0px auto;
	}
	.onl_img{
		display:none;
	}
	.duo{
		
		border: 1px solid #e7e7eb;
		color: #8d8d8d;
		display:none;
	}
	.parentNews{
		position: relative;
		top: 24px;
		width:91%;
		height: 160px;
		display: block;
		color: #c0c0c0;
		text-align: center;
		line-height: 160px;
		font-size: 16px;
		margin: 0px auto;
		z-index: 1;
		background: rgb(236, 236, 236);
	
	}
	.duo_name_out{
		
		width:100%;
		height:160px;
		background: rgba(229,229,229,0.85)!important;
		position:absolute;
	    top: 0; 
		z-index:10000
	}
	.duo_tit{
		height: 41px;
		background: #5E5E5E;
		position: relative;
		top: -42px;
		display:block;
		line-height: 38px;
		text-align: left;
		padding-left: 15px;
		color: #fff;
	}
	
	.childNewes{
		
		position: relative;
		
	}
	.duo_div{
		
		height: 126px;
		border-top: 1px solid #e7e7eb;
	}
	.duo_div_out{
		position:relative;
		z-index: 1;
		
		height: 126px;
		bottom:54px;
		display: none;
		background: rgba(229,229,229,0.85)!important;
	}
	.duo_cen{
		width: 198px;
		height: 24px;
		line-height: 120px;
		margin-left: 15px;
	}
	.childNewes_img{
		display: block;
		color: #c0c0c0;
		text-align: center;
		line-height: 84px;
		background-color: #ececec;
		width: 78px;
		height: 78px;
		float: right;
		margin-top: 2px;
		margin-right: 15px;
	}
	.childNewes_i{
		background: url(../image/new.png);
		background-position: -70px 380px;
		width: 40px;
		height: 33px;
		display: block;
		margin: 20px auto;
	}
	.addChildNews{
		display: block;
		height: 70px;
		border: 2px dotted #d9dadc;
		margin: 20px 14px;
	}
	.addChildNews:hover{
		border-color: #b3b3b3;
	}
	.icon_pen_img{
		width: 20px;
		height: 20px;
		background: url(../image/base_z20273e.png);
		background-position:0 -5432px;
		display: block;
		margin: 0px auto;
		position: relative;
		top: 43px;
		float: left;
		left: 101px;
	}	
	/*添加多文章遮罩层*/
	.mask{
		width: 100%;
		height: 126px;
		background: rgba(229,229,229,0.85)!important;
		position: absolute;
		z-index: 10000;
	}
	.childNews{
		position:relative; 
		height:126px
	}
</style>

<body>
<!--顶部开始-->
<div class="container-fluid" id="newsSaveForm">
	<!---头部标题--->
	<div class="row margin20">
		<div class="col-md-10 col-xs-9">
			<h3 class="page-title bottomLine">素材管理&nbsp;<small><#if newsType == "0">添加单图文<#else>添加多图文</#if></small></h3>
		</div>
		<div class="col-md-2 col-xs-2 text-right">
			<button class="btn btn-default" role="button">返回素材列表</button>
		</div>
	</div>
	<hr>
	<!---添加素材开始--->
	<div class="row margin20">
		<div class="col-md-11">
            <!--==========左边==========-->
            <div class="col-md-4  col-xs-6">
            	<div class="title">图文消息列表(共1个)</div>
                <!--打开页面默认样式-->
                <div  class="newsMenu">
                    <span  class="add_cen" style="display:none;">
                        <i class="glyphicon glyphicon-plus font-size60" style="top:10px;" ></i>
                    </span>
                    <span id="heen" style="display:block;">
                        <a href="#">
                            <div class="img singleNews">
                                <i class ="add_png1"></i>
                                <b>单图文消息</b>
                            </div>
                        </a>
                        <a href="#">
                            <div class="img manyNews">
                                <i class="add_png1"></i>
                                <b>多图文消息</b>
                            </div>
                        </a>
                    </span>
                    <!--鼠标上移出现两个添加文章的图片-->
                </div>
                <!--单文章内容添加-->	
                <div class="only">
                    <p class="onl_tit">标题</p>
                    <div class="onl_name">
                        <img src="#" class="onl_img"/>
                        <i>封面图片</i>
                    </div>
                </div>
                <!--多文章内容添加-->
                <div class="duo">
                    <div class="parentNews" data-click="false">
                        <img src="#" class="onl_img"/>
                        <i>封面图片</i>
                        <p class="duo_tit">标题</p>
                        <div class="duo_name_out" data=1 style="display:none;">
            				<i class="pen_img glyphicon glyphicon-pencil" data-id='0'></i>
        				</div>                               
                    </div>
                    <div class="nul"></div>
                    <!--文章1-->
                    <div class="childNewes" data-id="0" data-click="false">                  
                        <div class="mask">
							<div class="col-md-5 col-xs-5"></div>
                            <i class="pen_img glyphicon glyphicon-pencil" style="top:54px;" data-id='1'></i>
                            <i class="glyphicon glyphicon-trash maskDel" style="top:54px;"></i>
                        </div>                                
                    </div>
                    <a href="#">
                        <!--点击添加文章div-->
                        <div class="addChildNews">
							<div class="col-md-5 col-xs-5"></div>
                            <i class="duo_pen_img glyphicon glyphicon-plus font-size30" ></i>
                        </div>
                    </a>
                 </div>
            </div>
            <div class="maskPlug" style="display:none;">
                <div class="duo_name_out" data=1>
                    <i class="pen_img glyphicon glyphicon-pencil"></i>
                </div> 
            </div>    
          <!--移入添加透明div-->
            <!--文章1上面的阴影-->
            <div class="childNewsPlug" style="display:none;">
             <div  class="childNews">
                <div class="duo_div">
                    <p class="duo_cen">标题</p>
                    <img src=""/>
                    <i class="childNewes_img">缩略图</i> 
                 </div>
             </div>
            </div>   
            <!--==========右边==========-->
            <div class="col-md-7 col-md-offset-1 col-xs-6">                     
                <!--下拉框-->
                <div class="col-md-4" style="padding:0px">
                    <select id="Select" class="form-control">
                        
                    </select>
                </div>
                <!--标题和栏目名-->
                <table class="table table-hover table table-bordered">
                    <tr>
                        <td colspan="2" class="text-left">
                            <i class="glyphicon glyphicon-pushpin"></i>
                            <a href="">栏目名</a>
                            &nbsp;&gt;&nbsp;
                            <a href=""><strong>文章列表</strong></a>
                        </td>
                    </tr>
                    <tr style="border-top:2px solid #ddd;border-bottom:2px solid #ddd;">
                        <td class="text-center"><b>栏目名</b></td>
                        <td class="text-center"><b>文章标题</b></td>
                    </tr>
                    <#if articleList?has_content>
                    <#list articleList as articleList>
                    <tr class="article" data-id="${articleList.basicId?c?default(0)}">
                        <td class="text-center"><#if articleList.column?exists>${articleList.column.categoryTitle?default("")}</#if></td>
                        <td class="text-center">${articleList.basicTitle?default("无标题")}</td>
                    </tr>
                    </#list>
                       </#if>
                </table>                   
            </div>       
        </div>
	</div>
</div>			
<script>
	$(function() {
		var i=0;
		addChildNews();
		/*主标题框鼠标上移出现添加单文章和多文章图标*/
		$(document).ready(function(){
              $(".newsMenu").hover(
					function(){								
						$("#heen").show();
						$(".add_cen").hide();
					},function(){
						$("#heen").hide();                    
						$(".add_cen").show();                    
			});
		});
		/*主标题框鼠标上移时图标颜色加深（偏移一张图片）*/
		$(".img").each(function(){
			$(this).mouseover(function(){
				$(this).find(".add_png1").css("background-position","-70px -355px");
				$(this).find("b").css("text-decoration","underline");
			});
			$(this).mouseout(function(){
				$(this).find(".add_png1").css("background-position","-70px -307px");
				$(this).find("b").css("text-decoration","none");
			});
		});
		$(".singleNews b").click(function(){
			$(".newsMenu").hide();
			$(".only").show();				
		});
		
		$(".manyNews b").click(function(){
			$(".newsMenu").hide();
			$(".duo").show();
		});
		/*************************************************************************/			
		//删除，根据mask上的data-index属性
		$(".mask .maskDel").on("click",function(){
			var u = $(".childNewes").height();		//获取外层大div的高度
			if(u!=126){								//如果这不是最后一个div则可以进行删除，否则不能删除				 	
				$(".childNews").eq(parseInt($(".mask").attr("data-index"))-1).remove();	//条件成立删除遮罩层下的div
					
				$(".mask").attr("data-index",$(".mask").attr("data-index")-1);			//可到遮罩层上移的位置即data-index								
				var obj = $(".mask").attr("data-index");		//重新获取遮罩层的位置
				$(".mask").animate({top:(obj-1)*126},150);		//遮罩层向上移动(上移的位置再乘以每格要偏移的像素数（*126）)
			}else{
				alert("多文章标题不得少于一条！")
			}
		});	
		
		//给“+”div增加新增childNews
		$(".addChildNews").click(function() {
			addChildNews(); //添加子新闻
		});							
			
		//添加大标题遮罩事件
		$(".parentNews").hover(function() {								
				//$(this).append($(".maskPlug").html());
				$(".duo_name_out").show();
			},function(){
				$(".duo_name_out").hide();
				//$(this).find(".duo_name_out").remove();
		});
		
		//点击编辑按钮,激活对应的相应内容
		$(".pen_img").click(function(){
			$(".pen_img").each(function(){
				$(this).parent().parent().attr("data-click","false");
			});
			$(this).parent().parent().attr("data-click","true");
		});
		//点击文章
		$(".article").click(function(){
			var articleId = $(this).attr("data-id");
			alert(articleId);
			$(".pen_img").each(function(){
				if($(this).parent().parent().attr("data-click")=="true"){
						
				}
			});
		});
	});
		
	/**
	*增加遮罩
	*/
	function addChildNews() {
		$(".childNewes").append($(".childNewsPlug").html());
		$(".childNewes .childNews:last").bind("mouseover",function() {
			//$("data-index")当前新闻位置
			$(".mask").show();	//滑块遮罩层始终存在
			$(".mask").animate({top:($(this).index()-1)*126},150);	//添加一个文章标题div
			$(".mask").attr("data-index",$(this).index());				
		});						
	}	
		
</script>
	
</body>
</html>

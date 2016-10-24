<!DOCTYPE html>
<html lang="en">
<head>
	<title>铭飞管理系统</title>
	<#include "${managerViewPath}/include/meta.ftl"/>
	<style> 
		.container{margin:0;padding:0;width:auto}
		hr{margin:0;padding:0;}
		.ms-button-group{padding:0px 0px 8px 0px}
	
	/*弹出窗口样式*/
		#WindowDialog .modal-dialog{width:auto;}
		td{word-break : break-all ;}
		.control-label{font-weight:normal;font-size:14px;}
		.redError{ color:red; font-size:12px;};	
		.container{margin:0;padding:0;width:auto}
		hr{margin-top:9px;margin-bottom:9px;padding:0;}
		.rowpadding3{padding-bottom: 3px;}
		.ms-button-group{padding:0px 0px 8px 0px}
		.row {margin-left:0;margin-right:0}
		.form-horizontal .form-group{margin-left:0;margin-right:0}
		.form-group{overflow: hidden;}
		.bs-example>.dropdown>.dropdown-menu {position: static;margin-bottom: 5px;clear: left;}
		.bs-example>.dropdown>.dropdown-toggle {float: left;}
		.padding-zero{padding:0;}
		/*链接样式*/
		.form-inline .form-group {display: inline-block;margin-bottom: 0;vertical-align: middle;}
		.dedeteRight{width: 82%;margin: 0 auto;overflow: hidden;text-align: right;}
		.has-error .form-control-feedback{float:right; margin-top:-24px; margin-right:5px; color:#A94442;}
		.has-success .form-control-feedback{float:right; margin-top:-24px; margin-right:5px; color:#3C763D;}
	</style>
</head>

<body>
	<div class="container-fluid link-style">
		<!--顶部   开始-->
		<div class="row rowpadding3">
			<div class="col-md-12 col-xs-12">
				<h3 class="page-title bottomLine">
					素材管理
					<small>文本素材列表</small>
				</h3>
			</div>
		</div>
		<!--顶部  结束-->
		<hr>
		<!--新增按钮开始-->
		<div class="row ">
			<!--功能按键部分 开始-->
			<div class="form-group">
				<button type="button" class="btn btn-success col-md" id="addText">新增文本素材</button>
			</div>
		</div>
		<!--新增按钮结束-->
		
		<!--列表 开始-->
		<div class="row ">
			<table class="table table-bordered table-hover" id="messageList">
				<!--表格栏目属性 开始-->
			        <thead>
			        	<tr>
			        		<td colspan="3" class="text-left">
		                     	<i class="glyphicon glyphicon-pushpin"></i>
		                     	<a href="">素材</a>&nbsp;>&nbsp;<a href=""><strong>文本素材列表</strong></a>
			        		</td>
			        	</tr>
			        	
				        <tr>
				        	<th class="col-md-1 col-xs-1 text-center">素材编号</th>
				        	<th class="col-md-9 col-xs-9 text-center">文本内容</th>
				        	<th class="col-md-2 col-xs-2 text-center">操作</th>
				        </tr>
			        </thead>
			        <!--表格栏目属性 结束-->
			        
			        <tbody>
				        <#if listNews?has_content>
				        	<#list listNews as listNews>
					        	<tr style="overflow:hidden">
					        		<td class="text-center">${listNews.newsId?c?default(0)}</td>
					        		<td>${listNews.newsContent?default('')}</td>
					        		<td class="text-center"> 
					        			<a class="btn btn-xs red tooltips del-btn deleteMessage" data-toggle="tooltip" data-original-title="删除" data-id="${listNews.newsId?c?default()}">
				                     		<i class="glyphicon glyphicon-trash"></i>
				                    	</a>
				                    	<a class="btn btn-xs red tooltips editMessage" data-toggle="tooltip"  data-original-title="修改" data-id="${listNews.newsId?c?default()}">
				                     		<i class="glyphicon glyphicon-pencil"></i>
				                    	</a>
				                    </td>
					        	</tr>
				        	</#list>
				        <#else>
				        	<tr>
				        		<td colspan="3" class="text-center">
					            	<p class="alert alert-info" role="alert" style="margin:0">
					            		<span class="glyphicon glyphicon-plus"></span>
					            			<a href="${base}${baseManager}/weixin/message/save.do" class="alert-link">
					            			您还没有添加文本素材，点击添加文本素材
					            		</a>
								  	</p>
								</td>
				        	</tr>
			        	 </#if>
			        </tbody>
			</table>
		</div>
		<!--列表 结束-->
		
	</div>
	
	<!--分页开始-->
	<@showPage page=page/>
	
	<div class="row" style="display:none;" id="textModel">
		<form action="" class="form-horizontal" role="form" id="textForm">
			<div class="form-group">
				<div class="col-md-12  col-xs-12">
					<textarea type="text" class=" form-control" rows="7" placeholder="请输入600个字符以内的文本素材！" name="newsContent"></textarea>
				</div>
				<div style="display:none" id="hideNewsId"></div>
				<input type="hidden" value="2" name="newsType"/>
			</div> 
		</form>
		 <div class="modal-footer">
			<button id="updateOrSaveText" type="button"  class="btn btn-primary" style="float:right;">保存</button>
			<button type="button" class="btn btn-default closeModal" data-dismiss="modal" style="float:right; margin-right:10px;">关闭</button>
	        <div style="clear:both;"></div>
        </div>
	</div>
	
	<!--删除消息模态框-->
	<@warnModal modalName="Model"/>
	
	<!--增加/更新消息模态框-->
	<@modalDialog modalName="addText"/>
	<script>
		$(function(){
			$("#addText").click(function(){
				$("#updateOrSaveText").html("保存");
				//清空文本框
				$("textarea[name='newsContent']").html("");
				$("#hideNewsId").html("");
				$("#textForm").attr("action","${base}${baseManager}/weixin/news/textSave.do");
				openaddText($("#textModel"),"新增文本素材");
			});
			
			//点击保存按钮开始保存消息
			//点击更新按钮
			$("#addTextDialog").delegate("#updateOrSaveText","click",function(){
				if($("#textForm textarea").val().length <=0){
					alert("请输入文本素材内容！");
					retrun ;
				}
				if($("#textForm textarea").val().length>600){
					var beyond = $("#textForm textarea").val().length - 600;
					alert("您输入的内容超出:"+beyond+"个字符！");
					return ;
				}
				var formdata = $("#textForm").serialize();
				var url = $("#textForm").attr("action");//文本素材提交地址
				$.ajax({
				   	type: "post",
				   	url:url,
				   	data: formdata,
				   	dataType:"json",
				   	success: function(msg){
				   		if(msg.result){
				   			if($("#updateOrSaveText").html()=="保存"){
				   				alert("保存成功");
				   				location.href="${base}${baseManager}/weixin/news/textList.do";
				   			}else{
				   				alert("更新成功");
				   				location.href="${base}${baseManager}/weixin/message/list.do?pageNo="+(msg.resultMsg);
				   			}
				   			$("#updateOrSaveText").attr("disabled", true);
				   		}else{
				   			alert(msg.resultMsg);
				   		}
				   	}
				});
			});
			
			//点击删除按钮
			$("#messageList").delegate("tbody tr .deleteMessage","click",function(){
				var newsId = $(this).attr("data-id");
				warnModel("确定要删除该文章？","删除文章","deleteMessage("+newsId+")");
			});
			
			//点击编辑按钮
			$("#messageList").delegate("tbody tr .editMessage","click",function(){
				$("#updateOrSaveText").html("更新");
				var newsId = $(this).attr("data-id");
				$("#textForm").attr("action","${base}${baseManager}/weixin/message/update.do");
				openaddText($("#textModel"),"更新消息");
				$.ajax({
					type: "post",
					url:"${base}${baseManager}/weixin/message/"+newsId+"/edit.do",
					dataType:"json",
					success:function(msg){
						var news = msg.news;
						$("#hideNewsId").html("<input type='hidden' name='newsId'/>");
						$("input[name='newsId']").val(news.newsId);
						$("textarea[name='newsContent']").html(news.newsContent);
					}
				});
			});
		});
		
		//删除消息
			function deleteMessage(id){
				$.ajax({ 
					type: "POST", 
					url: base+"${baseManager}/weixin/message/"+id+"/delete.do",
					dataType:"Json",
    				success: function(msg){
    					if(msg.result){
    						if($("tbody tr").length==1){
		 						if(msg.resultMsg=="1"){
		 							location.href="${base}${baseManager}/weixin/message/list.do?pageNo=1";
		 						}else{
		 							location.href="${base}${baseManager}/weixin/message/list.do?pageNo="+(msg.resultMsg-1);
		 						}
		 					}else{
		 						location.href="${base}${baseManager}/weixin/message/list.do?pageNo="+msg.resultMsg;
		 					}
    					}else{
    						alert("没有找到该文章！")
    					}
    				}
    			});
			}		
	</script>
</body>
</html>
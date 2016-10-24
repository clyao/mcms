<!DOCTYPE html>
<html lang="en">
<head>
<#include "${managerViewPath}/include/meta.ftl"/>
</head>
<style> 
	.container{margin:0;padding:0;width:auto}
	hr{margin:0;padding:0;}
	.ms-button-group{padding:0px 0px 8px 0px}

	/*弹出窗口样式*/
	#WindowDialog .modal-dialog{width:auto;}
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
<body>
	<@ms.content>
		<@ms.contentBody>
			<!--title对应板块名称-->
			<@ms.contentNav title="授权列表"></@ms.contentNav>
			<@ms.contentPanel>
				<@ms.panelNav empty=false>
					<!--列表操作按钮，添加和删除-->
					<@ms.panelNavBtnGroup>
						<@ms.panelNavBtnAdd id="addButton" value="" />
						<@ms.panelNavBtnDel id="delButton" value="" />
					</@ms.panelNavBtnGroup>													
				</@ms.panelNav>		
				<!--表格标题-->	
				<@ms.table head=['<input type="checkbox" value="" onclick="selectAll(this)" />','编号','授权地址','授权失败跳转地址','授权类型','授权描述']>
					<#if listOauth?has_content>
		       			<#list listOauth as list>
							<tr data-id="${list.oauthId?c?default(0)}">
								<td style="text-align:center">
									<input type="checkbox" name="oauthIds" value="${list.oauthId?c?default(0)}" />
								</td>
								<td style="text-align:center">${list.oauthId?c?default(0)}</td>
								<td style="text-align:center">
									<a style="cursor: pointer;" class="editOuath" data-toggle="tooltip"  data-original-title="修改" data-id="${list.oauthId?c?default(0)}">
										${list.oauthSuccessUrl?default("暂无")}
									</a>
								</td>
								<td style="text-align:center">${list.oauthErrorUrl?default("暂无")}</td>
								<td style="text-align:center" ><#if list.oauthType=1>登录授权<#else>普通授权</#if></td>
								<td style="text-align:center">${list.oauthDescription?default("暂无")}</td>		
							</tr>
						</#list>
					<!--若表格无数据-->	
					<#else>
						<tr>
							<td colspan="1330px" style="text-align:center">
								<@ms.nodata content="列表下空空如也!"/>
							</td>
						</tr>
					</#if>
				</@ms.table>											
			</@ms.contentPanel>
			<@ms.modal  modalName="delOauthModal" title="删除授权" >
					<@ms.modalBody>
						删除授权
					</@ms.modalBody>
					<@ms.modalButton>
						<!--模态框按钮组-->
						<@ms.button  value="确认删除？"  id="deleteOuathButton"  />
					</@ms.modalButton>
				</@ms.modal>
		</@ms.contentBody>
	</@ms.content>
	<!--引入分页-->
	<@showPage page=page/>	
	<!-- 新增授权弹出框开始 -->
		<div class="modal fade" id="oauthForm" style="display:hidden;">
			<div class="modal-dialog" style="width:55%">
				<div class="modal-content">				
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
						<h4 class="modal-title">
							新增授权地址
						</h4>
					</div>
					<div class="modal-body">
						<form action="" class="form-horizontal" role="form" id="modelFrom">
							<div class="form-group">
								<p class="alert alert-danger alert-dismissible fade in" role="alert" style="margin:0">
						            	<span class="glyphicon glyphicon-pushpin text-lef "></span>
						            	<strong>提示:</strong>
										授权类型选择后不能更改,请您根据实际需求在添加时谨慎选择!
								</p>
							</div>
							<input name="oauthId" type="hidden" value="0">
							<!-- 授权成功跳转开始 -->
							<div class="form-group">
								<label class="col-md-4 control-label col-xs-4" style="width:29%">授权地址:</label>
								<div class="col-md-7  col-xs-6" style="padding:0;width:71%">
									<input type="text" class="form-control" name="oauthSuccessUrl" placeholder="请输入授权地址">
								</div>
							</div>					
							<!-- 授权成功跳转结束 -->
						
							<!-- 授权失败跳转开始 -->
							<div class="form-group">
								<label class="col-md-4 control-label col-xs-4" style="width:29%">授权失败跳转地址:</label>
								<div class="col-md-7  col-xs-6" style="padding:0;width:71%">
									<input type="text" class="form-control" name="oauthErrorUrl" placeholder="请输入授权失败跳转地址">
								</div>
							</div>								
							<!-- 授权失败跳转结束 -->					
							<!-- 选择授权类型 -->
							<div class="form-group oauthType">
								<label class="col-md-4 control-label col-xs-4" style="width:29%">授权类型:</label>
								<div class="col-md-5  col-xs-6" style="padding:0;width:32%">
									<select class="form-control" name="oauthType">
										<option value="0">请选择授权类型</option>
										<option value="1">登录授权</option>
										<option value="2">普通授权</option>
									</select>
								</div>
							</div>							
							<!-- 选择授权类型 -->
							<!-- 授权描述 -->
							<div class="form-group">
								<label class="col-md-4 control-label col-xs-4" style="width:29%">授权描述:</label>
								<div class="col-md-7  col-xs-6" style="padding:0;width:71%">
									<textarea maxlength="50"  class="form-control  newsContent" name="oauthDescription" placeholder="请输入授权描述" style="height:80px"></textarea>
								</div>
							</div>							
							<!-- 授权描述  -->							
						</form>						
					</div>				
			      <div class="modal-footer">
			        <button type="button" id="saveOrUpdateBtn" class="btn btn-primary" style="float:right;">保存</button>
			        <button type="button" class="btn btn-default closeSaveModal" data-dismiss="modal" style="float:right; margin-right:10px;">关闭</button>
			      </div>				
					
				</div>
			</div>	
		</div>		
		<!-- 新增授权弹出框结束 -->
	</div>
<script>
	$(function(){
		/**
		* 新增授权
		*/
		$("#addButton").click(function(){
			//将所有输入框的值加载为空
			$("#oauthForm input[name='oauthId']").val(0);
			$("#oauthForm .oauthType").show();
			$("#oauthForm input[name='oauthSuccessUrl']").val('');
			$("#oauthForm input[name='oauthErrorUrl']").val('');
			$("#oauthForm select[name='oauthType'] option[value='0']").attr("selected","selected");
			$("#oauthForm textarea[name='oauthDescription']").val('');
			$("#saveOrUpdateBtn").text("保存");
			$(".modal-title").text("新增授权地址");
			//初始化提交地址
			$("#oauthForm").attr("action",base+"${baseManager}/weixin/oauth/save.do");
			$("#oauthForm").modal();
		});
		
		/**
		 * 更新授权
		 */
		$(".editOuath").bind("click",function(){
			//获取ID
			var oauthId = $(this).attr("data-id");
			var obj = $("table tr[data-id='"+oauthId+"']").find("td");
			//获取授权地址
			var oauthSuccessUrl = $(this).text();
			//获取授权失败跳转地址
			var oauthErrorUrl = obj.eq(3).text();
			//获取授权描述	
			var oauthDescription = obj.eq(5).text();
			
			//加载弹出框的默认值
			$("#oauthForm input[name='oauthId']").val(oauthId);
			$("#oauthForm input[name='oauthSuccessUrl']").val($.trim(oauthSuccessUrl));
			$("#oauthForm input[name='oauthErrorUrl']").val($.trim(oauthErrorUrl));
			$("#oauthForm .oauthType").hide();
			$("#oauthForm textarea[name='oauthDescription']").val($.trim(oauthDescription));
			$("#saveOrUpdateBtn").text("更新");
			$(".modal-title").text("更新授权地址")
			//初始化提交地址
			$("#oauthForm").attr("action",base+"${baseManager}/weixin/oauth/update.do");
			$("#oauthForm").modal();
		});
		
		/**
		 * 点击保存按钮
		 */
		 $("#saveOrUpdateBtn").click(function(){
		 	//验证提交信息
		 	var oauthSuccessUrl = $("#oauthForm input[name='oauthSuccessUrl']").val();
		 	if(oauthSuccessUrl == null || oauthSuccessUrl == ""){
		 		alert("授权地址不能为空!");
		 		return ;
		 	}
		 	var oauthId = $("#oauthForm input[name='oauthId']").val();
		 	var oauthType = $("#oauthForm select[name='oauthType']").val();
		 	if(oauthType == 0 && oauthId == 0){
		 		alert("请选择授权类型");
		 		return ;
		 	}
		 	var oauthDescription = $("#oauthForm textarea[name='oauthDescription']").val();
		 	if(oauthDescription == null || oauthDescription == ""){
		 		alert("请输入授权描述!");
		 		return ;
		 	}
			
			if(oauthId >0){
				$(this).text("更新中...");
				$(this).attr("class","btn btn-info");
			}else{
				$(this).text("保存中...");
				$(this).attr("class","btn btn-info");
			}		 	
			
			$("#saveOrUpdateBtn").unbind( "click" )
			
		 	//获取提交地址
		 	var url = $("#oauthForm").attr("action");
		 	//表单序列化
		 	var data = $("form").serialize();
		 	//发送新增请求
		 	$.ajax({
		 		type:"POST",
		 		url:url,
		 		dataType:"json",
		 		data:data,
		 		success:function(msg){
		 			if(msg.result == true){
		 				if(oauthId == 0){
			 				alert("保存成功!");
			 				location.href=base+"${baseManager}/weixin/oauth/list.do";
		 				}else{
		 					location.href=window.location.href;
		 					alert("更新成功!");
		 				}
		 			}else{
		 				if(oauthId == 0){
		 					alert("保存失败!");
		 				}else{
		 					alert("更新失败!");
		 				}
		 			}
		 		},
		 	});
		 });
	});
	$("#delButton").click(function(){
		//没有选中checkbox
		if($("input[type=checkbox]:checked").length <= 0){
			alert("请选择要删除的关键字回复");
		//点击全选，但是列表为空
		}else if($("input[name='oauthIds']:checked").length == 0){
			alert("没有可删除的关键字回复");
		}else{
	  		$(".delOauthModal").modal();
		}
	})
	//批量删除
	$("#deleteOuathButton").click(function(){
		$(this).text("努力删除中...")
		$(this).attr("disabled","true");
		$.ajax({		
		    type:"GET",
			url:"${managerPath}/weixin/oauth/delete.do",
		    data:$("input[name='oauthIds']").serialize(),
		    success:function(msg) { 
				if (msg.result == false) {
					alert("删除失败");
				}else{
					alert("删除成功");
					location.href = base+"${baseManager}/weixin/oauth/list.do";
				}
			}
		});	
	})
	
	//点击标题的checkbox全选,再次点击取消全选
	function selectAll(checkbox) {
    	$('input[type=checkbox]').prop('checked', $(checkbox).prop('checked'));
    }
	
</script>	
</body>
</html>

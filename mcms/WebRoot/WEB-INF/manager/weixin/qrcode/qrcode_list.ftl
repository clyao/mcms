<!DOCTYPE html>
<html lang="zh">
<head>
	<#include "${managerViewPath}/include/macro.ftl"/>
  	<#include "${managerViewPath}/include/meta.ftl"/>  
  	<style>
  		body #qrCodeModelDialog .modal-dialog{
  			width:45%
  		}
  	</style>
</head>
<body>
	<@ms.content>
		<@ms.contentBody>
			<!--title对应板块名称-->
			<@ms.contentNav title="微信二维码列表"></@ms.contentNav>
			<@ms.contentPanel>
				<@ms.panelNav empty=false>
					<!--列表操作按钮，添加和删除-->
					<@ms.panelNavBtnGroup>
						<@ms.panelNavBtnAdd id="addButton" value="" />						
						<@ms.panelNavBtnDel id="delButton" value="" />
					</@ms.panelNavBtnGroup>													
				</@ms.panelNav>		
				<!--表格标题-->	
				<@ms.table head=['<input type="checkbox" value="" onclick="selectAll(this)" />','编号','标题','场景值','类型','描述','生成时间']>
					<!--若表格有数据-->
					<#if qrCodeList?has_content>
						<!--微信列表信息-->
						<#list qrCodeList as qrCodeList>
							<tr data-id="${qrCodeList.qrcodeId?c?default(0)}"r>
								<td style="text-align:center">
									<input type="checkbox" name="qrcodeIds" value="${qrCodeList.qrcodeId?c?default()}" />
								</td>
								<td style="text-align:center">${qrCodeList.qrcodeId?c?default()}</td>
								<td style="text-align:center">${qrCodeList.qrcodeTitle?default('暂无')}</td>
								<td style="text-align:center" >${qrCodeList.qrcodeValue?c?default()}</td>
								<!--微信二维码类型：1永久二维码，2.临时二维码-->
								<td style="text-align:center" data-type="${qrCodeList.qrcodeType?c?default()}">	
									<a class="btn btn-xs red tooltips editQrcode qrcodeType" data-type="${qrCodeList.qrcodeType?c?default()}" data-id="${qrCodeList.qrcodeId}" data-toggle="tooltip"  data-original-title="编辑">
										${qrCodeList.qrcodeType?c?default()}
									</a>
								</td>	
								<td style="text-align:center">${qrCodeList.qrcodeDescription?default('暂无')}</td>
								<td style="text-align:center">${qrCodeList.qrcodeTime?string("yyyy-MM-dd HH:mm:ss")}</td>	
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
				<!--编辑模态框-->
				<@ms.modal modalName="qrCodeModel" title="">
					<@ms.modalBody>
						<@ms.form isvalidation=true name="qrcodeForm" id="qrcodeForm" action="">
							<input name="codeId" value="" type="hidden" / >
							<@ms.text maxlength="50"  name="qrcodeTitle"label="二维码标题:" value="" title="二维码标题"/>					    		
				    		<@ms.text style="width:25%" name="qrcodeValue" maxlength="32" label="场景值:" value="0" title="场景值" />	
				    		<@ms.select style="width:55%" name="qrcodeType" label="二维码类型:" list={"1":"永久二维码","2":"临时二维码"}  value="" listKey="qrcodeType" listValue="qrcodeTypeValue" validation={"required":"true", "data-bv-notempty-message":"必填项目"}/>			
				    		<@ms.textarea style="width:84%" name="qrcodeDescription" label="二维码描述:" maxlength="200" value="" title="更新通知" />		    		
						</@ms.form>
					</@ms.modalBody>
					<!--模态框按钮组-->
					<@ms.modalButton>
						<@ms.button value="确认修改" id="saveOrUpdateQrcode" />
					</@ms.modalButton>
				</@ms.modal>
				<@ms.modal  modalName="delCodeModal" title="删除微信二维码" >
					<@ms.modalBody>
						删除微信二维码
					</@ms.modalBody>
					<@ms.modalButton>
						<!--模态框按钮组-->
						<@ms.button  value="确认删除？"  id="deleteCodeButton"  />
					</@ms.modalButton>
				</@ms.modal>											
			</@ms.contentPanel>
		</@ms.contentBody>
	</@ms.content>
</body>
</html>
<script>
	
	//新增微信二维码
	$("#addButton").click(function(){
		//将所有值加载为空
		$("#qrcodeForm input[name='codeId']").val(0);
		$("#qrcodeForm input[name='qrcodeTitle']").val('');
		$("#qrcodeForm select[name='qrcodeType'] option[value='0']").attr("selected","selected");
		$("#qrcodeForm textarea[name='qrcodeDescription']").val('');
		$("#saveOrUpdateQrcode").text("保存");
		//初始化提交地址
		$("#qrCodeModelTitle").text("新增二维码")
		$("#qrcodeForm").attr("action",base+"${baseManager}/weixin/qrcode/save.do");
		$(".qrCodeModel").modal();
	});
	
	//编辑二维码
	$(".editQrcode").click(function(){
		var codeId = $(this).attr("data-id");	//获取ID
		var obj = $("table tr[data-id='"+codeId+"']").find("td");	
		var qrcodeTitle = obj.eq(2).text();//获取二维码标题
		var qrcodeValue = obj.eq(3).text();//获取场景值
		var qrcodeType = $(this).attr("data-type");//获取二维码类型
		var qrcodeDescription = obj.eq(5).text();//获取描述
		
		//加载弹出框的默认值
		$("#qrcodeForm input[name='codeId']").val(codeId);
		$("#qrcodeForm input[name='qrcodeTitle']").val($.trim(qrcodeTitle));
		$("#qrcodeForm input[name='qrcodeValue']").val($.trim(qrcodeValue));
		$("#qrcodeForm select[name='qrcodeType']").find("option[value='"+qrcodeType+"']").attr("selected",true);
		$("#qrcodeForm textarea[name='qrcodeDescription']").val($.trim(qrcodeDescription));
		$("#qrCodeModelTitle").text("更新二维码")
		$("#saveOrUpdateQrcode").text("更新");
		//初始化提交地址
		$("#qrcodeForm").attr("action",base+"${baseManager}/weixin/qrcode/"+codeId+"/update.do");
		$(".qrCodeModel").modal();
	})
	
	 $("#saveOrUpdateQrcode").click(function(){
	 	//验证提交信息
	 	var codeId = $("input[name='codeId']").val();
	 	var qrcodeValue = $.trim($("input[name='qrcodeValue']").val());
	 	if(qrcodeValue !="" && isNaN(qrcodeValue)){
			alert("场景值必须为数字!");
			return;
		}else if(qrcodeValue == ""){
			$("input[name='qrcodeValue']").val(0);
		}	 						
	 	var vobj = $("#qrcodeForm").data('bootstrapValidator').validate();
		if(vobj.isValid()){		
			if(codeId >0){
				$("#saveOrUpdateQrcode").text("更新中...");
				$("#saveOrUpdateQrcode").attr("class","btn btn-info");
			}else{
				$("#saveOrUpdateQrcode").text("保存中...");
				$("#saveOrUpdateQrcode").attr("class","btn btn-info");
			}			
			$("#saveOrUpdateQrcode").unbind( "click" )	
			$("#saveOrUpdateQrcode").postForm("#qrcodeForm",{func:function(msg) {
				if(msg.result == true){
	 				if(codeId == 0){
		 				alert("保存成功!");
		 				location.href=base+"${baseManager}/weixin/qrcode/list.do";
	 				}else{
	 					location.href=window.location.href;
	 					alert("更新成功!");
	 				}
	 			}else{
	 				if(codeId == 0){
	 					alert("保存失败!");
	 				}else{
	 					alert("更新失败!");
	 				}
	 			}
			}});
		}
	});
	
	//点击标题的checkbox全选,再次点击取消全选
	function selectAll(checkbox) {
    	$('input[type=checkbox]').prop('checked', $(checkbox).prop('checked'));
    }
	
	$("#delButton").click(function(){
		//没有选中checkbox
		if($("input[type=checkbox]:checked").length <= 0){
			alert("请选择要删除的二维码");
		//点击全选，但是列表为空
		}else if($("input[name='qrcodeIds']:checked").length == 0){
			alert("没有可删除的二维码");
		}else{
			$(".delCodeModal").modal();
		}
	})

	//批量删除
	$("#deleteCodeButton").click(function(){
		$(this).text("努力删除中...")
		$(this).attr("disabled","true");
		$.ajax({		
		    type:"GET",
			url:"${managerPath}/weixin/qrcode/delete.do",
		    data:$("input[name='qrcodeIds']").serialize(),
		    success:function(msg) { 
				if (msg.result == false) {
					alert("删除失败");
				}else{
					alert("删除成功");
					location.href = base+"${baseManager}/weixin/qrcode/list.do";
				}
			}
		});	
	})
	
	
	//判断微信类型
	$(function(){
		$(".qrcodeType").each(function(){
    		if($(this).data("type") == 1){
				$(this).text("永久二维码");
			}else if($(this).data("type") == 2){
				$(this).text("临时二维码");
			}else{
				$(this).text("未知");
			}
  		});		
	})
	
</script>

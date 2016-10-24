<!DOCTYPE html>
<html lang="zh">
<head>
	<#include "${managerViewPath}/include/macro.ftl"/>
  	<#include "${managerViewPath}/include/meta.ftl"/>  
  	<style>
  		body .editMessage{
  			padding-left:0
  		}
  	</style>
</head>
<body>
	<@ms.content>
		<@ms.contentBody>
			<!--title对应板块名称-->
			<@ms.contentNav title="关键字回复"></@ms.contentNav>
			<@ms.contentPanel>
				<@ms.panelNav empty=false>
					<!--列表操作按钮，添加和删除-->
					<@ms.panelNavBtnGroup>
						<@ms.panelNavBtnAdd id="addButton" value="" />
						<@ms.panelNavBtnDel id="delButton" value="" />
					</@ms.panelNavBtnGroup>													
				</@ms.panelNav>		
				<!--表格标题-->	
				<@ms.table head=["<th style='width:5%'><input type='checkbox' value='' onclick='selectAll(this)' /></th>",'关键字','回复内容',"<th style='text-align:center'>素材类型</th>"]>
					<!--若表格有数据-->
					<#if messageKeyList?has_content>		       			
						<!--列表信息-->
						<#list messageKeyList as list>
							<tr>
								<td>
									<input type="checkbox" name="keyMessageIds" value="${list.passiveMessageId?c?default()}" />
								</td>
								<!--关键词-->
								<td style="width:25%">${list.passiveMessageKey?default('暂无')}</td>
								<!--回复内容-->
								<td style="width:50%;">
									<a style="cursor: pointer;" class="editMessage" data-toggle="tooltip"  data-original-title="修改" data-id="${list.passiveMessageId?c?default(0)}">
				            			<#if list.newsEntity?has_content>    
				       						<#if list.newsEntity.newsType=0>
				       							${list.newsEntity.newsMasterArticle.basicTitle?default('')}
				       						<#elseif list.newsEntity.newsType=1>
				       							<strong>主图文标题:</strong>${list.newsEntity.newsMasterArticle.basicTitle?default('')}
						       					<#if list.newsEntity.childs?has_content>
						       						<strong>子图文标题:</strong>
						       						<#list list.newsEntity.childs as listChild>
						       							${listChild.basicTitle?default('')}/
						       						</#list>
						       					</#if>
				       						<#elseif list.newsEntity.newsType=2>
				       							${list.newsEntity.newsContent}
				       						<#elseif list.newsEntity.newsType=3>
				       							图片素材
				       						</#if>
										<#else>
											未知
										</#if>
			            			</a>	
								</td>
								<!--素材类型-->
								<td style="text-align:center">
									<#if list.newsEntity?has_content>
			       						<#if list.newsEntity.newsType=0>
			       							单图文素材
			       						<#elseif list.newsEntity.newsType=1>
			       							多图文素材
			       						<#elseif list.newsEntity.newsType=2>
			       							文本素材
			       						</#if>
									<#else>
										未知
									</#if>
								</td>
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
				<!--列表分页-->
				<@showPage page=page/>
				<!--删除模态框-->
				<@ms.modal  modalName="delMessagekeyModal" title="删除关键字回复" >
					<@ms.modalBody>
						删除关键字
					</@ms.modalBody>
					<@ms.modalButton>
						<!--模态框按钮组-->
						<@ms.button  value="确认删除？"  id="deleteMessagekey"  />
					</@ms.modalButton>
				</@ms.modal>											
			</@ms.contentPanel>
		</@ms.contentBody>
	</@ms.content>
</body>
</html>
<script>
	//新增关键字回复
	$("#addButton").click(function(){
		location.href = base+"${baseManager}/weixin/messagekey/add.do"; 
	});
	
	//编辑关键字回复
	$(".editMessage").click(function(){
		location.href = base+"${baseManager}/weixin/messagekey/"+$(this).attr("data-id")+"/edit.do"; 
	})
		
	//点击标题的checkbox全选,再次点击取消全选
	function selectAll(checkbox) {
    	$('input[type=checkbox]').prop('checked', $(checkbox).prop('checked'));
    }
	
	//判断打开删除模态框的条件
	$("#delButton").click(function(){
		//没有选中checkbox
		if($("input[type=checkbox]:checked").length <= 0){
			alert("请选择要删除的关键字回复");
		//点击全选，但是列表为空
		}else if($("input[name='keyMessageIds']:checked").length == 0){
			alert("没有可删除的关键字回复");
		}else{
			$(".delMessagekeyModal").modal();
		}
	})
	//批量删除
	$("#deleteMessagekey").click(function(){
		$(this).text("努力删除中...")
		$(this).attr("disabled","true");
		$.ajax({		
		    type:"GET",
			url:"${managerPath}/weixin/messagekey/delete.do",
		    data:$("input[name='keyMessageIds']").serialize(),
		    success:function(msg) { 
				if (msg.result == false) {
					alert("删除失败");
				}else{
					alert("删除成功");
					location.href = base+"${baseManager}/weixin/messagekey/list.do";
				}
			}
		});	
	})    
</script>

<@ms.html5>
	<@ms.nav title="公众号列表"></@ms.nav>
	<@ms.panel>
			<@ms.panelNav empty=false>
					<!--列表操作按钮，添加和删除 开始 -->
					<@ms.panelNavBtnGroup>
						<@ms.panelNavBtnAdd id="addButton" value="" /><!-- 新增按钮 -->
						<@ms.panelNavBtnDel id="delButton" value="" /><!-- 删除按钮 -->
					</@ms.panelNavBtnGroup>		
					<!--列表操作按钮，添加和删除结束 -->											
				</@ms.panelNav>		
				<!--表格标题-->	
				<@ms.table head=["<th style='width:2%'><input type='checkbox' value='' onclick='selectAll(this)' /></th>","编号","微信号","公众号名称","公众号类型","微信token","网页2.0授权地址"]>
					<!--若表格有数据-->
					<#if weixinList?has_content>
						<!--微信列表信息开始-->
						<#list weixinList as weixinList>
							<tr>
								<td>
									<input type="checkbox" name="weixinIds" value="${weixinList.weixinId?c?default()}" />
								</td>
								<!--编号-->
								<td style="text-align:center;width:5%">${weixinList.weixinId?default('')}</td>
								<!--微信号-->
								<td style="width:15%">${weixinList.weixinNo?default('暂无')}</td>
								<!--公众号名称-->
								<td style="width:15%" class="weixinName" data-weixinId=${weixinList.weixinId}>
									<a class="btn btn-xs red tooltips editWeixin" data-id="${weixinList.weixinId}" data-toggle="tooltip"  data-original-title="编辑">
					            		${weixinList.weixinName}
					            	</a>
								</td>
								<!--微信号类型开始 0 服务号,1订阅号-->
								<td><#if weixinList.weixinType==0>服务号<#else>订阅号</#if></td>
								<!--微信token-->
								<td style="width:10%;">${weixinList.weixinToken?default('暂无')}</td>
								<!--网页2.0授权地址-->
								<td style="width:25%;">${weixinList.weixinOauthUrl?default('暂无')}</td>	
							</tr>
						</#list>
					<!--微信列表信息结束-->
					<!--若表格无数据-->	
					<#else>
						<tr>
							<td colspan="1330px" style="text-align:center">
								<@ms.nodata content="列表下空空如也!"/>
							</td>
						</tr>
					</#if>
				</@ms.table>
				<!--分页-->
	   			<@ms.showPage page=page/>		
	</@ms.panel>
</@ms.html5>

<@ms.modal  modalName="delWeixinModal" title="删除微信" >
	<@ms.modalBody>
		删除微信
	</@ms.modalBody>
	<@ms.modalButton>
		<!--模态框按钮组-->
		<@ms.button  value="确认删除？"  id="deleteWeixin"  />
	</@ms.modalButton>
</@ms.modal>
<script>
	//新增微信
	$("#addButton").click(function(){
		location.href = "${managerPath}/weixin/add.do"; 	
	})
	
	//点击标题的checkbox全选,再次点击取消全选
	function selectAll(checkbox) {
    	$('input[type=checkbox]').prop('checked', $(checkbox).prop('checked'));
    }
	
	//判断打开删除模态框条件
	$("#delButton").click(function(){
		//没有选中checkbox
		if($("input[type=checkbox]:checked").length <= 0){
			alert("请选择要删除的微信");
		//点击全选，但是列表为空
		}else if($("input[name='weixinIds']:checked").length == 0){
			alert("没有可删除的微信");
		}else{
			$(".delWeixinModal").modal();
		}
	});

	//批量删除
	$("#deleteWeixin").click(function(){
		$(this).text("努力删除中...")
		$(this).attr("disabled","true");
		$.ajax({		
		    type:"GET",
			url:"${managerPath}/weixin/delete.do",
		    data:$("input[name='weixinIds']").serialize(),
		    success:function(msg) { 
		    	var obj = jQuery.parseJSON(msg);
				if(obj.result == true) {
					alert("删除成功");
				}else{
					alert("删除失败");
				}
				location.reload();
			}
		});	
	})
	
    $(".editWeixin").click(function(){
    	var weixinId = $(this).attr("data-id");
    	location.href="${managerPath}/weixin/"+weixinId+"/function.do";
    })
</script>

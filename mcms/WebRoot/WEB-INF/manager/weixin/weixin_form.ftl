<@ms.html5>
	<@ms.nav title="微信设置">
		<@ms.button class="btn btn-success"  id="saveOrUpdateWeixin"  value="编辑微信设置"/>
	</@ms.nav>
	<@ms.panel>
					<@ms.form isvalidation=true name="weixinForm" action="${managerPath}/weixin/save.do">
		    		<@ms.text  name="weixinNo"   label="微信号:" value="${weixin.weixinNo?default('')}" title="微信号" placeholder="请输入微信号"  validation={"required":"true","maxlength":"30","data-bv-stringlength-message":"微信号在30个字符以内!", "data-bv-notempty-message":"必填项目"}/>
		    		<@ms.text  name="weixinOriginId"   label="微信原始ID:" value="${weixin.weixinOriginId?default('')}" title="微信原始ID" placeholder="请输入微信原始ID" validation={"required":"true", "maxlength":"30","data-bv-stringlength-message":"微信原始ID在30个字符以内!","data-bv-notempty-message":"必填项目"}/>
		    		<@ms.text  name="weixinName"   label="公众号名称:" value="${weixin.weixinName?default('')}" title="公众号名称:" placeholder="请输入公众号名称"  validation={"required":"true","maxlength":"30","data-bv-stringlength-message":"公众号名称在30个字符以内!", "data-bv-notempty-message":"必填项目"}/>
		    		<@ms.select  name="weixinType" style="width: 20%;padding: 0 0 0 8px;"  label="微信类型:" list={"0":"服务号","1":"订阅号"}  value="${weixin.weixinType?c?default(0)}" listKey="weixinType" listValue="weixinTypeValue" validation={"required":"true", "data-bv-notempty-message":"必填项目"} />			
		    		<@ms.text  name="weixinAppID"   label="微信appId:" value="${weixin.weixinAppID?default('')}" title="微信appId" placeholder="请输入微信appId"  validation={"required":"true","maxlength":"150","data-bv-stringlength-message":"应用编号在150个字符以内!", "data-bv-notempty-message":"必填项目"}/>
		    		<@ms.text  name="weixinAppSecret"   label="微信appSecret:" value="${weixin.weixinAppSecret?default('')}" title="微信appSecret" placeholder="请输入微信appSecret"  validation={"required":"true","maxlength":"150","data-bv-stringlength-message":"授权码在150个字符以内!", "data-bv-notempty-message":"必填项目"}/>
		    		<@ms.text  name="weixinToken"  label="微信token:" value="${weixin.weixinToken?default('')}" title="微信token" placeholder="请输入微信token" validation={"maxlength":"30","data-bv-stringlength-message":"微信toke在30个字符以内!"} />
		    		
		    		<@ms.formRow label="头像：">
            				<#if weixin?has_content>
        						<@ms.uploadImg path="upload/weixin/${appId?c?default(0)}/" inputName="weixinHeadImg" size="1" filetype="" msg="头像缩略图"  maxSize="1" imgs="${weixin.weixinHeadImg?default('')}" />
        					<#else>
        						<@ms.uploadImg path="upload/weixin/${appId?c?default(0)}/" inputName="weixinHeadImg" size="1" filetype="" msg="头像缩略图"  maxSize="1" imgs="" />
        					</#if>		    		
		    		</@ms.formRow>
		    		<@ms.formRow label="头像：">
	            			<#if weixin?has_content>
	        					<@ms.uploadImg path="upload/weixin/${appId?c?default(0)}/" inputName="weixinImage" size="1" filetype="" msg="二维码缩略图"  maxSize="1" imgs="${weixin.weixinImage?default('')}" />
	    					<#else>
	        					<@ms.uploadImg path="upload/weixin/${appId?c?default(0)}/" inputName="weixinImage" size="1" filetype="" msg="二维码缩略图"  maxSize="1" imgs="" />
	        				</#if>		    		
		    		</@ms.formRow>
		    	</@ms.form>
	</@ms.panel>
</@ms.html5>

<script>
	<#if weixin.weixinId!=0>
		$("#weixinForm").attr("action","${managerPath}/weixin/"+${weixin.weixinId?c?default(0)}+"/update.do");
	</#if>
		
	$(function() {
		//点击保存或更新按钮进行保存
		$("#saveOrUpdateWeixin").click(function(){
			var btnWord = $(this).text();
			//表单验证开始
			var vobj = $("#weixinForm").data('bootstrapValidator').validate();
			if(vobj.isValid()){
				//判断地址合法性
				var weixinOauthUrl = $.trim($("input[name='weixinOauthUrl']").val());
				var weixinProxyUrl = $.trim($("input[name='weixinProxyUrl']").val());
				if(weixinProxyUrl!="" && weixinProxyUrl!=undefined){
					weixinProxyUrl = weixinProxyUrl.match(/http:\/\/.+/);
					if(weixinProxyUrl == null){
						alert("请输入http格式的内网地址!");
						return;
					}
				}
				if(weixinOauthUrl!="" && weixinOauthUrl!=undefined){
					weixinOauthUrl = weixinOauthUrl.match(/http:\/\/.+/);
					if(weixinOauthUrl == null){
						alert("请输入http格式的授权地址!");
						return;
					}
				}
				<#if weixin.weixinId!=0>
	     			$("#saveOrUpdateWeixin").text("更新中");	
     			<#else>
	     			$("#saveOrUpdateWeixin").text("保存中");		
	     		</#if>		     							
				$(this).postForm("#weixinForm",{func:function(msg) {
					if (msg.result == true) {
			     		<#if weixin.weixinId!=0>
			     			alert("更新成功");	
			     			$("#saveOrUpdateWeixin").text("更新");
		     			<#else>
			     			alert("保存成功");
			     			location.href = "${managerPath}/weixin/list.do"; 	
			     		</#if>		     					    		
			    	}else{
			    		alert("提交失败!");
			    		$("#saveOrUpdateWeixin").text(btnWord);	
			    	}
				}});
			}
		});
	});
</script>



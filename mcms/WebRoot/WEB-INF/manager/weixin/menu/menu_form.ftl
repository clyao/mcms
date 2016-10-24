<@ms.html5>
	<@ms.panel>
	<style>
		/*图文预览*/
		#menuForm .control-label{
			width:17%;
		}
		/*固定文本域*/
		textarea {
			resize: none;
		}
		/*图文预览框*/
		.msg-item-wrapper{
			width: 340px;
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
		body .col-xs-10{
			width:340px;
			padding:0
		}
		/*图文样式*/
		.sub-msg-item{
			padding:12px 14px;
			overflow:hidden;
			zoom:1;
			border-top:1px solid #c6c6c6;
		}
		.cover{
			margin:0 14px 12px;
			position:relative;
			font-size:0;
			height:160px;
			overflow:hidden;
		}
		.sub-msg-item .msg-t{
			margin-left:0;
			margin-right:85px;
			margin-top:0;
			padding-left:4px;
			padding-top:12px;
			line-height:24px;
			max-height:48px;
			font-size:14px;
			overflow:hidden;
		}
		.thumb{
			float:right;
			font-size:0;
		}
		.thumb .default-tip{
			font-size:16px;
			color:#c0c0c0;
			width:70px;
			line-height:70px;
			border:1px solid #b2b8bd;
		}
		.thumb img{
			width:70px;
			height:70px;
			border:1px solid #b2b8bd;
		}
		body .ms-content-body-panel{
			min-width: inherit;
		}
		.msg-meta,.msg-t,.msg-text{
			padding:0 14px;
		}
		img{
			width:100%;height:100%
		}
	</style>
	
				<@ms.form isvalidation=true name="menuForm"  action="">
					<@ms.hidden name="menuMenuId" value="" />
					<@ms.text  name="menuTitle"   label="菜单名称:" value="${menu.menuTitle?default('')}" title="菜单名称:" placeholder="请输入菜单名称" validation={"required":"true", "maxlength":"7","data-bv-stringlength-message":"长度介于1-7个字符!","data-bv-notempty-message":"必填项目"}/>
					<@ms.select  name="menuStyle"   label="菜单类型:" list={"1":"文本","2":"图文","4":"外连接"}  value="${menu.menuStyle?default(1)}" listKey="menuStyle" listValue="menuStyleValue" validation={"required":"true", "data-bv-notempty-message":"必填项目"} />	
					<!---文本内容开始---->
					<div class=" menuText r-module" data-style="1" style="display:block">
						<@ms.textarea  style="height:150px" name="replyText" label="文本内容:" id="replyText" value="${replyText?default('')}" wrap="Soft" size="12" placeholder="请输入文本内容" validation={"required":"true", "maxlength":"255","data-bv-stringlength-message":"长度介于1-255个字符!","data-bv-notempty-message":"必填项目"}/>
					</div>
					<!---文本内容结束---->				
					<!---图文开始---->
					<div class="form-group r-module" data-style="2" style="display:none;" >
						<label class="col-md-5 control-label col-xs-2">图文预览:</label>
						<div class="col-md-7  col-xs-10">
							<div id="article_reply" >
	    						<div class="msg-item-wrapper">              
									<div class="msg-item">           
										<h4 class="msg-t"><a href="#" class="i-title" target="_blank"></a></h4>           
										<p class="msg-meta"><span class="msg-date"></span></p>
										<div class="cover">
											<p class="default-tip" style="display:none">封面图片</p>
											<img src="" class="i-img">
										</div>
										<p class="msg-text"></p>      
									</div>
								</div> 							 
	    					</div>
	    					<button class="btn btn-big selArticle">选择图文</button>
						</div>
						<input type="hidden" name="picTextId" value="0" data-ajax-url=""/>
					</div>
					<!---图文结束---->			
					<!---外连接开始---->
					<div class="r-module" style="display:none;" data-style="4">
						<@ms.text  name="source_url"   label="外连接:" value="${sourceUrl?default('')}" title="外连接" placeholder="请输入外连接" validation={"required":"true", "maxlength":"200","data-bv-stringlength-message":"长度介于1-200个字符!","data-bv-notempty-message":"必填项目"}/>
					</div>
					<!---外连接结束---->
					<@ms.formRow>
							<button type="button"class="btn btn-success btn-md" id="saveOrUpdate" data-save = "true"><#if menuId?has_content>更新<#else>保存</#if></button>
					</@ms.formRow>
					<div id="hideMenuId"></div>
				</@ms.form>		
	</@ms.panel>
</@ms.html5>



	<#noparse>
	<!---被选中的图文模板---->
	<script id="selectedArticle" type="text/x-jquery-tmpl">
		<div class="msg-item-wrapper" data-id="${newsId}">
			<div class="msg-item multi-msg">
				<div class="appmsgItem">
					<h4 class="msg-t"><a href="/static/article/detail/326302" class="i-title" target="_blank">${newsMasterArticle.basicTitle}</a></h4>           
					<p class="msg-meta"><span class="msg-date">${newsDateTime}</span></p>
					<div class="cover">
						<p class="default-tip" style="display:none">封面图片</p>
						<img src="${newsMasterArticle.basicThumbnails}" class="i-img">
					</div>
					<div class="msg-hover-mask"></div> 
					<div class="msg-mask"><span class="dib msg-selected-tip"></span></div>       
				</div>
				{{each(i,listChild) childs}}				
				<div class="rel sub-msg-item appmsgItem">
					<span class="thumb">
						<img src="${listChild.basicThumbnails}" class="i-img">
					</span>
					<h4 class="msg-t">
						<a href="javascript:;" target="_blank" class="i-title">${listChild.basicTitle}</a>
					</h4>
				</div>
				{{/each}}	   	
			</div>
	 	</div>
	</script>
	</#noparse>
<script>
	$(function(){
		<#if menuId?has_content>
			$("input[name='menuMenuId']").val("${menu.menuMenuId}");
			<#if picTextId?has_content>
				$("input[name='picTextId']").val("${picTextId}");
				setSelectedArticle($("input[name='picTextId']").val())
			</#if>		
			$("#saveOrUpdate").attr("data-save","false");
			//改变表单提交地址
			$("#menuForm").attr("action","${base}${baseManager}/weixin/menu/update.do");
			$("#hideMenuId").html('<input type="hidden" name="menuId" value="${menu.menuId}"/><input type="hidden" name="menuOauthId" value="${menu.menuOauthId}"/>')
			//获取菜单类型
			var menuStyle = ${menu.menuStyle};
			$(".r-module").each(function(index){
				if(menuStyle == "") {
					$(".menuText").show();
				}else{
					if($(this).attr("data-style")==menuStyle){
    					$(this).show();
	    			}else{
	    				$(this).hide();
	    			}
				}
	   		 });
		<#else>
			var parentId = window.parent.$("iframe").attr("data-parent-id");
			$("input[name='menuMenuId']").val(parentId);
		</#if>
		//点击开始进行保存Or更新
		$("#saveOrUpdate").click(function(){
			//获取按钮文字
			var btnHtml = $(this).html();
			$("#menuForm").attr("action","${base}${baseManager}/weixin/menu/save.do")
			var vobj = $("#menuForm").data('bootstrapValidator').validate();
			//表单验证
			if(vobj.isValid()){
				//对外连接的合法性判断
				var source_url = $.trim($("input[name='source_url']").val());
				if(source_url.length!=0 && source_url!=null && source_url!=undefined){
					source_url = source_url.match(/http:\/\/.+/);
					if(source_url == null){
						alert("外连接为http格式!");
						return;
					}
				}
				$("#saveOrUpdate").text(btnHtml+"中...")
				$(this).postForm("#menuForm",{
					func:function(msg) {
						if (msg.result) {
			     			alert(btnHtml+"成功");
			     			var data = jQuery.parseJSON(msg.resultMsg);
			     			var node = {
                				"name" :data.menuTitle,
                				"id" : data.menuId,
                				"pId": data.menuMenuId,
	       					 };
	       				 	//判断是保存还是更新，是保存则添加新节点
	       				 	if($("#saveOrUpdate").attr("data-save")=="true"){
	       				 		parent.addNode(node);
	       				 	}else{
	       				 		parent.updateNode(node);
	       				 	}
				    		$("#saveOrUpdate").html("更新");
				    		$("#saveOrUpdate").attr("data-save",false);
				    		$("#menuForm").attr("action","${base}${baseManager}/weixin/menu/update.do");
				    		$("#hideMenuId").html('<input type="hidden" name="menuId" value="'+data.menuId+'"/>')
			    		}else{
			    			alert(msg.resultMsg);
			    			$("#saveOrUpdate").html(btnHtml);
			    		}
			    		$("#saveOrUpdate").attr("disabled",false);
					}
				});
			}
		});		
		//当菜单类型改变时
		$("select[name='menuStyle']").change(function(){
			var menuStyle = $(this).children('option:selected').val();
		  	if(menuStyle != ""){  		
		  		$(".r-module").hide();
			    $(".r-module").each(function(index){
		    		if($(this).attr("data-style")==menuStyle){
		    			$(this).show();
			    	}else{
			    		$(this).hide();
			    	}		    	
			    });
		  	};
		  	$("")
		});
		//点击选择图文时弹出，图文消息列表
		$("body").delegate(".selArticle","click",function(){
			$(window.parent.showArticleChoice());  
		});	
	});
	//选择图文后，执行的函数
	function setSelectedArticle(resId){
		$("input[name='picTextId']").val(resId);
		$("input[name='picTextId']").attr("data-ajax-url","${base}${baseManager}/weixin/news/"+resId+"/get.do");
		//根据图文id获取图文的信息
		$("input[name='picTextId']").request({method:"post",func:function(data) {
			$("#article_reply").html("");
			$('#selectedArticle').tmpl(data).appendTo('#article_reply'); 
		}});	
	}
</script>
<!DOCTYPE html>
<html lang="en">
<head>
	<#include "${managerViewPath}/include/meta.ftl"/>
	<style>
		#menuTree span{
			display: inline-block;
		}
		#menuTree i{
			cursor: pointer;
		}
		iframe {
			width: 100%;
			z-index: 20;
			border: 0;
			margin: 0 auto;
			display: block;
			-moz-border-radius: 10px;
			-webkit-border-radius: 10px;
			border-radius: 10px;
		}
		.glyphicon {
			position: relative;
			top: 1px;
			display: inline-block;
			font-family: 'Glyphicons Halflings';
			font-style: normal;
			font-weight: normal;
			line-height: 1;
		}
		.row,.col-md-3{
			height:100%;
		}
		.col-md-3{
			border-right: 1px solid #ccc;
			float:left;
			padding-top: 5px;
			width:25%;
		}
		body .row{
			margin-top:-10px
		}
		.newsMessageListModal  .modal-body{padding:0}
		body .col-md-9{
			width:75%;
			float:left;
			padding-left:0;
			padding-right:0;
		}
		body .ms-content-body-panel{
			padding-left:0;
			padding-right:0;
		}
		body .modal-dialog .modal-content{
			width:100%;
		}
	</style>
</head>
<body>
	<@ms.content>
		<@ms.contentBody>
			<@ms.contentNav title="微信菜单">
				<button type="button" class="btn btn-primary" data-ajax-url="${base}${baseManager}/weixin/menu/generateMenu.do"  id="publishOrStop" data-placement="bottom">发布</button>
				<button type="button" class="btn btn-danger" data-ajax-url="${base}${baseManager}/weixin/menu/stopMenu.do" id="publishOrStop">停用</button>
			</@ms.contentNav >
			<@ms.contentPanel>
				<div class="row margin20" id="menuList">
					<!--菜单显示区域开始-->
					<div class="col-md-3">
						<ul id="menuTree" class="ztree">
						</ul>
					</div>
					<!--菜单显示区域结束-->
					<div class="col-md-9" id="menuDetail">
					</div>
				</div>
				<!--删除菜单模态框开始-->
				<@ms.modal  modalName="deleteModel" title="删除菜单" >
					<@ms.modalBody>
						确定要删除菜单？
					</@ms.modalBody>
					<@ms.modalButton>
						<!--模态框按钮组-->
						<@ms.button  value="确定" id="deleteMenu"/>
					</@ms.modalButton>
				</@ms.modal>
				<!--删除菜单模态框结束-->			
				<!--选取素材模态框开始-->
				<@ms.modal modalName="newsMessageListModal" title="选择图文素材">
					<@ms.modalBody>
						<iframe class="box-iframe"  id="article_frame" name="article_frame"  data-key="iframe"   src="${base}${baseManager}/weixin/news/listSelect.do" frameborder="0" scrolling="yes"  height="400px;" width="900px;"></iframe>
					</@ms.modalBody>
					<@ms.modalButton>
						<!--模态框按钮组-->
						<@ms.button  value="确定" id="select"/>
					</@ms.modalButton>
				</@ms.modal>	
				<!--选取素材模态框结束-->								
			</@ms.contentPanel>
		</@ms.contentBody>
	</@ms.content>
	<#noparse>
	 	<script id="addBtn" type="text/x-jquery-tmpl"> 
			<div style="float:right;margin-left:8px;" id="diyBtnAdd_${id}" data-id="${id}"><i class="glyphicon glyphicon-plus" title="添加菜单"></i></div>
	 	</script>
	 	<script id="editBtn" type="text/x-jquery-tmpl"> 
			<div style="float:right;margin-left:8px;" id="diyBtnEdit_${id}" data-id="${id}"><i class="glyphicon glyphicon-edit" title="编辑菜单"></i></div>
	 	</script>
		<script id="trashBtn" type="text/x-jquery-tmpl"> 
			<div style="float:right;margin-left:8px;" id="diyBtnDelete_${id}" data-id="${id}"><i class="glyphicon glyphicon-remove" title="删除菜单"></i></div>
	 	</script>
	</#noparse>
</body>
</html>
<script type="text/javascript">
	var menuId = 0;
	//进行字段删除的函数
	$("#deleteMenu").click(function(){
		$.ajax({
   			type: "post",
		   	dataType: "json",
		  	url:"${base}${baseManager}/weixin/menu/"+menuId+"/delete.do",
		   	data: "menuId=" + menuId,
		   	beforeSend:function(){
		   		$("#deleteMenu").attr("disabled",true);
		   	},
		   	success: function(msg){
		    	if(msg.result) {
					alert("删除菜单成功");
		    	}else{
					alert("菜单下面有子菜单，请先逐个删除子菜单！");
		    	}
		    	location.reload();
		   	},
		   	error: function(){
		   	  	alert("删除菜单失败");
		   	  	$("#deleteMenu").attr("disabled",false);
		   	}
		});
	});
				
   /*
	* 在节点旁添加按钮的函数
	* treeNode 被添加的节点
	* diyName :添加按钮对象的id名
	* url：点击按钮后要访问的地址
	* temlpName要添加的按钮的模板名：编辑按钮的模板和新增按钮的模板
	*/
	function addDiyBtn(treeNode,diyName,url,temlpName){
		if ($("#diyBtn_"+treeNode.id).length>0) return;
		var aObj = $("#" + treeNode.tId+"_a");
		$("#"+temlpName+"").tmpl({id:treeNode.id}).insertBefore(aObj);
		//添加按钮对象
		var btn = $("#"+diyName+""+treeNode.id);
		if (btn){
			 //给添加按钮绑定事件
			 btn.bind("click", function(){
		 		var defaults={title:'菜单管理',url:url,pId:$(this).attr("data-id")};
		 		//显示框架内容
      			showIframe(defaults);
		 		$("iframe").contents().find("input[name='menuMenuId']").val($(this).attr("data-id"));
 			});
		}
	}
	
	/*******在节点后面添加删除和编辑按钮开始*****/
	function addDiyDom(treeId, treeNode){
		var aObj = $("#" + treeNode.tId);
		var aObja =  $("#" + treeNode.tId+"_a");
		//当id不等于0时添加删除和编辑按钮
		if(treeNode.id!=0){
			$("#trashBtn").tmpl({id:treeNode.id}).insertBefore(aObja);
			addDiyBtn(treeNode,"diyBtnEdit_",'${base}${baseManager}/weixin/menu/'+treeNode.id+'/edit.do',"editBtn")
			var btn = $("#diyBtnDelete_"+treeNode.id);
			if(btn){
				//给删除按钮添加绑定事件
			 	btn.bind("click", function(){
			 		menuId = $(this).attr("data-id");
			 		$("body .modal-dialog").css({"width":"35%","margin":"10% auto"})
			 		$(".deleteModel").modal();
			 	});
			}
		}
		var treeObj = $.fn.zTree.getZTreeObj(treeId);
		//获取父节点为0的节点
		var nodes = treeObj.getNodesByParam("pId", 0, null);
		//当父id为0时添加新增的按钮
		if (treeNode.pId == 0 || treeNode.pId==null) {
			//一级菜单为3个时，则无添加按钮
			if(nodes.length>=3 && treeNode.id ==0) return;
			nodes = treeObj.getNodesByParam("pId", treeNode.id, null);
			//二级菜单为5时，父菜单无添加按钮
			if(nodes.length>=5) return;
			addDiyBtn(treeNode,"diyBtnAdd_",'${base}${baseManager}/weixin/menu/add.do',"addBtn")
		} 
	}
	/*******在节点后面添加删除和编辑按钮结束*****/
	
	
	
	//移动菜单结束后的回调函数,更新后台数据
	function zTreeOnDrop(event, treeId, treeNodes, targetNode, moveType) {
		//当目标节点的子节点
		var nodes = treeObj.getNodesByParam("pId",targetNode.id, null);
		//当子节点个数超过5时，移除新增按钮(二级菜单个数)
		if (targetNode.pId == 0  &&(nodes.length>=5)){
				 $("#diyBtnAdd_"+targetNode.id).remove();
		}			
		//当一级菜单的子节点超过3时
		if(targetNode.id == 0  &&(nodes.length>=3)){
			 $("#diyBtnAdd_"+targetNode.id).remove();
		}
		//节点由父节点变成子节点时，移除新增按钮  (剔除了同级目录下打乱顺序的移动产生的bug)
		if (targetNode.pId != null && targetNode.pId == 0 && targetNode.open == true){
				 $("#diyBtnAdd_"+treeNodes[0].id).remove();
		}
		//获取一级菜单
		nodes =  treeObj.getNodesByParam("pId",0, null);
		//如果一级菜单小于3，添加新增按钮
		if(nodes.length<3){
			nodes =  treeObj.getNodesByParam("id",0, null);
			$("#diyBtnAdd_"+nodes[0].id).remove();
			addDiyBtn(nodes[0],"diyBtnAdd_",'${base}${baseManager}/weixin/menu/add.do',"addBtn");
		}
		//节点由子节点变成父节点时,添加新增按钮
		if(targetNode.id==0){
			if ($("#diyBtn_"+treeNodes[0].id).length>0) return;
			//调用添加新增按钮函数
			addDiyBtn(treeNodes[0],"diyBtnAdd_",'${base}${baseManager}/weixin/menu/add.do',"addBtn");
		}
		var menuMenuId = 0;
		//排除同级节点下前后的移动对menuMenuId的影响
		if(targetNode!=null && targetNode.open !=false){
			menuMenuId = targetNode.id;
		}
		$.ajax({
   			type: "post",
		   	dataType: "json",
		  	url:"${base}${baseManager}/weixin/menu/"+ treeNodes[0].id+"/"+menuMenuId+"/changeParent.do",
		   	data: "menuId=" + treeNodes[0].id+"&menuMenuId="+menuMenuId,
		   	success: function(msg){
		   		if(msg.result){
		   			alert("菜单移动成功");
		   		 }
		   	}
		});
	};			
	//移到目标节点前时触发
	function zTreebeforeDrop(treeId,treeNodes,targetNode,moveType){
		var treeObj = $.fn.zTree.getZTreeObj(treeId);
		//当目标对象为空,且目标节点的id为空时，不能移动
		if(targetNode==null){
			return false;
		}
		//节点不能移动到与最顶级节点‘自定义列表’”同级的地方
		if(targetNode.id==0 && moveType=="next" ){
			return false;
		}
		//移动时不能将菜单移动到二级菜单下
		if(targetNode.pId!=0 && targetNode.pId!=null){
			return false;
		}
		//模板对象是“最顶级节点‘自定义列表’”的前面时，不能移动节点
		if(targetNode.id==0&&moveType=="prev"){
			return false;
		}
		//当目标节点的子节点个数等于5时 ,不能进行移动
		var nodes = treeObj.getNodesByParam("pId",targetNode.id, null);
		if(nodes.length>=5 ){
			alert("子节点最多只能为5，不能进行移动");
			return false;
		}
		//移动时保证一级菜单的数量不超过3
		if(targetNode.id==0 &&nodes.length>=3 ){
			return false;
		}
		//当目标节点
		if(treeNodes[0].isParent &&targetNode.pId==0&&targetNode.id!=0){
			return false;
		}
		return true;
	}	
	/******设置节点信息开始******/
	var zNodes = new Array();
	zNodes[0]={ id:0,pId:0,name:'自定义菜单', open:true,type:1};
	var treeObj = $.fn.zTree.getZTreeObj("menuTree");
 	var menuList = ${menuList};
 	for(var i=0;i<menuList.length;i++){
		zNodes[i+1]={id:menuList[i].menuId,name:menuList[i].menuTitle,pId:menuList[i].menuMenuId,open:true};
	}
	//设置信息
	var setting = {
		edit: {
			enable: true,
			showRemoveBtn: false,
			showRenameBtn: false,
			drag: {
				prev: true,
				next: true,
				inner: true,
				isMove: true
			}
		},
		view: {
			addDiyDom: addDiyDom
		},
		callback: {
			beforeDrop:zTreebeforeDrop,
			onDrop:zTreeOnDrop,
		},
		data: {
			simpleData: {
				enable: true
			}
		}
	};
	/******设置节点信息结束******/
	
	/********给树形框架添加节点添加新的节点*****/
	function addNode(newNode){
		var treeObj = $.fn.zTree.getZTreeObj("menuTree");
			var  parentNode = null
		if(newNode.pId ==0){
			parentNode = treeObj.getNodeByParam("id", 0, null);
		}else{
			parentNode = treeObj.getNodeByParam("id", newNode.pId, null);
		}
		newNode = treeObj.addNodes(parentNode, newNode);
		addDiyDom("menuTree",newNode);
		var menuLength = 0;
		//判断父id为0则说明添加的是一级菜单
		if(parentNode.id==0){
			menuLength  =  treeObj.getNodesByParam("pId", 0, null).length;
			//如果一级菜单为3个时,移除顶级的新增按钮
			if(menuLength>=3){
				$("#diyBtnAdd_"+parentNode.id).remove();
			}
		}else{
			menuLength  =  treeObj.getNodesByParam("pId", parentNode.id, null).length;
			//如果二级菜单为5个时，移除该菜单的新增按钮
			if(menuLength>=5){
				$("#diyBtnAdd_"+parentNode.id).remove();
			}
		}
	}
	/********更新树形框架的某个节点*****/
	function updateNode(oldNode){
		var treeObj = $.fn.zTree.getZTreeObj("menuTree");
		var node = treeObj.getNodeByParam("id", oldNode.id, null);
		node.name = oldNode.name;
		treeObj.updateNode(node);
	}
	
	//图文选择回调
	function setSelectedArticle(resId){
		window.frames["detail"].setSelectedArticle(resId);
	}
	$(document).ready(function(){
		$.fn.zTree.init($("#menuTree"), setting, zNodes);
		treeObj = $.fn.zTree.getZTreeObj("menuTree");		
		//点击发布按钮，开始菜单的发布
		$(".ms-content-body-title #publishOrStop").click(function(){
			var html = $(this).text();
			$(this).text(html+"中");
			$(this).attr("disabled","disabled");
			$(this).request({
				method:"post",
				func:function(data) {
					var obj = jQuery.parseJSON(data)
					if (obj.result) {
			     		alert("菜单"+html+"成功");
			    	}else{
			    		alert(obj.resultMsg);
			    	}
			    	$(".ms-content-body-title #publishOrStop").text(html);
					$(".ms-content-body-title #publishOrStop").attr("disabled",false);
				}
			});
		});
		//选择弹出框的内容
		$(".newsMessageListModal #select").click(function(){
			var resId = window.frames["article_frame"].getSelected();
			if(resId == null){
				alert("请至少选择一个");
				return;
			}
			$(".newsMessageListModal").modal("hide");
			$(".modal-backdrop").toggle();
			//如果是图文的弹出框
			if($(".newsMessageListModal").attr("data-article")=="true"){
				setSelectedArticle(resId);
			}			
		});
	});
	//显示框架内容
    function showIframe(defaults){
    	iframeHeight =  $("#menuList").height();
   		if(defaults.url!=''){
   			$("#menuDetail .alert").hide();
			$("#menuDetail").html('<iframe class="box-iframe" height="'+iframeHeight+'" data-key="iframe"  id="detail" name="detail" src="'+defaults.url+'" frameborder="0" scrolling="yes" data-parent-id="'+defaults.pId+'"></iframe>');
		}
   	}   		   
	//显示图文选择模态框
	function showArticleChoice(){
		$(".newsMessageListModal").attr("data-article",true)
		$("#article_frame").attr("src","${managerPath}/weixin/news/listSelect.do");
		$("body .modal-dialog").css({"width":"81%","margin":"5% auto"})
		$(".newsMessageListModal").modal();
	}
	//点击添加进行多图文链接
	function showAddMulArticle(){
		location.href="${managerPath}/weixin/news/0/add.do";
	}
	//点击添加进行单图文链接
	function showAddArticle(){
		location.href="${managerPath}/weixin/news/1/add.do";
	}
</script>
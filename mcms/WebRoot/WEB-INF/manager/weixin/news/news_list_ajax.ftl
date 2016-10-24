<style> 
.appmsg {
	position: relative;
	overflow: hidden;
	margin-bottom: 20px;
	background-color: #fff;
	color: #666;
	cursor: pointer;
	border:1px solid #ccc;
}
.appmsg  .mask{
	display: none;
	position: absolute;
	top: 0;
	left: 0;
	width: 100%;
	height: 100%;
	background-color: #000;	
	-moz-opacity: .6;
	-khtml-opacity: .6;
	opacity: .6;
	filter: alpha(opacity = 60);
	z-index: 1;
	color: white;
}

.appmsg_info {
	font-size: 13px;
	line-height: 20px;
	padding-bottom: 10px
}

.appmsg_date {
	font-weight: 400;
	font-style: normal
}

.appmsg_content {
	padding: 0 14px;
	position: relative;
	*zoom: 1
}

.appmsg_title {
	font-weight: 400;
	font-style: normal;
	font-size: 16px;
	padding:0;
	margin:0;
	line-height: 45px;
	max-height: 56px;
	overflow: hidden;
	word-wrap: break-word;
	word-break: break-all
}

.appmsg_title a {
	display: block;
	color: #666
}

.appmsg_thumb_wrp {
	height: 160px;
	overflow: hidden
}

.appmsg_thumb {
	width: 100%;
	height:100%;
}

.appmsg_desc {
	padding:0;
	word-wrap: break-word;
	word-break: break-all
}

.appmsg_opr {
	background-color: #f4f4f4;
	border-top: 1px solid #e7e7eb
}

.appmsg_opr ul {
	overflow: hidden;
	*zoom: 1
}

.appmsg_opr_item {
	float: left;
	line-height: 44px;
	height: 44px;
	width: 49%;
	font-size: 17px;
}

.appmsg_opr_item a {
	display: block;
	border-right: 1px solid #e7e7eb;
	text-align: center;
	text-decoration: none;
	cursor: pointer;
}

.appmsg_opr_item a:hover {
	text-decoration: none
}

.appmsg_opr_item a.no_extra {
	border-right-width: 0
}

.appmsg_item {
	*zoom: 1;
	position: relative;
	padding: 20px 14px;
	border-top: 1px solid #e7e7eb
}

.appmsg_item:after {
	content: "\200B";
	display: block;
	height: 0;
	clear: both
}

.appmsg_item .appmsg_title {
	line-height: 24px;
	max-height: 48px;
	overflow: hidden;
	*zoom: 1;
	margin-top: 14px
}

.appmsg_item .appmsg_thumb {
	float: right;
	width: 78px;
	height: 78px;
	margin-left: 14px
}

.multi .appmsg_info {
	padding-top: 14px;
	padding-left: 14px;
	padding-right: 14px
}

.multi .appmsg_content {
	padding: 0
}

.multi .appmsg_title {
	font-size: 14px;
	padding-top: 0
}

.cover_appmsg_item {
	position: relative;
	margin: 0 14px 14px
}

.cover_appmsg_item .appmsg_title {
	position: absolute;
	bottom: 0;
	left: 0;
	width: 100%;
	background: rgba(0,0,0,0.6)!important;
	color: white;
	filter: progid:DXImageTransform.Microsoft.gradient(GradientType=0,startColorstr='#99000000',endcolorstr = '#99000000')
}

.cover_appmsg_item .appmsg_title a {
	padding: 0 8px;
	color: #fff
}

.appmsg_mask {
	display: none;
	position: absolute;
	top: 0;
	left: 0;
	width: 100%;
	height: 100%;
	background-color: #000;
	filter: alpha(opacity = 60);
	-moz-opacity: .6;
	-khtml-opacity: .6;
	opacity: .6;
	z-index: 1
}

.appmsg .icon_card_selected {
	display: none;
	position: absolute;
	top: 50%;
	left: 50%;
	margin-top: -23px;
	margin-left: -23px;
	line-height: 999em;
	overflow: hidden;
	z-index: 1
}

.dialog_wrp .appmsg:hover {
	cursor: pointer
}

.appmsg:hover .appmsg_mask {
	display: block
}

.appmsg.selected .appmsg_mask {
	display: block
}

.appmsg.selected .icon_card_selected {
	display: inline-block
}

.appmsg_thumb.default {
	display: block;
	color: #c0c0c0;
	text-align: center;
	line-height: 160px;
	font-weight: 400;
	font-style: normal;
	background-color: #ececec;
	font-size: 16px
}

.appmsg_item .appmsg_thumb.default {
	line-height: 78px;
	font-size: 14px
}

.appmsg_edit_mask {
	display: none;
	position: absolute;
	top: 0;
	left: 0;
	width: 100%;
	height: 100%;
	background: rgba(229,229,229,0.85)!important;
	filter: progid:DXImageTransform.Microsoft.gradient(GradientType=0,startColorstr='#d9e5e5e5',endcolorstr = '#d9e5e5e5');
	text-align: center
}

.appmsg_item .appmsg_edit_mask {
	line-height: 118px
}

.cover_appmsg_item .appmsg_edit_mask {
	line-height: 160px
}

.appmsg_edit_mask a {
	margin-left: 8px;
	margin-right: 8px
}

.editing .cover_appmsg_item:hover .appmsg_edit_mask,.editing .appmsg_item:hover .appmsg_edit_mask {
	display: block
}

.editing .appmsg_thumb {
	display: none
}

.editing .appmsg_thumb.default {
	display: block
}

.editing .has_thumb .appmsg_thumb {
	display: block
}

.editing .has_thumb .appmsg_thumb.default {
	display: none
}

.editing .appmsg_content {
	box-shadow: none;
	-moz-box-shadow: none;
	-webkit-box-shadow: none;
	border-bottom-width: 0
}

.editing.multi .appmsg_content {
	border-bottom-width: 1px
}

.appmsg_add {
	margin: 20px 14px
}

.tab_content .appmsg {
	width: 320px
}

.appmsg_list {
	text-align: justify;
	text-justify: distribute-all-lines;
	font-size: 0;
	margin: 0 30px;
	letter-spacing: -4px
}

.appmsg_list:after {
	display: inline-block;
	width: 100%;
	height: 0;
	font-size: 0;
	margin: 0;
	padding: 0;
	overflow: hidden;
	content: "."
}

.appmsg_list .tj_item {
	font-size: 14px;
	text-align: left;
	text-justify: auto
}

.appmsg_col {
	display: inline-block;
	*display: inline;
	*zoom: 1;
	vertical-align: top;
	width: 32%;
	font-size: 14px;
	text-align: left;
	font-size: 14px;
	letter-spacing: normal
}

.appmsgContentArea {
	word-wrap: break-word;
	word-break: break-all
}

.appmsgImgArea {
	float: left;
	margin-right: 4px;
	padding: 3px
}

.appmsgImgArea img {
	width: 80px;
	height: 80px;
	display: block
}

.appmsgContentArea {
	overflow: hidden;
	*zoom: 1
}

.appmsgContentArea.multiple .appmsgTitle {
	margin-top: 10px
}

.appmsgContentArea.multiple .appmsgTitle:first-child {
	margin-top: 0
}

.appmsgContentArea .appmsgTitle {
	font-size: 14px;
	line-height: 21px
}

.appmsgContentArea .appmsgDesc {
	font-size: 14px;
	color: #8d8d8d;
	line-height: 21px
}

.appmsgContentArea .icon_vote {
	background: url("/mpres/zh_CN/htmledition/comm_htmledition/style/widget/media_z213560.png") 0 -100px no-repeat;
	width: 18px;
	height: 16px;
	vertical-align: middle;
	display: inline-block
}

.appmsgFrom {
	font-size: 12px;
	color: #222
}

.appmsgFrom.resource {
	margin-left: 90px;
	margin-top: 10px
}

.appmsgSendedItem {
	position: relative;
	padding-left: 90px;
	min-height: 80px
}

.appmsgSendedItem .title_wrp {
	color: #222;
	display: inline-block;
	*display: inline;
	*zoom: 1
}

.appmsgSendedItem .title_wrp .icon {
	position: absolute;
	left: 0;
	background: transparent url() no-repeat 0 0;
	width: 80px;
	height: 80px;
	vertical-align: middle;
	display: inline-block;
	background-color: #d7d8da!important
}

.appmsgSendedItem .title_wrp:hover .icon.icon_lh {
	background-color: #c5c6c8!important
}

.appmsgSendedItem .appsmg_item {
	margin-top: 10px
}

.appmsgSendedItem .appsmg_item:first-child {
	margin-top: 0
}

.appmsgSendedItem .desc,.appmsgSendedItem .desc a.appmsg_desc {
	color: #666
}

.appmsgSendedItem .icon_vote {
	background: url("/mpres/zh_CN/htmledition/comm_htmledition/style/widget/media_z213560.png") 0 -126px no-repeat;
	width: 18px;
	height: 16px;
	vertical-align: middle;
	display: inline-block;
	margin-right: 5px;
	vertical-align: -2px
}
.newsList{
	width:31%;
	float:left;
	margin-right: 2%;
}
</style>
		<!--列表开始-->
		<div class="row" id="newsListContainer">		
   		<#if newsList?has_content>
   			<#list newsList as list>		
   				<#if list.newsType==0>
				<!--单图文开始-->
				<div  class="newsList">
					<div class="appmsg " data-id="${list.newsId}">
					    <div class="appmsg_content">			        
				            <h4 class="appmsg_title">${list.newsMasterArticle.basicTitle?default('')}</h4>
				            <div class="appmsg_info">
				                <em class="appmsg_date">${list.newsDateTime?string('MM月dd日')}</em>
				            </div>
				            <div class="appmsg_thumb_wrp"><img src="${list.newsMasterArticle.basicThumbnails?default('')}" alt="" class="appmsg_thumb"></div>
				            <p class="appmsg_desc">${list.newsMasterArticle.basicDescription?default('')}</p>
					    </div>
					    <div class="mask"><i class='icon iconfont' style='font-size:60px;position: absolute;top: 36%;left: 40%;display:none;'>&#xe693;</i></div>
				    </div>
				</div>
				<!--单图文结束-->
				<#elseif list.newsType==1>	
				<!--多图文开始-->
				<div  class="newsList">
					<div class="appmsg multi" data-id="${list.newsId}">
					    <div class="appmsg_content">
				            <div class="appmsg_info">
				                <em class="appmsg_date">${list.newsDateTime?string('MM月dd日')}</em>
				            </div>
				            <div class="cover_appmsg_item">
				                <h4 class="appmsg_title">${list.newsMasterArticle.basicTitle?default('')}</h4>
				                <div class="appmsg_thumb_wrp"><img src="${list.newsMasterArticle.basicThumbnails?default('')}" alt="" class="appmsg_thumb"></div>
				            </div>
	       					<#if list.childs?has_content>
	       						<#list list.childs as listChild>
						            <div class="appmsg_item">
						                <img src="${listChild.basicThumbnails?default('')}" alt="" class="appmsg_thumb">
						                <h4 class="appmsg_title">${listChild.basicTitle?default('')}</h4>
						            </div>		       							
	       						</#list>
	       					</#if>					            
					    </div>
					    <div class="mask"><i class='icon iconfont' style='font-size:60px;position: absolute;top: 36%;left: 40%;display:none;'>&#xe693;</i></div>
					</div>
				</div>
				<!--多图文结束-->
				<#elseif list.newsType==2>
				<!--文本开始-->
				<div  class="newsList">
					<div class="appmsg " data-id="${list.newsId}">
					    <div class="appmsg_content">
				            <div class="appmsg_info" style="margin-top:10px">
				                <em class="appmsg_date">${list.newsDateTime?string('MM月dd日')}</em>
				            </div>					        
				            <p class="appmsg_desc" style="min-height:200px">${list.newsContent?default('')}</p>
					    </div>
				      	<div class="mask"><i class='icon iconfont' style='font-size:60px;position: absolute;top: 36%;left: 40%;display:none;'>&#xe693;</i></div>
					</div>
				</div>
				<!--文本结束-->
		        <#elseif list.newsType==3>
					图片
				</#if>
			</#list>
		<#else>
			<div class="alert alert-info" role="alert">您还没有添加素材</div>
		</#if>				
	</div>			    
	<!--列表结束-->

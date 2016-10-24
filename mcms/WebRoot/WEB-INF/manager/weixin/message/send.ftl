<!DOCTYPE html>
<html lang="en">
<head>
<#include "${managerViewPath}/include/meta.ftl"/>
</head>
<style>
.weixinEditer .editArea {
  border: 1px solid #AAA;
  border-top: 0;
  padding: 20px;
  background-color: #FFF;
}

.weixinEditer  .editArea div{
  border: 1px solid #DDD;
  width: 100%;
     height: 80px;  
}

.weixinEditer .editArea textarea {
     border: 1px solid #DDD;
  width: 100%;
  height: 80px;
}

.weixinEditer .functionBar {
  height: 40px;
  border: 1px solid #AAA;
  padding: 10px 20px;
  background-color: #FFF;
  position: relative;
  box-shadow: 0 3px 3px #ddd;
  -moz-box-shadow: 0 3px 3px #ddd;
  -webkit-box-shadow: 0 3px 3px #ddd;
}

.weixinEditer .functionBar .opt {
  float: left;
}
</style>
<body>
	<div class="container-fluid">
    	<!--头部开始-->
        <div class="row">
          	<div class="col-md-10">
            	<h3 class="page-title bottomLine">
              		订单回复
              		<small>回复的内容</small>
            	</h3>
          	</div>
          	<div class="col-md-2 text-right">
            	<button class="btn btn-default" role="button" onclick="javascript:history.go(-1)">返回</button>
          	</div>
        </div>
    	<!--头部结束-->
    	<hr>
    	<!--主体开始-->
    	<div class="row">
			<form role="form" method="post" action="" id="messageForm" name="messageForm" class="form-horizontal">
                <div class="form-group ms-form-group" style="height:156px;margin:0;padding:0 13px;">	
                    <label class=" control-label text-right ms-form-label">回复内容</label>		
                    <div class="ms-form-control">
                        <div class="weixinEditer" style="width:600px">
                            <div class="functionBar"></div>
                            <!--文本内容区域A-->
                            <div class="editArea">
                                <textarea style="display: none;" name="messageContent" id="messageContent"></textarea>
                                <div contenteditable="true" style="overflow-y: auto; overflow-x: hidden;"></div>
                            </div>
                            <!--文本内容区域B-->
                        </div> 
                    </div>
                </div>
                <div class="form-group ms-form-group" style="clear:both;margin:0;padding:20px 13px">
                    <label class=" control-label text-right ms-form-label"> </label>
                    <div class="ms-form-control">
                        <button type="button" class="btn btn-success" id="sendButton" data-id="${openId?default('0')}">发送</button>	
                    </div>
                </div>
			</form>
    	</div>
    	<!--主体结束--> 
 	</div>
	<script>
		//点击发送按钮将信息发送给用户
		$("#sendButton").click(function(){
			var actionUrl =  "${managerPath}/weixin/message/"+$(this).attr("data-id")+"/sendText.do"; 
			$("#messageContent").val($("#messageContent").next("div").text());
	    	$(this).postForm("#messageForm",{action:actionUrl,func:function(data) {
				if(data.result){
					alert("发送成功");
				}else{
					alert(data.resultMsg);
				}
			}});
		});
		function save(target) {
	    	
	    }		
	</script>
</body>
</html>

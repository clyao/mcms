<#macro weixinediter name="" id="" value="" style="">
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
<div class="weixinEditer" style="${style}">
	<div class="functionBar"></div>
    <!--文本内容区域A-->
    <div class="editArea">
      <textarea<#if id!=""> id="${id}"</#if><#rt/> <#if name!=""> name="${name}" <#if id="">id=${name}</#if></#if><#rt/> style="display: none;"><#if value!="">${value}</#if></textarea>
      <div contenteditable="true" style="overflow-y: auto; overflow-x: hidden;" ><#if value!="">${value}</#if></div>
    </div>
    <!--文本内容区域B-->
</div> 
</#macro>
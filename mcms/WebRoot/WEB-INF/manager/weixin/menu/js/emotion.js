Emotion={
	url:"/images/qqface/",
	ext:".png",replaceEmoji:function(f){
	var b,h,d=Emotion,a=d.url,c=d.ext,g=d.data;
		/*for(b in g){
			h=new RegExp("/"+g[b],"g");
			f=f.replace(h,'<img src="'+a+b+c+'" alt="mo-'+g[b]+'"/>').replace(/\n/g,"<br />");
		}*/
		return f;
	},replaceInput:function(a){
		return a.replace(/<img.*?alt=["]{0,1}mo-([^"\s]*).*?>/ig,"/$1").replace(/<br.*?>/ig,"\n").replace(/<.*?>/g,"").replace(/&amp;/gi,"&").replace(/&quot;/gi,'"').replace(/&nbsp;/gi," ").replace(/&copy;/gi,"©").replace(/&reg;/gi,"®");
	},getSelection:function(){
		return document.selection?document.selection:window.getSelection();
	},getRange:function(c){
		var d=Emotion;
		var a=d.getSelection();
		if(!a){
			return null;
		}
		var b=a.getRangeAt?a.rangeCount?a.getRangeAt(0):null:a.createRange();
		return b?c?d.containsRange(c,b)?b:null:b:null;
	},contains:function(c,a,d){
		if(!d&&c===a){
			return !1;
		}
		if(c.compareDocumentPosition){
			var b=c.compareDocumentPosition(a);
			if(b==20||b==0){
				return !0;
			}
		}else{
			if(c.contains(a)){
				return !0;
			}
		}
		return !1;
	},
	containsRange:function(c,a){
		var d=Emotion;
		var b=a.commonAncestorContainer||a.parentElement&&a.parentElement()||null;
		return b?d.contains(c,b,!0):!1;
	},saveRange:function(){
		Emotion._lastRange=Emotion.getRange();
	},resotreRange:function(){
		var c=Emotion._lastRange;var b=Emotion;
		if(c){
			var a=b.getSelection();
			if(a.addRange){
				a.removeAllRanges(),a.addRange(c);
			}else{
				var d=b.getRange();
				d.setEndPoint("EndToEnd",c),d.setEndPoint("StartToStart",c),d.select();
			}
		}
		return this;
	},focus:function(c){
		$(".editArea div").focus();
		var b;
		if(c&&(b=Emotion._lastRange)){
			var d=Emotion.getSelection();
			if(d.addRange){
				d.removeAllRanges();
				d.addRange(b);
			}else{
				var a=Emotion.getRange();
				a.setEndPoint("EndToEnd",b);
				a.setEndPoint("StartToStart",b);
				a.select();
			}
		}
		return Emotion.resotreRange();
	},insertHTML:function(d){
		Emotion.focus(1);
		var b=Emotion.getRange();
		if(b.createContextualFragment){
			d+='<img style="width:1px;height:1px;">';
			var f=b.createContextualFragment(d),a=f.lastChild;
			b.deleteContents(),b.insertNode(f),b.setEndAfter(a),b.setStartAfter(a);
			var c=Emotion.getSelection();
			c.removeAllRanges(),c.addRange(b),document.execCommand("Delete",!1,null);
			}else{
				b.pasteHTML(d);b.collapse(!1);b.select();
			}
		Emotion.saveRange();
		return this;
		}
	};

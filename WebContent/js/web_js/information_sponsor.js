function information_sponsor(){
	var strs = document.getElementByName('aa');
	var str;
	for(i in strs){
		if(strs[i].checked){
			str += strs[i].value;
		}
	}
	$.ajax({
		type:"get",
	    url:'',
	    data:{},
	    dataType:'jsonp',
	    jsonp:'callback',
	    success:function(t){
	    	if(t.status == 1){
	    		alert("成功");
	    	}else{
	    		alert("失败");
	    	}
	    }
	})
}
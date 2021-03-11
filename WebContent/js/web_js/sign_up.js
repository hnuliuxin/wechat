function rigister(){
	var user_name = document.getElementById("user_name").value;
	var user_password = document.getElementById("user_password").value;
	var user_ID = document.getElementById("user_ID").value;
	var openID = 0;

	var url = "http://daipeng.nat300.top/sign_up_servlet";

    var index = location.href;
    var open_ID =index.substr(index.indexOf('=') + 1) ;

	if(user_name == "" || user_name == null || user_password == "" || user_password == null || user_ID == "" || user_ID == null){
		alert("请填满所有空文本！！！");
		return ;
	}else{	
		$.ajax({
			type:"post",
			url:url,
		 	data:{'user_name':user_name,'user_password':user_password,'user_ID':user_ID,'open_ID':open_ID},
		 	dataType:"jsonp",
		 	jsonp:"callback",
		 	success:function(t){
		 		if(t.status == 0){
		 			alert('该学号已存在');
		 		}else{
		 			alert('注册成功');
		 		}
		 	}
		 })
		var url="index.html?user_ID="+user_ID;
		window.location.href=url;
	}
}
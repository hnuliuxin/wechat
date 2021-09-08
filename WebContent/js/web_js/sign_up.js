function rigister(){
	var user_name = document.getElementById("user_name").value;//获取id为user_name的value
	var user_password = document.getElementById("user_password").value;//获取id为uuser_password的value
	var user_ID = document.getElementById("user_ID").value;//获取id为user_ID的value
	var openID = 0;

	var url = "http://daipeng.nat300.top/sign_up_servlet";//后端的java servlet（即前端传值）

    var index = location.href;
    var open_ID =index.substr(index.indexOf('=') + 1) ;//分离得到open_ID

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
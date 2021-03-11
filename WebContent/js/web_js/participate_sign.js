function participate_sign(){
	var sign_num = document.getElementById('sign_num').value;
	var index=window.location.search;//取问号后面的值
	var user_ID=index.substr(index.indexOf("?user_ID=")+9);

	var  location_Latitude =1.1;
	var location_Longitude =2.2;
	var location_Precision =2.3;

	$.ajax({
		type:"get",
		url:'http://daipeng.nat300.top/participate_sign_servlet',
		data:{'sign_num':sign_num,'user_ID':user_ID,'location_Latitude':location_Latitude,'location_Longitude':location_Longitude,'location_Precision':location_Precision},
		dataType:'jsonp',
		jsonp:'callback',
		success:function(t){
			if(t.status=="1"){
				alert("签到成功");
				url = "index.html?user_ID="+user_ID;
				window.location.href = url;
			}else{
				alert("签到失败");
				alert(t.msg);
			}
		}
	})
}
	
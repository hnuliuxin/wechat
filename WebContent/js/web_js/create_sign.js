function create_sign(){
	var index=window.location.search;

	var user_ID = index.substr(index.indexOf("?user_ID=")+9);

	var date = new Date();
	var year = date.getFullYear();
	var month = date.getMonth()+1;
	var day = date.getDate();
	var hour = date.getHours();
	var minute = date.getMinutes();
	var seconds = date.getSeconds();
	if(month<10){
		month = "0" + month;
	}
	if(day<10){
		day = "0" + day;
	}
	var start_time = hour + ":" + minute + ":" + seconds;

	var end_time_one = document.getElementById('end_time').value;

	var end_time = end_time_one + ":" + seconds;
    
	var record_date = year + "-" + month + "-" + day;//年月日

	var end_hour = end_time_one.substr(0,2);
	var end_minute = end_time_one.substr(3,5);
	var  location_Latitude =1.1;
	var location_Longitude =2.2;
	var location_Precision =2.3;
	
	
	if(end_time_one == "" || end_time_one == null){

		alert("请输入结束时间");
		}
		else
		{
			if(end_hour>hour){
				$.ajax({
					type:"get",
					url:'http://daipeng.nat300.top/create_sign_servlet',
					data:{'do_type':'2','record_date':record_date,'user_ID':user_ID,'end_time':end_time,'location_Latitude':location_Latitude
					,'location_Longitude':location_Longitude,'location_Precision':location_Precision},
					dataType:'jsonp',
					jsonp:'callback',
					success:function(t){
						if(t.status=="1"){
							alert("创建成功");
							url = "sponsor_record.html?user_ID="+user_ID;
							window.location.href = url;
						}else{
							alert("创建失败");
						}
					}
				})
			}else{
				if(end_hour == hour){
					if(end_minute > minute){
						$.ajax({
							type:"get",
							url:'http://daipeng.nat300.top/create_sign_servlet',
							data:{'do_type':'2','record_date':record_date,'user_ID':user_ID,'end_time':end_time,'location_Latitude':location_Latitude
							,'location_Longitude':location_Longitude,'location_Precision':location_Precision},
							dataType:'jsonp',
							jsonp:'callback',
							success:function(t){
								if(t.status=="1"){
									alert("创建成功");
									url = "sponsor_record.html?user_ID="+user_ID;
									window.location.href = url;
								}else{
									alert("创建失败");
								}
							}
						})
					}else{
						alert("输入时间错误，请重新输入");
					}
				}else{
					alert("输入时间错误，请重新输入");
				}
			}



		}

	
	// var sign_num;//签到码
	// var min=1000;
	// var max=9999;
	// var res=Math.floor(min+Math.random()*(max-min));
	// sign_num = res;

	
}
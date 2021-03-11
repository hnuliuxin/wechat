function startsignin(){
	var index=window.location.search;//取问号后面的值
	var user_ID=index.substr(index.indexOf("?user_ID=")+9);
	var url = "create_sign.html?user_ID="+user_ID;
	window.location.href=url;
}
function participate_sign(){
	var index=window.location.search;//取问号后面的值
	var user_ID=index.substr(index.indexOf("?user_ID=")+9);
	var url = "participate_sign.html?user_ID="+user_ID;
	window.location.href=url;
}
function sponsor_record(){
	var index=window.location.search;//取问号后面的值
	var user_ID=index.substr(index.indexOf("?user_ID=")+9);
	var url = "sponsor_record.html?user_ID="+user_ID;
	window.location.href=url;
}
function participate_record(){
	var index=window.location.search;//取问号后面的值
	var user_ID=index.substr(index.indexOf("=")+1);
	var url = "participate_record.html?user_ID="+user_ID;
	window.location.href=url;
}
function sign_space(){
	var index=window.location.search;//取问号后面的值
	var user_ID=index.substr(index.indexOf("=")+1);
	var url = "sign_space.html?user_ID="+user_ID;
	
	window.location.href=url;
}
function online_help(){
	var index=window.location.search;//取问号后面的值
	var user_ID=index.substr(index.indexOf("=")+1);
	var url = "online_help.html?user_ID="+user_ID;
	
	window.location.href=url;
}
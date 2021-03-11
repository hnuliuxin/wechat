function sign_space(){
	var space_name = document.getElementById('space_name').value;
	var formData = new FormData();
	var name = $("#excel_file").val();
	formData.append("file",$("#excel_file")[0].files[0]);
	formData.append("file_name",name);
	alert($("#excel_file")[0].files[0]);
	alert(name);
	$.ajax({
		type:"post",
	    url:'http://daipeng.nat300.top/sign_space_servlet',
	    data:formData,
	    dataType:'jsonp',
	    jsonp:'callback',
	    success:function(t){
	     	if(t.status == 1){
	     	    alert("OK");
	     	}
	    }
	})
}

	
	

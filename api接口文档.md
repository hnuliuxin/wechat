# **API接口文档**

## Loading

### 业务逻辑：判断用户是否注册过    

传入json格式

```json
{
	"code":code
}
```

输出jison格式(数据库中有该用户)

```json
{
	"status":1,
    "msg":"In there",
    "userID":user_ID
}
```

(数据库中没有该用户)

```json
{
    "status":0,
    "msg":"Not found in database",
    "open_ID"：open_ID
}
```

## sign_up

### 业务逻辑：注册账号

传入格式

```json
{
    "user_name":user_name,
    "user_password":user_password,
    "user_ID":user_ID
}
```

返回数据格式(失败)

```json
{
    "status":status,								 //返回状态
    "msg":msg									     //返回信息
}
```



## create_sign

### 业务逻辑：随机生成时间戳和字符串，向后端请求签名

传入格式

```json
{
    "url":url,									   
    "timestamp":timestamp,							//随机时间戳
    "nonceStr":nonceStr								//随机生成字符串
}
```

返回数据格式

```json
{
    "timestamp":timestamp,							//随机时间戳
    "nonceStr":nonceStr,							//随机生成字符串
    "signature":signature							//签名
}
```

### 业务逻辑：调用微信API接口获取地理位置

传入格式

```json
{
    "appId":appID,								
    "timestamp":timestamp,							//随机时间戳
    "nonceStr":nonceStr,							//随机生成字符串
    "signature":signature,							//签名
    "jsApiList":["openLocation","getLocation"]		  //返回需求的数据
}
```

返回数据格式

```json
{
    "latitude":latitude,							//精度
    "longitude":longitude,							//纬度
    "accuracy":accuracy								//精确度
}
```

### 业务逻辑：获取用户信息

传入格式

```json
{
	"do_type":1,									//请求类型
    "user_ID":user_ID								//用户ID
}
```

返回数据格式（失败）

```json
{
    "status":101,
    "msg":"user not found"
}
```

### 业务逻辑：创建签到

传入格式

```json
{
    "do_type":2,									//请求类型
    "record_date":record_date,						 //发起时间
    "user_ID":user_ID,								//用户ID	
    "end_time":end_time,							//签到结束时间
    "location_Latitude":location_Latitude,			  //发起人经度
    "location_Longitude":location_Longitude,		  //发起人纬度
    "location_Precision":location_Precision			  //发起人地理位置精确度
}
```

返回数据格式

```json
(成功)
{
    "status":1,
    "msg":"OK",
    "sign_num":sign_num								 //签到码
}
(失败)
{
    "status":101,
    "msg":"fail to create"
}
```

## participate_sign

### 业务逻辑：参与签到

传入格式

```json
{
    "sign_num":sign_num,							
    "user_ID":user_ID,								
    "location_Latitude":location_Latitude,			  
    "location_Longitude":location_Longitude,
    "location_Precision":location_Precision
}
```

返回数据格式

```json
{
    "status":status,									//状态
     "msg",msg											//返回信息
}
```

## sponsor_record

### 业务逻辑：查询用户发起签到记录

传入格式

```json
{
    "user_ID":user_ID
}
```

返回数据格式

```json
{
    "status":status,									 //状态
    "msg":msg,											//返回信息
    "length":length,									 //data长度
    "data":[
    	"sign_num":sign_num1,							   //签到码
    	"record_date":record_date1,						   //签到发起日期
    	"start_time":start_time1,						   //发起时间
    	"sponsor_ID":sponsor_ID1						   //签到发起人ID
    ],
	[
    	"sign_num":sign_num2,
    	"record_date":record_date2,
    	"start_time":start_time2,
    	"sponsor_ID":sponsor_ID2
    ]
}
```

## participate_record

### 业务逻辑：查询用户参与签到记录

数据格式与上类似

## information_sponsor

### 业务逻辑：获取发起签到记录的详细信息

传入格式

```json
{
    "do_type":1,											   //操作类型
    "sign_record_ID":sign_record_ID,							//所属签到记录ID
    "user_ID":user_ID
}
```

返回数据格式

```json
{
    "status":status,											 //状态
    "msg":msg;													//返回信息
    "sign_record_length":sign_record_length,
    "sign_record":[
    	{
            "sign_num":sign_num,
            "record_date":record_date,
            "start_time":start_time,
            "record_date":record_date,
            "end_time":end_time
		}
    ]
    "sign_space_length":sign_space_length,
	"sign_spaces":[
        {
            "iD":iD,											//签到空间ID
            "space_name":space_name								 //签到空间名称
        }
    ]
}
```

### 业务逻辑：选择签到空间，生成附带签到空间人员的参与表

传入格式

```json
{
    "do_type":2,										  	   //操作类型
    "sign_record_ID":sign_record_ID,							//所属签到记录ID
    "user_ID":user_ID
}
```

返回数据格式

```json
{
    "status":status,											 //状态
    "msg":msg,													//返回信息
    "data_length":data_length,
    "data":[
        {
			"user_ID":user_ID,									//所有签到空间的用户ID
    		"user_name":user_name								 //所有签到空间的用户名称
    	}
    ]
}
```

## information_participate

### 业务逻辑：获取参与签到记录的详细信息（与上类似）

传入格式

```json
{
    "do_type":2,										  	   //操作类型
    "sign_record_ID":sign_record_ID,							//所属签到记录ID
    "user_ID":user_ID
}
```

返回数据格式

```json
{
    "status":status,											 //状态
    "msg":msg,													//返回信息
    "sign_record":[
        {
            "sign_num":sign_num,
            "record_date":record_date,
            "start_time":start_time,
            "record_date":record_date,
            "end_time":end_time,
            "user_name":user_name
        }
    ],
    "participants_length":participants_length,
	"participants":[
        {
            "user_ID":user_ID,
            "user_name":user_name
        }
    ]
}
```

## sign_space

### 业务逻辑：上传Excel文件，并将数据导入到数据库中

传入格式

```json
{
    "file":file,
    "String":[
        "space_name":space_name,								//新建签到空间名称
        "user_ID":user_ID										//所属用户ID
    ]
}
```

返回数据格式

```json
{
    "status":status,											 //状态
    "msg":msg,													//返回信息
}
```


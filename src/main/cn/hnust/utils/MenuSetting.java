package main.cn.hnust.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;

import main.cn.hnust.wechat.NetWorkHelper;

public class MenuSetting {

	public static String appId = "wxf6817d77698a38c9";
	public static String appSecret = "a35a083ffabd3854ae705149e7ee0dc2";

	public static void main(String[] args) throws JSONException {
		add();
		//delete();
	}

	public static String getAccessToken() throws JSONException {
		NetWorkHelper netHelper = new NetWorkHelper();
		String Url = String.format("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s", appId, appSecret);
		String result = netHelper.getHttpsResponse(Url, "");
		System.out.println(result);
		JSONObject json = JSON.parseObject(result);
		return json.getString("access_token");
	}

	public static void delete() throws JSONException {
		String s = getAccessToken();
		NetWorkHelper netHelper = new NetWorkHelper();
		String Url = String.format("https://api.weixin.qq.com/cgi-bin/menu/delete?access_token=%s", s);
		String result = netHelper.getHttpsResponse(Url, "");
		System.out.println(result);
	}

	public static void add() throws JSONException{
    	String s = getAccessToken();
        NetWorkHelper netHelper = new NetWorkHelper();
        String json = " {" +
				"\"button\":[" +
				"{" +
				"\"type\":\"view\"," +
				"\"name\":\"主页\"," +
				"\"url\":\"https://open.weixin.qq.com/connect/oauth2/authorize?" +
				"appid=wxf6817d77698a38c9&redirect_uri=http%3a%2f%2fdaipeng.nat300.top" +
				"&response_type=code&scope=snsapi_base&state=123&connect_redirect=1#wechat_redirect\"" +
				"}]}";
        System.out.println(json);
         
        String Url = String.format("https://api.weixin.qq.com/cgi-bin/menu/create?access_token=%s", s);      
        String result = netHelper.getHttpsResponsePostBody(Url, "POST", json);
        System.out.println(result);
    }

}

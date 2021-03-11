package main.cn.hnust.wechat;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Formatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * 使用@WebServlet注解配置WxServlet,urlPatterns属性指明了WxServlet的访问路径
 */
@WebServlet(
		urlPatterns = {"/WxAuth"},
		initParams = {
				@WebInitParam(name = "appId", value = "wxf6817d77698a38c9"),
				@WebInitParam(name = "appSecret", value = "a35a083ffabd3854ae705149e7ee0dc2")
		})
public class AuthServlet extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO 接收、处理、响应由网页发送
		// 将请求、响应的编码均设置为UTF-8（防止中文乱码）
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");

		String appId = getInitParameter("appId");
		String appSecret = getInitParameter("appSecret");

		//获取前台传来的1个参数
		String code = request.getParameter("code");

		JSONObject json = null;
		System.out.println("通过code 获取access token");
		json =  getAccessToken(appId,  appSecret,  code);
		AuthAccessToken token = new AuthAccessToken();
		token.setAccessToken(json.getString("access_token"));
		token.setExpiresin(json.getInteger("expires_in"));
		token.setRefreshToken(json.getString("refresh_token"));
		token.setOpenid(json.getString("openid"));
		token.setScope(json.getString("scope"));
		TokenInfo.authAccessToken = token;

		response.getWriter().println(json.toString());

	}
	/**
	 * 获取access_token
	 *
	 * @return AccessToken
	 */
	private JSONObject getAccessToken(String appId, String appSecret, String code) {
		NetWorkHelper netHelper = new NetWorkHelper();

		String Url = String.format("https://api.weixin.qq.com/sns/oauth2/access_token?appid=%s&secret=%s&code=%s&grant_type=authorization_code", appId, appSecret, code);
		//此请求为https的get请求，返回的数据格式为{
		//"access_token":"ACCESS_TOKEN",
		//"expires_in":7200,
		//"refresh_token":"REFRESH_TOKEN",
		//"openid":"OPENID",
		//"scope":"SCOPE" }
		String result = netHelper.getHttpsResponse(Url, "");
		System.out.println("获取到的auth_access_token="+result);
		//使用FastJson将Json字符串解析成Json对象
		JSONObject json = JSON.parseObject(result);

		return json;
	}


}

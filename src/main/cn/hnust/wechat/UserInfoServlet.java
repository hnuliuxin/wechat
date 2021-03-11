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
 * 使用@WebServlet注解配置UserInfoServlet,urlPatterns属性指明了WxServlet的访问路径
 */
@WebServlet(urlPatterns = {"/WxUser"})
public class UserInfoServlet extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO 接收、处理、响应由网页发送
		// 将请求、响应的编码均设置为UTF-8（防止中文乱码）
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");

		//获取前台传来的2个参数
		String accesstoken = request.getParameter("access_token");
		String openid = request.getParameter("openid");

		JSONObject json = null;
		System.out.println("通过access token 和 openid 获取 user");
		json =  getUser(accesstoken,  openid);

		response.getWriter().println(json.toString());

	}
	/**
	 * 获取access_token
	 *
	 * @return AccessToken
	 */
	private JSONObject getUser(String accesstoken, String openid) {
		NetWorkHelper netHelper = new NetWorkHelper();

		String Url = String.format("https://api.weixin.qq.com/sns/userinfo?access_token=%s&openid=%s&lang=zh_CN", accesstoken, openid);
		//此请求为https的get请求，返回的数据格式为{
		//	  "openid":" OPENID",
		//	  "nickname": NICKNAME,
		//	  "sex":"1",
		//	  "province":"PROVINCE",
		//	  "city":"CITY",
		//	  "country":"COUNTRY",
		//	  "headimgurl": "http://thirdwx.qlogo.cn/mmopen/g3MonUZtNHkdmzicIlibx6iaFqAc56vxLSUfpb6n5WKSYVY0ChQKkiaJSgQ1dZuTOgvLLrhJbERQQ4eMsv84eavHiaiceqxibJxCfHe/46",
		//	  "privilege":[ "PRIVILEGE1" "PRIVILEGE2"     ],
		//	  "unionid": "o6_bmasdasdsad6_2sgVt7hMZOPfL"
		//	}
		String result = netHelper.getHttpsResponse(Url, "");
		System.out.println("获取到的user="+result);
		//使用FastJson将Json字符串解析成Json对象
		JSONObject json = JSON.parseObject(result);
		String nickName = String.valueOf(json.getString("nickname"));
		return json;
	}


}

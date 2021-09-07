package main.cn.hnust.wechat;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import main.cn.hnust.mapper.user_mapper;
import main.cn.hnust.model.user;
import main.cn.hnust.utils.Mybatis_utils;
import org.apache.ibatis.session.SqlSession;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(
        urlPatterns = "/loading_servlet",
        initParams = {
        @WebInitParam(name = "appId", value = "wxf6817d77698a38c9"),
        @WebInitParam(name = "appSecret", value = "a35a083ffabd3854ae705149e7ee0dc2")
}
)
public class loading_servlet extends HttpServlet{

    protected void doGet (HttpServletRequest request, HttpServletResponse response) throws  IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        String callback = request.getParameter("callback");
        System.out.println("code="+request.getParameter("code"));
        String code=request.getParameter("code");
        NetWorkHelper netWorkHelper=new NetWorkHelper();
        String url=String.format("https://api.weixin.qq.com/sns/oauth2/access_token?appid=%s&secret=%s&code=%s&grant_type=authorization_code",
               getInitParameter("appId"),getInitParameter("appSecret"),code);
        String result=netWorkHelper.getHttpsResponse(url,"");
        JSONObject res=JSON.parseObject(result);
        String open_id=res.getString("openid");

        SqlSession sqlSession= Mybatis_utils.getSqlSession();
        user_mapper pm=sqlSession.getMapper(user_mapper.class);
        user u=pm.get_user_List_by_openID(open_id);
        JSONObject json_ob=new JSONObject();
        System.out.println(u);
        if(u==null){
            json_ob.put("status",0);
            json_ob.put("msg","Not found in database");
            json_ob.put("open_ID",open_id);
        }
        else {
            json_ob.put("status",1);
            json_ob.put("msg","In there");
            json_ob.put("userID",u.getUser_ID());
        }
        response.getWriter().println(callback+"("+json_ob+")");
    }

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
        return JSON.parseObject(result);
    }

}

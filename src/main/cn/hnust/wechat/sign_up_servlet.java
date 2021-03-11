package main.cn.hnust.wechat;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import main.cn.hnust.mapper.user_mapper;
import main.cn.hnust.model.user;
import main.cn.hnust.utils.Mybatis_utils;
import org.apache.ibatis.session.SqlSession;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@WebServlet(
        urlPatterns = "/sign_up_servlet",
        initParams = {
                @WebInitParam(name = "appId", value = "wxf6817d77698a38c9"),
                @WebInitParam(name = "appSecret", value = "a35a083ffabd3854ae705149e7ee0dc2")
        }
)
public class sign_up_servlet extends HttpServlet {

    protected void doPost (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        //System.out.println("请求进入");

        String user_name=request.getParameter("user_name");
        String user_password=request.getParameter("user_password");
        String user_ID=request.getParameter("user_ID");
        System.out.println("user_name="+user_name);
        String callback = request.getParameter("callback");
        //System.out.println("请求进入");
        //String result = "success";
        System.out.println("user_name="+request.getParameter("user_name"));

        String open_id=request.getParameter("open_ID");
        //System.out.println("open_id="+open_id+"\n\n");

        JSONObject json_ob=new JSONObject();

        //获取session对象
        SqlSession sqlSession= Mybatis_utils.getSqlSession();
        user_mapper pm=sqlSession.getMapper(user_mapper.class);
        user u1=pm.get_user_List_by_openID(open_id);
        user u2=pm.get_user_List_by_user_ID(user_ID);
        if(u1==null&&u2==null){
            pm.insert_user(new user(open_id,user_name,user_ID,user_password));
            json_ob.put("user_ID",user_ID);
            json_ob.put("status",1);
            json_ob.put("msg","OK");
        }
        else if(u2!=null){
            json_ob.put("status",101);
            json_ob.put("msg","have user_ID");
        }
        else{
            json_ob.put("status",102);
            json_ob.put("msg","have open_ID");
        }


        //提交事务
        sqlSession.commit();

        //关闭sqlSession
        sqlSession.close();
        response.getWriter().println(callback+"("+json_ob+")");
    }
}

package main.cn.hnust.wechat;

import com.alibaba.fastjson.JSONObject;
import main.cn.hnust.mapper.sign_record_mapper;
import main.cn.hnust.mapper.user_mapper;
import main.cn.hnust.model.sign_record;
import main.cn.hnust.utils.Mybatis_utils;
import org.apache.ibatis.session.SqlSession;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(
        urlPatterns = "/sponsor_record_servlet",
        initParams = {
                @WebInitParam(name = "appId", value = "wxf6817d77698a38c9"),
                @WebInitParam(name = "appSecret", value = "a35a083ffabd3854ae705149e7ee0dc2")
        }
)
public class sponsor_record_servlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        String callback = request.getParameter("callback");
        JSONObject json_ob=new JSONObject();

        String user_ID=request.getParameter("user_ID");

        SqlSession sqlSession= Mybatis_utils.getSqlSession();
        sign_record_mapper srm=sqlSession.getMapper(sign_record_mapper.class);

        List<sign_record> sign_records=srm.get_sign_record_List_by_userID(user_ID);
        json_ob.put("status","OK");
        json_ob.put("msg","return success");
        json_ob.put("data",sign_records);
        json_ob.put("length",sign_records.size());

        sqlSession.close();

        response.getWriter().println(callback+"("+json_ob+")");
    }

}

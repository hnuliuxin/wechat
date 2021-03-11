package main.cn.hnust.wechat;

import com.alibaba.fastjson.JSONObject;
import main.cn.hnust.mapper.participants_mapper;
import main.cn.hnust.mapper.sign_record_mapper;
import main.cn.hnust.mapper.sign_space_mapper;
import main.cn.hnust.mapper.user_mapper;
import main.cn.hnust.model.participants;
import main.cn.hnust.model.sign_record;
import main.cn.hnust.model.user;
import main.cn.hnust.model.sign_space;
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
        urlPatterns = "/information_user_servlet",
        initParams = {
                @WebInitParam(name = "appId", value = "wxf6817d77698a38c9"),
                @WebInitParam(name = "appSecret", value = "a35a083ffabd3854ae705149e7ee0dc2")
        }
)
public class information_user_servlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        SqlSession sqlSession= Mybatis_utils.getSqlSession();
        sign_record_mapper srm=sqlSession.getMapper(sign_record_mapper.class);
        participants_mapper pm=sqlSession.getMapper(participants_mapper.class);
        sign_space_mapper ssm=sqlSession.getMapper(sign_space_mapper.class);
        user_mapper um=sqlSession.getMapper(user_mapper.class);

        String callback=request.getParameter("callback");
        JSONObject json_ob=new JSONObject();

        String user_ID=request.getParameter("user_ID");
        String sign_record_ID=request.getParameter("sponsor_ID");

        System.out.println(sign_record_ID);
        sign_record sr=srm.get_sign_record_List_by_ID(sign_record_ID);
        List<participants> participantsList=pm.get_participants_by_sign_record_ID(sign_record_ID);
        user u=um.get_user_List_by_user_ID(user_ID);

        if(sr==null){
            json_ob.put("status",101);
            json_ob.put("msg","sign record not found");
        }
        else{

            json_ob.put("sign_record",sr);
            json_ob.put("user_information",u);
            json_ob.put("data",participantsList);
            json_ob.put("sign_record_length",participantsList.size());
        }



        sqlSession.close();
        response.getWriter().println(callback+"("+json_ob+")");
    }
}

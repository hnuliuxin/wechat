package main.cn.hnust.wechat;

import com.alibaba.fastjson.JSONObject;
import main.cn.hnust.mapper.*;
import main.cn.hnust.model.*;
import main.cn.hnust.utils.Mybatis_utils;
import org.apache.ibatis.session.SqlSession;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@WebServlet(
        urlPatterns = "/information_sponsor_servlet",
        initParams = {
                @WebInitParam(name = "appId", value = "wxf6817d77698a38c9"),
                @WebInitParam(name = "appSecret", value = "a35a083ffabd3854ae705149e7ee0dc2")
        }
)
public class information_sponsor_servlet extends HttpServlet {
        protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
                request.setCharacterEncoding("UTF-8");
                response.setCharacterEncoding("UTF-8");

                SqlSession sqlSession= Mybatis_utils.getSqlSession();
                sign_space_mapper ssm=sqlSession.getMapper(sign_space_mapper.class);
                sign_record_mapper srm=sqlSession.getMapper(sign_record_mapper.class);
                participants_mapper pm=sqlSession.getMapper(participants_mapper.class);
                user_mapper um=sqlSession.getMapper(user_mapper.class);
                user_infomation_mapper uim=sqlSession.getMapper(user_infomation_mapper.class);

                String callback=request.getParameter("callback");
                JSONObject json_ob=new JSONObject();

                String do_type=request.getParameter("do_type");
                System.out.println("do_type="+do_type);

                List<participants> participantsList=null;
                if(do_type.equals("1")){
                        String user_ID=request.getParameter("user_ID");
                        String sign_record_ID=request.getParameter("sponsor_ID");

                        System.out.println("发起签到ID："+sign_record_ID);
                        sign_record sr=srm.get_sign_record_List_by_ID(sign_record_ID);
                        participantsList=pm.get_participants_by_sign_record_ID(sign_record_ID);
                        user u=um.get_user_List_by_user_ID(user_ID);
                        List<sign_space> sign_spaces=ssm.get_sign_space_by_user_ID(user_ID);

                        System.out.println(sign_spaces);
                        if(sr==null){
                                json_ob.put("status",101);
                                json_ob.put("msg","sign record not found");
                        }
                        else{
                                json_ob.put("sign_record",sr);
                                json_ob.put("user_information",u);
                                json_ob.put("data",participantsList);
                                json_ob.put("sign_record_length",participantsList.size());
                                json_ob.put("sign_space_length",sign_spaces.size());
                                json_ob.put("sign_spaces",sign_spaces);
                        }
                }
                else if(do_type.equals("2")){
                        String sign_spaces_ID=request.getParameter("sign_spaces_ID");
                        String sign_record_ID=request.getParameter("sponsor_ID");
                        participantsList=pm.get_participants_by_sign_record_ID(sign_record_ID);
                        System.out.println("participantsList="+participantsList);

                        String[] ss=sign_spaces_ID.split(",");


                        List<user_infomation> user_infomations=uim.get_user_infomation_List_by_signID_List(ss);
                        System.out.println("user_infomations="+user_infomations);
                        System.out.println("participantsList="+participantsList);
                        if(user_infomations!=null&&participantsList!=null){
                            for(participants pp:participantsList){
                                for(user_infomation ui:user_infomations){
                                    if(pp.getUser_ID().equals(ui.getUser_ID())){
                                            user_infomations.remove(ui);
                                            break;
                                    }
                                }
                            }
                            System.out.println("user_infomations||"+user_infomations);
                            json_ob.put("status",1);
                            json_ob.put("msg","OK");
                            json_ob.put("data",user_infomations);
                            json_ob.put("data_length",user_infomations.size());
                        }

                }
                sqlSession.close();
                response.getWriter().println(callback+"("+json_ob+")");
        }
}

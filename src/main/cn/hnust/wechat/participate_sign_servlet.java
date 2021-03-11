package main.cn.hnust.wechat;


import com.alibaba.fastjson.JSONObject;
import main.cn.hnust.mapper.participants_mapper;
import main.cn.hnust.mapper.sign_record_mapper;
import main.cn.hnust.mapper.user_mapper;
import main.cn.hnust.model.participants;
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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@WebServlet(
        urlPatterns = "/participate_sign_servlet",
        initParams = {
                @WebInitParam(name = "appId", value = "wxf6817d77698a38c9"),
                @WebInitParam(name = "appSecret", value = "a35a083ffabd3854ae705149e7ee0dc2")
        }
)
public class participate_sign_servlet extends HttpServlet {
        protected void doGet (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
                request.setCharacterEncoding("UTF-8");
                response.setCharacterEncoding("UTF-8");

                SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");//设置日期格式
                SqlSession sqlSession= Mybatis_utils.getSqlSession();
                sign_record_mapper srm=sqlSession.getMapper(sign_record_mapper.class);
                participants_mapper pm=sqlSession.getMapper(participants_mapper.class);
                user_mapper um=sqlSession.getMapper(user_mapper.class);
                String callback=request.getParameter("callback");
                JSONObject json_ob=new JSONObject();

                String sign_num=request.getParameter("sign_num");
                String user_ID=request.getParameter("user_ID");//参与人
                String location_Latitude=request.getParameter("location_Latitude");
                String location_Longitude=request.getParameter("location_Longitude");
                String location_Precision=request.getParameter("location_Precision");

                //通过签到码和当前时间找到 当前正在签到且签到码符合的签到
                sign_record sr=srm.get_sign_record_List_by_sign_num(Integer.parseInt(sign_num),df.format(new Date()));
                if(sr==null){
                        json_ob.put("status",101);
                        json_ob.put("msg","sign_record not found");
                }
                else{
                        participants p=pm.get_participants_by_userid_signid(user_ID,sr.getID());
                        if(p!=null){
                                json_ob.put("status",102);
                                json_ob.put("msg","already sign in");
                        }
                        else{
//                                if(check(location_Latitude,location_Longitude,location_Precision)){
                                        participants to_insert=new participants(UUID.randomUUID().toString().replaceAll("-","")
                                                ,user_ID,um.get_user_List_by_user_ID(user_ID).getUser_name(),true,sr.getID());
                                        json_ob.put("stats",1);
                                        json_ob.put("msg","OK");
                                        pm.insert_participants(to_insert);
//                                }
//                                else{
//                                        json_ob.put("status",103);
//                                        json_ob.put("msg","location fail");
//                                }

                        }
                }



                sqlSession.commit();
                sqlSession.close();

                response.getWriter().println(callback+"("+json_ob+")");
        }

        private boolean check(double location_Latitude,double location_Longitude,double location_Precision){
                return true;
        }
}


package main.cn.hnust.wechat;

import com.alibaba.fastjson.JSONObject;
import main.cn.hnust.mapper.sign_record_mapper;
import main.cn.hnust.mapper.user_mapper;
import main.cn.hnust.model.sign_record;
import main.cn.hnust.model.user;
import main.cn.hnust.utils.Mybatis_utils;
import org.apache.ibatis.session.SqlSession;
import org.jcp.xml.dsig.internal.dom.DOMUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

@WebServlet(
        urlPatterns = "/create_sign_servlet",
        initParams = {
                @WebInitParam(name = "appId", value = "wxf6817d77698a38c9"),
                @WebInitParam(name = "appSecret", value = "a35a083ffabd3854ae705149e7ee0dc2")
        }
)
public class create_sign_servlet extends HttpServlet {
        protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
                request.setCharacterEncoding("UTF-8");
                response.setCharacterEncoding("UTF-8");
                String callback = request.getParameter("callback");
                String do_type=request.getParameter("do_type");
                SqlSession sqlSession= Mybatis_utils.getSqlSession();
                user_mapper pm=sqlSession.getMapper(user_mapper.class);
                JSONObject json_ob=new JSONObject();
                if(do_type.equals("1")){
                        String user_ID=request.getParameter("user_ID");
                        user u=pm.get_user_List_by_user_ID(user_ID);
                        if(u==null){
                                json_ob.put("status",101);
                                json_ob.put("msg","user not found");
                        }
                        else{
                                String user_name=u.getUser_name();
                                json_ob.put("user_name",user_name);
                                json_ob.put("status",1);
                                json_ob.put("msg","success");
                        }
                        sqlSession.close();
                }
                else if(do_type.equals("2")){
                        json_ob.put("msg","test");
                        int sign_num=0;
                        String ID= UUID.randomUUID().toString().replaceAll("-","");
                        SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");//设置日期格式
                        Random random=new Random();
                        sign_record_mapper srm=sqlSession.getMapper(sign_record_mapper.class);
                        //循环找到没有正在使用的 sign_num
                        String start_time;
                        while(true){
                                sign_num=random.nextInt(9000)+1000;

                                start_time=df.format(new Date());
                                System.out.println("now sign_num="+sign_num);
                                sign_record record=srm.get_sign_record_List_by_sign_num(sign_num,start_time);
                                System.out.println("record="+record);
                                if(record==null){
                                        break;
                                }
                        }

                        String record_date=request.getParameter("record_date");
                        String user_ID=request.getParameter("user_ID");
                        String end_time=request.getParameter("end_time");
                        System.out.println("\n"+record_date+"|record_date");
                        System.out.println(start_time+"|start_time");
                        System.out.println(end_time+"|end_time\n");
                        String location_Latitude=request.getParameter("location_Latitude");
                        String location_Longitude=request.getParameter("location_Longitude");
                        String location_Precision=request.getParameter("location_Precision");
                        sign_record sr=new sign_record(ID, java.sql.Date.valueOf(record_date),user_ID,sign_num
                                , Time.valueOf(start_time),Time.valueOf(end_time),Double.parseDouble(location_Latitude)
                                , Double.parseDouble(location_Longitude), Double.parseDouble(location_Precision));
                        int sta=srm.insert_sign_record(sr);
                        if(sta==1){
                                json_ob.put("status",1);
                                json_ob.put("msg","OK");
                                json_ob.put("sign_num",sign_num);
                        }
                        else{
                                json_ob.put("status",101);
                                json_ob.put("msg","fail to create");
                        }
                        sqlSession.commit();
                        sqlSession.close();
                }
                response.getWriter().println(callback+"("+json_ob+")");
        }
}

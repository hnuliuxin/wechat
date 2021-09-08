package main.cn.hnust.wechat;


import com.alibaba.fastjson.JSONObject;
import main.cn.hnust.mapper.participants_mapper;
import main.cn.hnust.mapper.sign_record_mapper;
import main.cn.hnust.mapper.user_mapper;
import main.cn.hnust.model.participants;
import main.cn.hnust.model.sign_record;
import main.cn.hnust.utils.Mybatis_utils;
import org.apache.ibatis.session.SqlSession;

import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

@WebServlet(
        urlPatterns = "/participate_sign_servlet",
        initParams = {
                @WebInitParam(name = "appId", value = "wxf6817d77698a38c9"),
                @WebInitParam(name = "appSecret", value = "a35a083ffabd3854ae705149e7ee0dc2")
        }
)
public class participate_sign_servlet extends HttpServlet {
    protected void doGet (HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        SqlSession sqlSession= Mybatis_utils.getSqlSession();
        sign_record_mapper srm=sqlSession.getMapper(sign_record_mapper.class);
        participants_mapper pm=sqlSession.getMapper(participants_mapper.class);
        user_mapper um=sqlSession.getMapper(user_mapper.class);
        String callback=request.getParameter("callback");
        JSONObject json_ob=new JSONObject();

        SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");//设置日期格式
        String do_type=request.getParameter("do_type");
        String user_ID=request.getParameter("user_ID");//参与人


        if(Objects.equals(do_type, "1")) {
            String sign_num=request.getParameter("sign_num");
            String Slocation_Latitude=request.getParameter("location_Latitude");
            String Slocation_Longitude=request.getParameter("location_Longitude");
            double location_Latitude=Double.parseDouble(Slocation_Latitude);
            double location_Longitude=Double.parseDouble(Slocation_Longitude);

            //通过签到码和当前时间找到 当前正在签到且签到码符合的签到
            if(Objects.equals(sign_num, ""))
            {
                json_ob.put("status",104);
                json_ob.put("msg","sign_num is null");
            }
            else {
                sign_record sr = srm.get_sign_record_List_by_sign_num(Integer.parseInt(sign_num), df.format(new Date()));
                if (sr == null) {
                    json_ob.put("status", 101);
                    json_ob.put("msg", "sign_record not found");
                } else {
                    double location_Latitude_sponsor = sr.getLocation_Latitude();
                    double Location_Longitude_sponsor = sr.getLocation_Longitude();
                    participants p = pm.get_participants_by_userid_signid(user_ID, sr.getID());
                    if (p != null) {
                        json_ob.put("status", 102);
                        json_ob.put("msg", "already sign in");
                    } else {
                        if (check(location_Latitude, location_Longitude, location_Latitude_sponsor, Location_Longitude_sponsor)) {
                            participants to_insert = new participants(UUID.randomUUID().toString().replaceAll("-", "")
                                    , user_ID, um.get_user_List_by_user_ID(user_ID).getUser_name(), true, sr.getID());
                            json_ob.put("status", 1);
                            json_ob.put("msg", "OK");
                            pm.insert_participants(to_insert);
                        } else {
                            json_ob.put("status", 103);
                            json_ob.put("msg", "location fail");
                        }
                    }
                }
            }
        }

        sqlSession.commit();
        sqlSession.close();
        response.getWriter().println(callback+"("+json_ob+")");
    }

    private boolean check(double location_Latitude1,double location_Longitude1,double location_Latitude2,double location_Longitude2){
        // 计算两个经纬度之间的距离
        double La1 = location_Latitude1 * Math.PI / 180.0;
        double La2 = location_Latitude2 * Math.PI / 180.0;
        double La3 = La1 - La2;
        double Lb3 = location_Longitude1 * Math.PI / 180.0 - location_Longitude2 * Math.PI / 180.0;
        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(La3 / 2), 2) + Math.cos(La1) * Math.cos(La2) * Math.pow(Math.sin(Lb3 / 2), 2)));
        s = s * 6378.137; //地球半径
        s = Math.round(s * 10000) *1.0 / 10000;
        System.out.println(s);
        return !(s > 1.0);
    }
}


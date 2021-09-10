package main.cn.hnust.wechat;

import com.alibaba.fastjson.JSONObject;
import main.cn.hnust.mapper.*;
import main.cn.hnust.model.*;
import main.cn.hnust.utils.Mybatis_utils;
import org.apache.ibatis.session.SqlSession;

import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@WebServlet(
        urlPatterns = "/information_sponsor_servlet",
        initParams = {
                @WebInitParam(name = "appId", value = "wxf6817d77698a38c9"),
                @WebInitParam(name = "appSecret", value = "a35a083ffabd3854ae705149e7ee0dc2")
        }
)
public class information_sponsor_servlet extends HttpServlet {
        protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
                request.setCharacterEncoding("UTF-8");
                response.setCharacterEncoding("UTF-8");

                SqlSession sqlSession= Mybatis_utils.getSqlSession();
                sign_space_mapper ssm=sqlSession.getMapper(sign_space_mapper.class);
                sign_record_mapper srm=sqlSession.getMapper(sign_record_mapper.class);
                participants_mapper pm=sqlSession.getMapper(participants_mapper.class);
                user_mapper um=sqlSession.getMapper(user_mapper.class);
                user_information_mapper uim=sqlSession.getMapper(user_information_mapper.class);

                String callback=request.getParameter("callback");
                JSONObject json_ob=new JSONObject();

                String do_type=request.getParameter("do_type");
                List<participants> participantsList;
                if(do_type.equals("1")){
                        String user_ID=request.getParameter("user_ID");
                        String sign_record_ID=request.getParameter("sign_record_ID");

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
                                json_ob.put("status",100);
                                json_ob.put("msg","success to display");
                                json_ob.put("sign_record",sr);
                                json_ob.put("user",u);
                                json_ob.put("data",participantsList);
                                json_ob.put("sign_record_length",participantsList.size());
                                json_ob.put("sign_space_length",sign_spaces.size());
                                json_ob.put("sign_spaces",sign_spaces);
                        }
                }
                else if(do_type.equals("2")){
                        String sign_spaces_ID=request.getParameter("sign_spaces_ID");
                        String sign_record_ID=request.getParameter("sign_record_ID");
                        participantsList=pm.get_participants_by_sign_record_ID(sign_record_ID);
                        //System.out.println("participantsList="+participantsList);

                        String[] ss=sign_spaces_ID.split(",");


                        List<user_information> user_informations =uim.get_user_information_List_by_signID_List(ss);
                        if(user_informations !=null&&participantsList!=null){
                            for(participants pp:participantsList){
                                for(user_information ui: user_informations){
                                    if(pp.getUser_ID().equals(ui.getUser_ID())){
                                            user_informations.remove(ui);
                                            break;
                                    }
                                }
                            }
                            Collections.sort(user_informations);
                            user_informations=remove_duplication(user_informations);
                            json_ob.put("status",200);
                            json_ob.put("msg","OK");
                            json_ob.put("data", user_informations);
                            json_ob.put("data_length", user_informations.size());
                        }
                        else{
                            json_ob.put("status",201);
                            json_ob.put("msg","user_informations or participantsList are empty");
                        }

                }
                sqlSession.close();
                response.getWriter().println(callback+"("+json_ob+")");
        }
    public static List remove_duplication(List<user_information> list) {
        TreeSet set = new TreeSet(list);
        //把List集合所有元素清空
        list.clear();
        //把HashSet对象添加至List集合
        list.addAll(set);
        return list;
    }
}

import main.cn.hnust.mapper.*;

import main.cn.hnust.model.participants;
import main.cn.hnust.model.sign_record;
import main.cn.hnust.model.sign_space;
import main.cn.hnust.model.user;
import main.cn.hnust.model.user_infomation;

import main.cn.hnust.utils.Mybatis_utils;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.io.File;
import java.sql.Date;
import java.sql.Time;
import java.util.*;

import static main.cn.hnust.utils.ExcelDataUtil.use_Excel;

public class mybatis_test {
    @Test
    public void add_participants(){

        //获取session对象
        SqlSession sqlSession= Mybatis_utils.getSqlSession();
        sign_record_mapper pm=sqlSession.getMapper(sign_record_mapper.class);

//        int sta=pm.insert_user(new user("asdqwe231","name","user_ID","123"));
//        System.out.println(sta);

//        List<user> list=pm.get_user_List_by_class(new user("","name","user_ID","123"));
//        for(user p:list){
//            System.out.println(p);
//        }
        sign_record sr=pm.get_sign_record_List_by_ID("62f247ec92bb4e31a9dd2d15943dbdaa");
        System.out.println(sr);
        //System.out.println(pm.get_sign_record_List_by_ID("37e09d5d371442c1a908fc477c586d97"));
        //提交事务
        sqlSession.commit();

        //关闭sqlSession
        sqlSession.close();
    }

    @Test
    public  void javatest(){
        File file=new File("D:\\工作\\test.xls");
        use_Excel(file);
    }

    @Test
    public void sign_record_test(){

        //获取session对象
        SqlSession sqlSession= Mybatis_utils.getSqlSession();
        sign_record_mapper pm=sqlSession.getMapper(sign_record_mapper.class);

//        sign_record sr=new sign_record("ID", java.sql.Date.valueOf("2021-03-09"),"user_ID",3447
//                , Time.valueOf("22:10:27"),Time.valueOf("11:43:27"),1.1,12.2,3.2);
//
//        int sta=pm.insert_sign_record(sr);
//        System.out.println(sta);
        sign_record sr=pm.get_sign_record_List_by_sign_num(3447,"22:16:45");
        System.out.println(sr);
        //提交事务
        sqlSession.commit();

        //关闭sqlSession
        sqlSession.close();
    }

    @Test
    public void insert(){
        //获取session对象
        SqlSession sqlSession= Mybatis_utils.getSqlSession();
        user_infomation_mapper pm=sqlSession.getMapper(user_infomation_mapper.class);
        sign_space_mapper ssm=sqlSession.getMapper(sign_space_mapper.class);
        //user_infomation ui=new user_infomation("54qwr","user_ID2","user_name2","department2","dsa" );
        //pm.insert_user_infomation(ui);
        sign_space ss=new sign_space("dsa","space_name2","1805050221" );
        ssm.insert_sign_space(ss);
        //提交事务
        sqlSession.commit();

        //关闭sqlSession
        sqlSession.close();
    }

}

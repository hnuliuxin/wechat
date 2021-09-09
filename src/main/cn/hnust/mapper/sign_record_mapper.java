package main.cn.hnust.mapper;

import main.cn.hnust.model.sign_record;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface sign_record_mapper {
    List<sign_record> get_sign_record_List();
    sign_record get_sign_record_List_by_ID(String ID);
    sign_record get_sign_record_List_by_ID_and_time(@Param("ID") String ID,@Param("now_time") String now_time);
    List<sign_record> get_sign_record_List_by_userID(String user_ID);
    sign_record get_sign_record_List_by_sign_num(@Param("sign_num") int sign_num, @Param("now_time") String now_time);
    int insert_sign_record(sign_record sr);
}

package main.cn.hnust.mapper;

import main.cn.hnust.model.participants;
import org.apache.ibatis.annotations.Param;
import org.junit.runners.Parameterized;

import java.util.List;

public interface participants_mapper {
    List<participants> get_participants_List();
    List<participants> get_participants_List_by_user_ID(String user_ID);
    List<participants> get_participants_by_sign_record_ID(String sign_record_ID);
    participants get_participants_by_userid_signid(@Param("user_ID") String user_ID,@Param("sign_record_ID") String sign_record_ID);
    int insert_participants(participants p);
}

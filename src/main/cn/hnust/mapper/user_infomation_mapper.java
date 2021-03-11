package main.cn.hnust.mapper;

import main.cn.hnust.model.user_infomation;

import java.util.List;

public interface user_infomation_mapper {
    List<user_infomation> get_user_infomation_List();
    List<user_infomation> get_user_infomation_List_by_signID_List(String[] sign_spaces_ID);
    int insert_user_infomation(user_infomation ui);
}

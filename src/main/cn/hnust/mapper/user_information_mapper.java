package main.cn.hnust.mapper;

import main.cn.hnust.model.user_information;

import java.util.List;

public interface user_information_mapper {
    List<user_information> get_user_information_List();
    user_information get_user_information_by_ID(String user_ID);
    List<user_information> get_user_information_List_by_signID_List(String[] sign_spaces_ID);
    int insert_user_information(user_information ui);
}

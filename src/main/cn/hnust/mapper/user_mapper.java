package main.cn.hnust.mapper;

import main.cn.hnust.model.user;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface user_mapper {
    List<user> get_user_List();
    user get_user_List_by_class(user u);
    user get_user_List_by_openID(String openID);
    user get_user_List_by_user_ID(String user_ID);
    int insert_user(user u);

}

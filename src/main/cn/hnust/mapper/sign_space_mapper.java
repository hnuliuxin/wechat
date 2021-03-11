package main.cn.hnust.mapper;

import main.cn.hnust.model.sign_space;

import java.util.List;

public interface sign_space_mapper {
    List<sign_space> get_sign_space_List();
    List<sign_space> get_sign_space_by_user_ID(String user_ID);
    int insert_sign_space(sign_space ss);
}

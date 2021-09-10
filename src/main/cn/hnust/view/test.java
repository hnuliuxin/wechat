package cn.hnust.view;

import main.cn.hnust.model.user_information;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class test {
    public static List removeDuplicationByTreeSet(List<user_information> list) {
        TreeSet set = new TreeSet(list);
        //把List集合所有元素清空
        list.clear();
        //把HashSet对象添加至List集合
        list.addAll(set);
        return list;
    }


    public static void main(String[] args) {
        ArrayList<user_information> list = new ArrayList<>();
        user_information information1 = new user_information();
        information1.setUser_ID("1");


        user_information information2 = new user_information();
        information2.setUser_ID("2");


        user_information information3 = new user_information();
        information3.setUser_ID("2");


        user_information information4 = new user_information();
        information4.setUser_ID("3");
        list.add(information1);
        list.add(information2);
        list.add(information3);
        list.add(information4);
        list = (ArrayList<user_information>) removeDuplicationByTreeSet(list);
        for (user_information information : list) {
            System.out.println(information);
        }

    }
}

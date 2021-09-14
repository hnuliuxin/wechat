package main.cn.hnust.wechat;

import com.alibaba.fastjson.JSONObject;
import main.cn.hnust.mapper.sign_space_mapper;
import main.cn.hnust.mapper.user_information_mapper;
import main.cn.hnust.model.sign_space;
import main.cn.hnust.model.user_information;
import main.cn.hnust.utils.Mybatis_utils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.ibatis.session.SqlSession;

import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static main.cn.hnust.utils.ExcelDataUtil.use_Excel;

@WebServlet(
        urlPatterns = "/sign_space_servlet",
        initParams = {
                @WebInitParam(name = "appId", value = "wxf6817d77698a38c9"),
                @WebInitParam(name = "appSecret", value = "a35a083ffabd3854ae705149e7ee0dc2")
        }
)
public class sign_space_servlet extends HttpServlet {
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        System.out.println("开始上传");
        SqlSession sqlSession= Mybatis_utils.getSqlSession();
        sign_space_mapper ssm=sqlSession.getMapper(sign_space_mapper.class);
        user_information_mapper uim=sqlSession.getMapper(user_information_mapper.class);


        String ID= UUID.randomUUID().toString().replaceAll("-","");

        JSONObject json_ob=new JSONObject();
        DiskFileItemFactory fu = new DiskFileItemFactory();
        fu.setSizeThreshold(30 * 1024 * 1024);
        //fu.setSizeThreshold(4096);
        ServletFileUpload upload = new ServletFileUpload(fu);
        upload.setHeaderEncoding("UTF-8");
        List<FileItem> fileItems;
        try {
            fileItems = upload.parseRequest(request);
            String space_name=(fileItems.get(0)).getString("UTF-8");   //获取directory参数
            String user_ID = (fileItems.get(1)).getString("UTF-8");   //获取directory参数
            for (FileItem item : fileItems) {

                System.out.println(item);
                item.getString("UTF-8");
                //忽略其他不是文件域的所有表单信息
                if (!item.isFormField()) {
                    String name1 = item.getName();//获取上传的文件名
                    long size = item.getSize();//获取上传的文件大小(字节为单位)
                    if ((name1 == null || name1.equals("")) && size == 0) {
                        continue;//跳到while检查条件
                    }
                    assert name1 != null;
                    int end = name1.length();
                    int begin = name1.lastIndexOf("\\");
                    String newname = name1.substring(begin + 1, end);
                    if (newname.length() == 0) {
                        System.out.println("上传文件导入异常，请重新上传...");
                    } else {
                        try {
                            //保存文件
                            File savedFile = new File("D:\\学习\\wechat\\文件", name1);//用原文件名，作为上传文件的文件名。“/code”为目标路径
                            item.write(savedFile);
                            item.delete();
                            System.out.println("上传结束");
                            List<Map<String, String>> files = use_Excel(savedFile);

                            if(!ssm.get_sign_space_by_user_ID_and_space_name(user_ID,space_name).isEmpty()){
                                json_ob.put("status",102);
                                json_ob.put("msg","already have this sign space");
                            }
                            else if (files.get(0).get("学号/工号") != null && files.get(0).get("姓名") != null && files.get(0).get("单位") != null) {
                                for (Map<String, String> file_i : files) {
                                    if (uim.get_user_information_by_ID(ID) == null) {
                                        if(file_i.get("学号/工号").indexOf('.')!=-1)
                                            uim.insert_user_information(new user_information(UUID.randomUUID().toString().replaceAll("-", ""),
                                                file_i.get("学号/工号").substring(0, file_i.get("学号/工号").indexOf('.')), file_i.get("姓名"), file_i.get("单位"), ID));
                                        else
                                            uim.insert_user_information(new user_information(UUID.randomUUID().toString().replaceAll("-", ""),
                                                    file_i.get("学号/工号"), file_i.get("姓名"), file_i.get("单位"), ID));
                                    }
                                }
                                sign_space to_insert = new sign_space(ID, space_name, user_ID);
                                ssm.insert_sign_space(to_insert);
                                savedFile.delete();
                                System.out.println("插入完成");
                                json_ob.put("status", 1);
                                json_ob.put("msg","success");
                            } else {
                                json_ob.put("status", 101);
                                json_ob.put("msg", "Presentation Error");
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        } catch (FileUploadException e) {
            e.printStackTrace();
        }
        sqlSession.commit();
        sqlSession.close();
        response.getWriter().println(json_ob);
    }



}
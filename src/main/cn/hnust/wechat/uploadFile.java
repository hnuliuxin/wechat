package main.cn.hnust.wechat;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.List;


@WebServlet(
        urlPatterns = "/uploadFile_servlet",
        initParams = {
                @WebInitParam(name = "appId", value = "wxf6817d77698a38c9"),
                @WebInitParam(name = "appSecret", value = "a35a083ffabd3854ae705149e7ee0dc2")
        }
)
public class uploadFile extends HttpServlet {
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        System.out.println("开始上传");
        DiskFileItemFactory fu = new DiskFileItemFactory();
        fu.setSizeThreshold(2 * 1024 * 1024);
        fu.setSizeThreshold(4096);
        ServletFileUpload upload = new ServletFileUpload(fu);
        upload.setHeaderEncoding("UTF-8");
        List<FileItem> fileItems;
        try {
            fileItems = upload.parseRequest(request);
            for (FileItem item : fileItems) {
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
                            File savedFile = new File("C:\\Users\\12456\\Desktop", name1);//用原文件名，作为上传文件的文件名。“/code”为目标路径
                            item.write(savedFile);
                            item.delete();
                            System.out.println("上传结束");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        } catch (FileUploadException e) {
            e.printStackTrace();
        }
    }


}

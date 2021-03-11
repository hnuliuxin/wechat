package main.cn.hnust.wechat;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import static main.cn.hnust.utils.ExcelDataUtil.use_Excel;

@WebServlet(
        urlPatterns = "/sign_space_servlet",
        initParams = {
                @WebInitParam(name = "appId", value = "wxf6817d77698a38c9"),
                @WebInitParam(name = "appSecret", value = "a35a083ffabd3854ae705149e7ee0dc2")
        }
)
public class sign_space_servlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String callback=request.getParameter("callback");
        JSONObject json_ob=new JSONObject();

        String excelPath=request.getParameter("file_name");
        File excel = new File(excelPath);
        List<Map<String, String>> data=use_Excel(excel);
        json_ob.put("status",1);
        json_ob.put("data",data);
        response.getWriter().println(callback+"("+json_ob+")");
    }
}
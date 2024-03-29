package main.cn.hnust.wechat;

import main.cn.hnust.utils.MessageHandlerUtil;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;

/**
 * 使用@WebServlet注解配置WxServlet,urlPatterns属性指明了WxServlet的访问路径
 */
@WebServlet(urlPatterns="/WxServlet")
public class WxServlet extends HttpServlet {

    /**
     * 处理微信服务器发来的消息
     */

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // TODO 接收、处理、响应由微信服务器转发的用户发送给公众帐号的消息
        // 将请求、响应的编码均设置为UTF-8（防止中文乱码）
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        System.out.println("请求进入");
        String result = "";
        try {
            Map<String,String> map = MessageHandlerUtil.parseXml(request);
            System.out.println("开始构造消息");
            result = MessageHandlerUtil.buildXml(map);
            //System.out.println(result);
            if(result.equals("")){
                result = "未正确响应";
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("发生异常："+ e.getMessage());
        }
        response.getWriter().println(result);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println("开始校验签名");
        /*
         * 接收微信服务器发送请求时传递过来的4个参数
         */
        System.out.println(request);
        String signature = request.getParameter("signature");//微信加密签名signature结合了开发者填写的token参数和请求中的timestamp参数、nonce参数。
        String timestamp = request.getParameter("timestamp");//时间戳
        String nonce = request.getParameter("nonce");//随机数
        String echostr = request.getParameter("echostr");//随机字符串
        //排序
        /*
         * Token可由开发者可以任意填写，用作生成签名（该Token会和接口URL中包含的Token进行比对，从而验证安全性）
         * 比如这里我将Token设置为hnust
         */
        String TOKEN = "123456";
        String sortString = sort(TOKEN, timestamp, nonce);
        //加密
        String mySignature = sha1(sortString);
        //校验签名
        if (mySignature != null && !Objects.equals(mySignature, "") && mySignature.equals(signature)) {
            System.out.println("签名校验通过。");
            //如果检验成功输出echostr，微信服务器接收到此输出，才会确认检验完成。
            //response.getWriter().println(echostr);
            response.getWriter().write(echostr);
        } else {
            System.out.println("签名校验失败.");
        }

    }

    /**
     * 排序方法
     *
     * @param token 标志
     * @param timestamp 时间戳
     * @param nonce .
     * @return String
     */
    public String sort(String token, String timestamp, String nonce) {
        String[] strArray = {token, timestamp, nonce};
        Arrays.sort(strArray);
        StringBuilder sb = new StringBuilder();
        for (String str : strArray) {
            sb.append(str);
        }

        return sb.toString();
    }

    /**
     * 将字符串进行sha1加密
     *
     * @param str 需要加密的字符串
     * @return 加密后的内容
     */
    public String sha1(String str) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-1");
            digest.update(str.getBytes());
            byte[] messageDigest = digest.digest();
            // Create Hex String
            StringBuilder hexString = new StringBuilder();
            // 字节数组转换为 十六进制 数
            for (byte b : messageDigest) {
                String shaHex = Integer.toHexString(b & 0xFF);
                if (shaHex.length() < 2) {
                    hexString.append(0);
                }
                hexString.append(shaHex);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static void main(String[] args) {
        // TODO Auto-generated method stub

    }

}

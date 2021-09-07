package main.cn.hnust.wechat;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.cert.X509Certificate;



/**
 * 访问网络用到的工具类
 */
public class NetWorkHelper {

    /**
     * 发起Https请求
     * @param reqUrl 请求的URL地址
     * @param requestMethod 返回方法
     * @return 响应后的字符串
     */
    public String getHttpsResponse(String reqUrl, String requestMethod) {
        URL url;
        InputStream is;
        StringBuilder resultData = new StringBuilder();
        try {
            url = new URL(reqUrl);
            HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
            TrustManager[] tm = {xtm};

            SSLContext ctx = SSLContext.getInstance("TLS");
            ctx.init(null, tm, null);

            con.setSSLSocketFactory(ctx.getSocketFactory());
            con.setHostnameVerifier((arg0, arg1) -> true);


            con.setDoInput(true); //允许输入流，即允许下载

            //在android中必须将此项设置为false
            con.setDoOutput(false); //允许输出流，即允许上传
            con.setUseCaches(false); //不使用缓冲
            if (null != requestMethod && !requestMethod.equals("")) {
                con.setRequestMethod(requestMethod); //使用指定的方式
            } else {
                con.setRequestMethod("GET"); //使用get请求
            }
            is = con.getInputStream();   //获取输入流，此时才真正建立链接
            InputStreamReader isr = new InputStreamReader(is, StandardCharsets.UTF_8);
            BufferedReader bufferReader = new BufferedReader(isr);
            String inputLine;
            while ((inputLine = bufferReader.readLine()) != null) {
                resultData.append(inputLine).append("\n");
            }
            //System.out.println(resultData);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultData.toString();
    }

    public String getHttpsResponsePostBody(String hsUrl,String requestMethod,String body) {
        URL url;
        InputStream is;
        StringBuilder resultData = new StringBuilder();
        try {
            url = new URL(hsUrl);
            HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
            TrustManager[] tm = {xtm};

            SSLContext ctx = SSLContext.getInstance("TLS");
            ctx.init(null, tm, null);

            con.setSSLSocketFactory(ctx.getSocketFactory());
            con.setHostnameVerifier((arg0, arg1) -> true);

            con.setDoInput(true);
            con.setDoOutput(true);
            con.setUseCaches(false);
            if(null!=requestMethod && !requestMethod.equals("")) {
                con.setRequestMethod(requestMethod);
            }
            else{
                con.setRequestMethod("GET");
            }

            con.setRequestProperty("Content-Type","application/json");

            DataOutputStream out = new DataOutputStream(con.getOutputStream());
            byte[] outputInBytes = body.getBytes(StandardCharsets.UTF_8);
            out.write(outputInBytes);
            out.flush();
            out.close();

            is = con.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader bufferReader = new BufferedReader(isr);
            String inputLine;
            while ((inputLine = bufferReader.readLine()) != null) {
                resultData.append(inputLine).append("\n");
            }
            //System.out.println(resultData);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultData.toString();
    }


    X509TrustManager xtm = new X509TrustManager() {
        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return null;
        }

        @Override
        public void checkServerTrusted(X509Certificate[] arg0, String arg1) {

        }

        @Override
        public void checkClientTrusted(X509Certificate[] arg0, String arg1) {

        }
    };
}
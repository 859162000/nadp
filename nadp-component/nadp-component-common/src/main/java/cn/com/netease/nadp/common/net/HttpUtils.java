package cn.com.netease.nadp.common.net;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpOptions;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import  org.apache.http.client.methods.HttpGet;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * HTTP工具类
 * Created by bjbianlanzhou on 2016/8/2.
 * Description
 */
public class HttpUtils {
    public static String doGet(String url) throws Exception {
        String message = null;
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);
        CloseableHttpResponse response = httpclient.execute(httpGet);
        try {
            System.out.println(response.getStatusLine());
            HttpEntity entity = response.getEntity();
            message = EntityUtils.toString(entity, "utf-8");
        } finally {
            response.close();
        }
        return message;
  }

    public static String doPost(String url,List<NameValuePair> nvps) throws Exception {
        String message = null;
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        httpPost.setEntity(new UrlEncodedFormEntity(nvps));
        CloseableHttpResponse response = httpclient.execute(httpPost);
        try {
            if(response.getStatusLine().getStatusCode()!=200){
                throw new RuntimeException("connection Exception ! status code :"+response.getStatusLine().getStatusCode());
            }
            HttpEntity entity = response.getEntity();
            message = EntityUtils.toString(entity, "utf-8");
        } finally {
            response.close();
        }
        return message;
    }

    public static String doPost(String url,String jsonStr) throws Exception {
        String message = null;
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        httpPost.setEntity(new StringEntity(jsonStr));
        CloseableHttpResponse response = httpclient.execute(httpPost);
        try {
            if(response.getStatusLine().getStatusCode()!=200){
                throw new RuntimeException("connection Exception ! status code :"+response.getStatusLine().getStatusCode());
            }
            HttpEntity entity = response.getEntity();
            message = EntityUtils.toString(entity, "utf-8");
        } finally {
            response.close();
        }
        return message;
    }

    public static void main(String[] args) {
        try {
            //<nadp:register id="register" address="http://123.56.14.172:8080/api" appKey="bb617ba9-75b4-4982-8357-0395db6f49cc" />
            List<NameValuePair> nvps = new ArrayList<NameValuePair>();
            nvps.add(new BasicNameValuePair("appKey","bb617ba9-75b4-4982-8357-0395db6f49cc"));
            String message = HttpUtils.doPost("http://127.0.0.1:9090/api/configuration/registCenter",nvps);
            System.out.println(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

package cn.com.netease.nadp.common.net;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpOptions;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
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
        HttpClient httpclient = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(url);
        HttpResponse response = httpclient.execute(httpGet);
        HttpEntity entity = response.getEntity();
        message = EntityUtils.toString(entity, "utf-8");
        return message;
  }

    public static String doPost(String url,List<NameValuePair> nvps) throws Exception {
        String message = null;
        HttpClient httpclient  = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(url);
        httpPost.setEntity(new UrlEncodedFormEntity(nvps));
        HttpResponse response = httpclient.execute(httpPost);
        if(response.getStatusLine().getStatusCode()!=200){
            throw new RuntimeException("connection Exception ! status code :"+response.getStatusLine().getStatusCode());
        }
        HttpEntity entity = response.getEntity();
        message = EntityUtils.toString(entity, "utf-8");
        return message;
    }

    public static String doPost(String url,String jsonStr) throws Exception {
        String message = null;
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(url);
        httpPost.setEntity(new StringEntity(jsonStr));
        HttpResponse response = httpclient.execute(httpPost);
        if(response.getStatusLine().getStatusCode()!=200){
            throw new RuntimeException("connection Exception ! status code :"+response.getStatusLine().getStatusCode());
        }
        HttpEntity entity = response.getEntity();
        message = EntityUtils.toString(entity, "utf-8");
        return message;
    }
}

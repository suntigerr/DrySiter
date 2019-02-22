package com.example.sun.drysister.net;

import android.util.Log;

import com.example.sun.drysister.bean.Sister;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Description：网络请求相关类
 * created by <Sun> on 2019/02/22
 * Email:bigsun343@163.com
 */
public class SisterApi {
    private static final String TAG = "NetWork";
    private static final String BASE_URL = "http://gank.io/api/data/福利/";

    /**
     * 妹子查询
     *
     * @param count 查询数量
     * @param page  第几页
     * @return 返回查询到的信息
     */
    public ArrayList<Sister> fetchSister(int count, int page) {
        String fetchUrl = BASE_URL + count + "/" + page;
        ArrayList<Sister> sisters = new ArrayList<>();
        try {
            URL url = new URL(fetchUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(5000);
            connection.setRequestMethod("GET");
            int code = connection.getResponseCode();
            Log.d(TAG, "fetchSister: server response" + code);
            if (code == 200) {
                InputStream in = connection.getInputStream();
                byte[] data = readFromStream(in);
                String result = new String(data, "UTF-8");
                sisters = parseSister(result);
            } else {
                Log.d(TAG, "fetchSister:请求失败 " + code);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sisters;
    }

    /**
     * 解析json数据的方法
     * @param content 要解析的内容
     * @return  返回集合
     */
    private ArrayList<Sister> parseSister(String content) throws Exception{
        ArrayList<Sister> sisters = new ArrayList<>();
        JSONObject object = new JSONObject(content);
        JSONArray array = object.getJSONArray("results");
        for (int i = 0; i < array.length(); i++) {
            JSONObject result = (JSONObject) array.get(i);
            Sister sister = new Sister();
            sister.setId(result.getString("_id"));
            sister.setCreateAt(result.getString("createdAt"));
            sister.setDesc(result.getString("desc"));
            sister.setPublishedAt(result.getString("publishedAt"));
            sister.setSource(result.getString("source"));
            sister.setType(result.getString("type"));
            sister.setUrl(result.getString("url"));
            sister.setUsed(result.getBoolean("used"));
            sister.setWho(result.getString("who"));
            sisters.add(sister);
        }
        return sisters;
    }

    /**
     *读取流数据的方法
     * @param in 解析的流数据
     * @return 返回字节数组
     */
    private byte[] readFromStream(InputStream in)throws Exception {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] buff = new byte[1024];
        int len ;
        while ((len = in.read(buff))!=-1){
            outputStream.write(buff,0,len);
        }
        in.close();
        return outputStream.toByteArray();
    }
}

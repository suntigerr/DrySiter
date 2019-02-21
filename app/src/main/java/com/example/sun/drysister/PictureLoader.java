package com.example.sun.drysister;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.print.PrinterId;
import android.widget.ImageView;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.LogRecord;

/**
 * Description：${DESCRIPTION}
 * created by <Sun> on 2019/02/22
 * Email:bigsun343@163.com
 */
public class PictureLoader {
    private ImageView mLoadImg;
    private String mImgUrl;
    private byte[] mPicByte;
     Handler  mHandler = new Handler(){
         @Override
         public void handleMessage(Message msg) {
             super.handleMessage(msg);
             if (msg.what ==0x123){
                 if (mPicByte!=null){
                     Bitmap bitmap = BitmapFactory.decodeByteArray(mPicByte,0,mPicByte.length);
                     mLoadImg.setImageBitmap(bitmap);
                 }
             }
         }
     };

     public void load(ImageView imageView, String imgUrl){
         this.mLoadImg = imageView;
         this.mImgUrl = imgUrl;
         Drawable drawable = mLoadImg.getDrawable();
         if (drawable!=null&&drawable instanceof BitmapDrawable){
             Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
             if (bitmap!=null&&bitmap.isRecycled()){
                 bitmap.recycle();
             }
         }
         new Thread(mRunnable).start();
     }
     Runnable mRunnable = new Runnable() {
         @Override
         public void run() {
             try {
                 URL url = new URL(mImgUrl);
                 HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                 connection.setRequestMethod("GET");
                 connection.setReadTimeout(10000);
                 if (connection.getResponseCode() ==200){
                     InputStream inputStream = connection.getInputStream();
                     ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                     byte[] bytes = new byte[1024];
                     int length = -1;
                     while ((length = inputStream.read(bytes))!=-1){
                         outputStream.write(bytes,0,length);
                     }
                     mPicByte = outputStream.toByteArray();
                     inputStream.close();
                     outputStream.close();
                     mHandler.sendEmptyMessage(0x123);
                 }
             } catch (MalformedURLException e) {
                 e.printStackTrace();
             } catch (IOException e) {
                 e.printStackTrace();
             }

         }
     };

}

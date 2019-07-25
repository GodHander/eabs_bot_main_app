package com.fubao.newedu.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;

public class FileUtlis {

    private static final String TAG = "FileUtlis";

    public static final String ENCODING = "UTF-8";

    @SuppressLint("NewApi")
    public static String getFromAssets(Context context, String fileName, boolean base64Flag) {

        String result = "";
        InputStream in = null;
        try {
             in = context.getResources().getAssets().open(fileName);
            //获取文件的字节数
            int lenght = in.available();
            //创建byte数组
            byte[] buffer = new byte[lenght];
            //将文件中的数据读到byte数组中
            in.read(buffer);
            if (base64Flag) {
                result = Base64.getEncoder().encodeToString(buffer); //转base64
            } else {
                result = new String (buffer);
                // EncodingUtils.getString(buffer, ENCODING); //转字符串
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Log.d(TAG,"getFromAssets-->"+result);
        return result;
    }

}

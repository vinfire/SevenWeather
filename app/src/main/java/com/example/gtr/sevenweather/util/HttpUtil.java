package com.example.gtr.sevenweather.util;

import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by GTR on 2017/3/1.
 */

public class HttpUtil {

    public static void sendOkHttpRequest(String address, okhttp3.Callback callback){
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder().url(address).build();
        okHttpClient.newCall(request).enqueue(callback);
    }
}

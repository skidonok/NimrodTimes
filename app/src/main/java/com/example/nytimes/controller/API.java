package com.example.nytimes.controller;

import com.example.nytimes.BuildConfig;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;

public class API {

    public static void getMostPopular(Callback callback, int period) {

        OkHttpClient mClient = new OkHttpClient();
        String apikey = BuildConfig.API_KEY;

        Request request = new Request.Builder()
                .url("https://api.nytimes.com/svc/mostpopular/v2/emailed/"+period+".json?api-key="+apikey)
                .addHeader("Content-Type", "application/json")
                .get()
                .build();
        Call response = mClient.newCall(request);
        response.enqueue(callback);

    }

}

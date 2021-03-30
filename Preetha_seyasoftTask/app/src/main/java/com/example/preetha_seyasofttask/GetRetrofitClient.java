package com.example.preetha_seyasofttask;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GetRetrofitClient {
    private static GetRetrofitClient instance = null;
    private GetAPIService myApi;

    private GetRetrofitClient() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(GetAPIService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        myApi = retrofit.create(GetAPIService.class);
    }

    public static synchronized GetRetrofitClient getInstance() {
        if (instance == null) {
            instance = new GetRetrofitClient();
        }
        return instance;
    }

    public GetAPIService getMyApi() {
        return myApi;
    }
}

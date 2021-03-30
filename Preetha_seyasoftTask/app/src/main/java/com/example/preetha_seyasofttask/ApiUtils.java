package com.example.preetha_seyasofttask;

public class ApiUtils {
    private ApiUtils() {}

    public static final String BASE_URL = "https://source.seyasoftech.com/api/api.php/";

    public static PostAPIService getAPIService() {

        return PostRetrofitClient.getClient(BASE_URL).create(PostAPIService.class);
    }
}

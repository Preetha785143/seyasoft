package com.example.preetha_seyasofttask;

import retrofit2.Call;
import retrofit2.http.GET;

public interface GetAPIService {
    String BASE_URL = "https://source.seyasoftech.com/api/gets.php/";

    @GET("list")
    Call<ContactList> getList();
}

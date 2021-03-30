package com.example.preetha_seyasofttask;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface PostAPIService {
    @POST("posts")
    @FormUrlEncoded
    Call<RequestModel> savePost(@Field("name") String name,
                                @Field("email") String email,
                                @Field("mobile") String mobile,
                                @Field("image") String image);
}

package com.example.bismillah_air.API;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface InterfaceAPI {

    String BASE_URL = "https://9be2-36-91-58-207.ngrok.io/api/";

    @GET("send-mesin/{id}")
    Call<Respon> rp(@Path("id") String id);
}

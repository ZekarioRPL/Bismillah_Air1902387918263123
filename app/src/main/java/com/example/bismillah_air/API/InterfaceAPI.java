package com.example.bismillah_air.API;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface InterfaceAPI {

    String BASE_URL = "https://dfa8-125-166-3-4.ngrok.io/api/";

    @GET("send-mesin/{id}")
    Call<Respon> rp(@Path("id") String id);
}

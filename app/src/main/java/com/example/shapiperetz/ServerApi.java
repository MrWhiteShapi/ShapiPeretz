package com.example.shapiperetz;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ServerApi {

    @GET("category/3")
    Call<List<Constructor>> getMenu();

}



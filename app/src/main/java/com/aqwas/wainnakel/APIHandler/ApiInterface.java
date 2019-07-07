package com.aqwas.wainnakel.APIHandler;


import com.aqwas.wainnakel.Models.RestaurantModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("GenerateFS.php")
    Call<RestaurantModel> getRestaurant(@Query("uid") String location);

}
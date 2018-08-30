package com.example.puza.friendrecommendation.Interface;

import com.example.puza.friendrecommendation.Manager.ApiClient;
import com.example.puza.friendrecommendation.model.Interest;
import com.example.puza.friendrecommendation.model.InterestsDAO.Interests;
import com.example.puza.friendrecommendation.model.Login;
import com.example.puza.friendrecommendation.model.Register;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiInterface {

    @POST(ApiClient.BASE_URL+"signup")
    Call<Register> createRegisterUser(@Query(value = "first_name") String first_name,
                                      @Query(value = "last_name") String last_name,
                                      @Query(value = "email") String email,
                                      @Query(value = "mobile") String mobile,
//                                      @Query(value = "dob") String dob,
//                                      @Query(value = "interests") String interests,
                                      @Query(value = "password") String password,
                                      @Query(value = "address") String address);


    @GET("interests")
    Call<Interest> getInterests();

}

package com.sampleapp.api;

import com.sampleapp.constants.ApiConstants;
import com.sampleapp.model.response.LoginResponse;
import com.sampleapp.model.response.ituneresponse.Example;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Contains API methods to be consumed using Retrofit
 */
public interface RestService {

    //LOGIN API
    @FormUrlEncoded
    @POST(ApiConstants.LOGIN)
    Observable<LoginResponse> login(@Field("email") String email, @Field("password") String password);

    //fetch itune list
//    @FormUrlEncoded
    @GET(ApiConstants.TOP_LIST)
    Observable<Example> fetchMusicList();


}

package com.bus_main

import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface MainApi {

    @FormUrlEncoded
    @POST("user/register")
    suspend fun register(
        @Field("username") username: String?,
        @Field("password") password: String?,
        @Field("repassword") repassword: String?
    ): String
}
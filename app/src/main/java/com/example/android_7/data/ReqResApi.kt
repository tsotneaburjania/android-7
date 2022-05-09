package com.example.android_7.data

import com.example.android_7.data.response.CreatedUserModel
import com.example.android_7.data.response.PageModel
import com.example.android_7.data.request.CreateUserModel
import com.example.android_7.data.response.UpdatedUserModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT

interface ReqResApi {
    @GET("users?page=2")
    fun getUsers(): Call<PageModel>

    @POST("users")
    fun createUser(@Body userData: CreateUserModel): Call<CreatedUserModel>

    @PUT("users/2")
    fun updateUser(@Body userData: CreateUserModel): Call<UpdatedUserModel>
}
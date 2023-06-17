package com.example.shoppinglisttesting.data.remote


import com.example.shoppinglisttesting.data.remote.responses.ImageResponse
import com.example.shoppinglisttesting.utils.Constants.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface PixabayApi {

    @GET("api")
    suspend fun searchImage(
        @Query("q") searchQuery:String,
        @Query("key") apiKey:String = API_KEY
    ):Response<ImageResponse>
}
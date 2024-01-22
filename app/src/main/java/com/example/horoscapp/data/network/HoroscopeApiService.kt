package com.example.horoscapp.data.network

import retrofit2.http.GET
import retrofit2.http.Path

interface HoroscopeApiService {

    @GET("/{name}")
    suspend fun getHoroscope(@Path("name") name:String)

}
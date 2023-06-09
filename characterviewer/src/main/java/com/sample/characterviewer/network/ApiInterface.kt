package com.sample.characterviewer.network

import com.sample.characterviewer.network.response.Response
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers

interface ApiInterface {

    @GET("https://api.duckduckgo.com/?q=simpsons+characters&format=json")
    @Headers("Content-Type: application/json")
    fun getSimpsonsData(): Call<Response>

    @GET("https://api.duckduckgo.com/?q=the+wire+characters&format=json")
    @Headers("Content-Type: application/json")
    fun getTheWireData(): Call<Response>
}
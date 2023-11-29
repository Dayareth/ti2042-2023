package com.example.myfirebaseexample.api

import com.example.myfirebaseexample.api.response.PostResponse
import com.example.myfirebaseexample.api.response.AnimexResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface FirebaseApi {
    @GET("Animes.json")
    fun getAnime(): Call<MutableMap<String, AnimexResponse>>

    @GET("Animes/{id}.json")
    fun getAnime(
        @Path("id") id: String
    ): Call<AnimexResponse>

    @POST("Animes.json")
    fun setAnime(
        @Body() body: AnimexResponse
    ): Call<PostResponse>
}
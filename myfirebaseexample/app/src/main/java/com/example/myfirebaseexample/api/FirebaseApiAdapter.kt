package com.example.myfirebaseexample.api

import com.example.myfirebaseexample.api.response.PostResponse
import com.example.myfirebaseexample.api.response.AnimexResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class FirebaseApiAdapter {
    private var URL_BASE = "https://animex-1a8e4-default-rtdb.firebaseio.com/"
    private val firebaseApi = Retrofit.Builder()
        .baseUrl(URL_BASE)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun getAnime(): MutableMap<String, AnimexResponse>? {
        val call = firebaseApi.create(FirebaseApi::class.java).getAnime().execute()
        val anime = call.body()
        return anime
    }

    fun getAnime(id: String): AnimexResponse? {
        val call = firebaseApi.create(FirebaseApi::class.java).getAnime(id).execute()
        val anime = call.body()
        anime?.id = id
        return anime
    }

    fun setAnime(anime: AnimexResponse): PostResponse? {
        val call = firebaseApi.create(FirebaseApi::class.java).setAnime(anime).execute()
        val results = call.body()
        return results
    }
}
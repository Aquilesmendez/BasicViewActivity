package com.example.basicviewactivity

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface APIService {

    @GET("search/{name}")
    fun searchCharacterByName(@Path("name") name: String): Call<SearchResponse>

    @GET("{id}/powerstats")
    fun getPowerStatsById(@Path("id") id: String): Call<CharacterPowerStats>

    @GET("{id}/image")
    fun getImageById(@Path("id") id: String): Call<CharacterImage>
}

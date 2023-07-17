package com.example.basicviewactivity

import com.google.gson.annotations.SerializedName

data class SearchResponse(
    @SerializedName("response") val response: String,
    @SerializedName("results") val results: List<CharacterResponse>
)
data class CharacterResponse(
    @SerializedName("response") val response: String,
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String
)

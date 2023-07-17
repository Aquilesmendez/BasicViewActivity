package com.example.basicviewactivity

import com.google.gson.annotations.SerializedName

data class CharacterImage(
    @SerializedName("response") val response: String,
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("url") val url: String
)

package com.example.basicviewactivity

import com.google.gson.annotations.SerializedName

data class CharacterPowerStats(
    @SerializedName("response") val response: String,
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("intelligence") val intelligence: String,
    @SerializedName("strength") val strength: String,
    @SerializedName("speed") val speed: String,
    @SerializedName("durability") val durability: String,
    @SerializedName("power") val power: String,
    @SerializedName("combat") val combat: String
)
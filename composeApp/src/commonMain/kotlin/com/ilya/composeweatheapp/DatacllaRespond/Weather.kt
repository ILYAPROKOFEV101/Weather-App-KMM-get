package com.ilya.composeweatheapp.DatacllaRespond


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class Weather(
    @SerialName("description")
    val description: String,
    @SerialName("icon")
    val icon: String,
    @SerialName("id")
    val id: Int,
    @SerialName("main")
    val main: String
)
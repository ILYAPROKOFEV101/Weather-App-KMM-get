package com.ilya.composeweatheapp.DatacllaRespond


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class Sys(
    @SerialName("country")
    val country: String,
    @SerialName("id")
    val id: Int,
    @SerialName("sunrise")
    val sunrise: Int,
    @SerialName("sunset")
    val sunset: Int,
    @SerialName("type")
    val type: Int
)
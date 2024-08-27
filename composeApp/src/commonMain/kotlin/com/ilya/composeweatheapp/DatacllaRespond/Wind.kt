package com.ilya.composeweatheapp.DatacllaRespond


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class Wind(
    @SerialName("deg")
    val deg: Int,
    @SerialName("speed")
    val speed: Double
)
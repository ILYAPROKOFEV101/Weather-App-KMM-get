package com.ilya.composeweatheapp.DatacllaRespond


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class Coord(
    @SerialName("lat")
    val lat: Double,
    @SerialName("lon")
    val lon: Double
)
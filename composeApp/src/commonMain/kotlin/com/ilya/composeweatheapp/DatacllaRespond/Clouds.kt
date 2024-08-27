package com.ilya.composeweatheapp.DatacllaRespond


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class Clouds(
    @SerialName("all")
    val all: Int
)
package com.sakuno.restaurant.json


import com.google.gson.annotations.SerializedName

data class CustomerFullInfo(
    @SerializedName("id")
    val id: String,
    @SerializedName("phone")
    val phone: String?,
    @SerializedName("sessionAuthCode")
    val sessionAuthCode: String?,
    @SerializedName("username")
    val username: String
)
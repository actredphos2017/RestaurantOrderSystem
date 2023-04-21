package com.sakuno.restaurant.json


import com.google.gson.annotations.SerializedName

data class CustomerLoginInfo(
    @SerializedName("account")
    val account: String,
    @SerializedName("password")
    val password: String
)
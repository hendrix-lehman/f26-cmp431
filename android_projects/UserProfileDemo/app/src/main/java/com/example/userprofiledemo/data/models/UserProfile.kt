package com.example.userprofiledemo.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserProfile(
    val id: Int,
    @SerialName("username")
    val uname: String,
    val email: String,
    val phone: String
)
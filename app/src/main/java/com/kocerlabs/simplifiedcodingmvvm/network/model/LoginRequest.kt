package com.kocerlabs.simplifiedcodingmvvm.network.model

data class LoginRequest(
    val username: String,
    val password: String,
    val expiresInMins: Int? = 60 // Varsayılan değer 60
)

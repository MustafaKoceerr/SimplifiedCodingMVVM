package com.kocerlabs.simplifiedcodingmvvm.network.model


data class LoginResponse(
    val id: Long? = null,
    val username: String? = null,
    val email: String? = null,
    val firstName: String? = null,
    val lastName: String? = null,
    val gender: String? = null,
    val image: String? = null,
    val accessToken: String? = null,
    val refreshToken: String? = null,
)


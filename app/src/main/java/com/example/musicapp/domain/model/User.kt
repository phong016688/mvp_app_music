package com.example.musicapp.domain.model

data class User(
    val id: String = "",
    val userName: String = "",
    val role: String = "user"
) {
    fun isAdmin() = role == "admin"
    fun hasId(): Boolean = id.isNotBlank()
}
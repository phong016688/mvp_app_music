package com.example.musicapp.data

import com.example.musicapp.domain.model.User
import kotlinx.coroutines.delay
import kotlin.random.Random

interface Repository {
    suspend fun login(email: String, password: String): User
    suspend fun getUsers(formIndex: Int, toIndex: Int): List<User>
}

class RepositoryImpl : Repository {
    override suspend fun login(email: String, password: String): User {
        delay(2000)
        return if (email == "admin" && password == "admin") {
            User(System.currentTimeMillis().toString(), "admin", "admin")
        } else if (email == "user" && password == "user") {
            User(System.currentTimeMillis().toString(), "user", "user")
        } else {
            throw Throwable("user not exit")
        }
    }

    override suspend fun getUsers(formIndex: Int, toIndex: Int): List<User> {
        delay(2000)
        if (Random.nextBoolean()) {
            throw Throwable("get user error")
        }
        return (formIndex..toIndex)
            .map { User("id${Random.nextInt()}", "name${Random.nextInt()}", "user") }
    }

    companion object {
        val instance by lazy { RepositoryImpl() }
    }
}

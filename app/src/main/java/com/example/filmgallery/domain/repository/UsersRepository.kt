package com.example.filmgallery.domain.repository

import com.example.filmgallery.domain.model.User

interface UsersRepository {

    suspend fun addUser(user: User)
    suspend fun loginUser(userName: String, password: String): User?
    suspend fun getUser(): User?
    suspend fun getUserName(): String?
    suspend fun quit(id: Int, isEntered: Boolean)

}

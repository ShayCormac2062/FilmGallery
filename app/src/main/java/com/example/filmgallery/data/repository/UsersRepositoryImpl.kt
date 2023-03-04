package com.example.filmgallery.data.repository

import com.example.filmgallery.data.local.db.dao.UserDao
import com.example.filmgallery.domain.model.User
import com.example.filmgallery.domain.repository.UsersRepository
import javax.inject.Inject

class UsersRepositoryImpl @Inject constructor(
    private val dao: UserDao
) : UsersRepository {

    override suspend fun addUser(user: User) =
        dao.insert(user.toUserEntity())

    override suspend fun loginUser(
        userName: String,
        password: String
    ): User? = dao.loginUser(userName, password)?.toUser()

    override suspend fun getUser(): User? =
        dao.getUser()?.toUser()

    override suspend fun getUserName(): String? =
        dao.getUserName()

    override suspend fun quit(id: Int, isEntered: Boolean) =
        dao.quit(id, isEntered)

}

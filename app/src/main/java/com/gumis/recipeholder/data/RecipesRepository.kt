package com.gumis.recipeholder.data

import android.app.Application


class RecipesRepository(private val userDao: UserDao) {

    private var user: User? = null

    suspend fun login(email: String, password: String): User? {
        user = userDao.checkEmailAndPassword(email, password)
        return user
    }

    suspend fun register(newUser: User): Boolean {
        val m = userDao.howManyUsers()
        userDao.insertUser(newUser)
        val n = userDao.howManyUsers()
        return n > m
    }

    suspend fun deleteUser(user2Del: User): Boolean {
        val m = userDao.howManyUsers()
        userDao.deleteUser(user2Del)
        val n = userDao.howManyUsers()
        return n < m
    }

    suspend fun updateUser(user2Up: User) {
        userDao.updateUser(user2Up)
    }

}
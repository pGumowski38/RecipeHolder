package com.gumis.recipeholder.data

import androidx.room.*

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertUser(user: User)

    @Delete
    suspend fun deleteUser(user: User)

    @Update
    suspend fun updateUser(user: User)

    @Query("SELECT * FROM user WHERE email = :email AND password = :password")
    suspend fun checkEmailAndPassword(email: String, password: String): User?

    @Query("SELECT count(*) FROM user")
    suspend fun howManyUsers(): Long
}
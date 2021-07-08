package com.gumis.recipeholder

import android.provider.ContactsContract
import androidx.room.*

@Dao
interface UserDao {
    @Insert
    fun insertUser(user: User)

    @Delete
    fun deleteUser(user: User)

    @Update
    fun updateUser(user: User)

    @Query("SELECT count(*) FROM user WHERE email = :email AND password = :password")
    fun checkEmailAndPassword(email: String, password: String): User?
}
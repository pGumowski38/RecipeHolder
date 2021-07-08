package com.gumis.recipeholder

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [User::class], version = 1)
abstract class AppDatabase : RoomDatabase(){
    abstract fun userDao(): UserDao

    @Volatile private var instance: AppDatabase? = null

    fun getInstance(context: Context): AppDatabase? {
        if (instance == null) {
            synchronized(this) {
                if (instance == null) {
                    instance = Room.databaseBuilder(context.applicationContext, AppDatabase::class.java,
                        "recipes-db")
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }
        }
        return instance
    }
}
package com.cbellmont.neoland.datamodel

import androidx.room.Database
import androidx.room.RoomDatabase
import com.cbellmont.neoland.datamodel.user.User
import com.cbellmont.neoland.datamodel.user.UserDao

@Database(entities = [User::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun UserDao(): UserDao
}
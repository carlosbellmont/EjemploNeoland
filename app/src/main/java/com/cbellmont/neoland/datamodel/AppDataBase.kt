package com.cbellmont.neoland.datamodel

import androidx.room.Database
import androidx.room.RoomDatabase
import com.cbellmont.neoland.datamodel.bootcamp.Bootcamp
import com.cbellmont.neoland.datamodel.bootcamp.BootcampDao
import com.cbellmont.neoland.datamodel.campus.Campus
import com.cbellmont.neoland.datamodel.campus.CampusDao
import com.cbellmont.neoland.datamodel.user.User
import com.cbellmont.neoland.datamodel.user.UserDao

@Database(entities = [User::class, Campus::class, Bootcamp::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun UserDao(): UserDao
    abstract fun CampusDao(): CampusDao
    abstract fun BootcampDao(): BootcampDao

}
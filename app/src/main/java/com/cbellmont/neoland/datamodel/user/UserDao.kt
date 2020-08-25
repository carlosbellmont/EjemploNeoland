package com.cbellmont.neoland.datamodel.user

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface UserDao {
    @Query("SELECT * FROM User")
    fun getAll(): List<User>

    @Query("SELECT * FROM User")
    fun getAllLive(): LiveData<List<User>>

    @Query("SELECT * FROM User WHERE uid IN (:UsersId)")
    fun loadAllByIds(UsersId: IntArray): List<User>

    @Query("SELECT * FROM User WHERE name LIKE (:nombreUser)")
    fun loadAllByTitle(nombreUser: String): List<User>

    @Insert
    fun insert(User: User)

    @Update
    fun update(User: User)

    @Insert
    fun insertAll(Users: List<User>)

    @Delete
    fun delete(User: User)

    @Query("DELETE FROM User")
    fun deleteAll()
}

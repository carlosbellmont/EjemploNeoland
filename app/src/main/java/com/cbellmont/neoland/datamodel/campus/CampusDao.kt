package com.cbellmont.neoland.datamodel.campus

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query


@Dao
interface CampusDao {
    @Query("SELECT * FROM Campus")
    fun getAll(): List<Campus>

    @Query("SELECT * FROM Campus")
    fun getAllLive(): LiveData<List<Campus>>

    @Insert
    fun insertAll(listCampus: List<Campus>)

    @Insert
    fun insert(listCampus: Campus)
}
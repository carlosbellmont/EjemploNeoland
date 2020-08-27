package com.cbellmont.neoland.datamodel.bootcamp

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.cbellmont.neoland.datamodel.BootcampWithCampus


@Dao
interface BootcampDao {
    @Query("SELECT * FROM Bootcamp")
    fun getAll(): List<Bootcamp>

    @Query("SELECT * FROM Bootcamp")
    fun getAllLive(): LiveData<List<Bootcamp>>

    @Query("SELECT  * FROM  Bootcamp ORDER BY RANDOM() LIMIT 1")
    fun getRandom(): Bootcamp

    @Insert
    fun insertAll(listCampus: List<Bootcamp>)

    @Query("SELECT * FROM bootcamp INNER JOIN campus ON bootcamp.fkCampusId = campus.campusId")
    fun getBootcampWithCampusLive(): LiveData<List<BootcampWithCampus>>
}
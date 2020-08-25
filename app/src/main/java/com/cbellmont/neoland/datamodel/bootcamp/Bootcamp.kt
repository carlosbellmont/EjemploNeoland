package com.cbellmont.neoland.datamodel.bootcamp

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.cbellmont.neoland.datamodel.campus.Campus

@Entity(foreignKeys = arrayOf(
    ForeignKey(entity = Campus::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("campusId"),
        onDelete = ForeignKey.SET_NULL),
    ))
data class Bootcamp (
    val name : String,
    val about : String,
    var campusId : Int? = null
) {

    @PrimaryKey(autoGenerate = true)
    var id = 0
}
package com.cbellmont.neoland.datamodel.bootcamp

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.cbellmont.neoland.datamodel.campus.Campus

@Entity(foreignKeys = [ForeignKey(entity = Campus::class,
    parentColumns = arrayOf("campusId"),
    childColumns = arrayOf("fkCampusId"),
    onDelete = ForeignKey.SET_NULL)]
)
data class Bootcamp (
    val bootcampName : String,
    val about : String,
    var fkCampusId : Int? = null
) {

    @PrimaryKey(autoGenerate = true)
    var bootcampId = 0
}
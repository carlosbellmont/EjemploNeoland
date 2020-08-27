package com.cbellmont.neoland.datamodel.campus

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Campus (
    val campusName : String,
    val image : Int
) {
    @PrimaryKey(autoGenerate = true)
    var campusId = 0
}
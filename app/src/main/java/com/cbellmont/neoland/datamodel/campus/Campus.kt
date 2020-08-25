package com.cbellmont.neoland.datamodel.campus

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Campus (
    val name : String,
    val image : Int
) {
    @PrimaryKey(autoGenerate = true)
    var id = 0
}
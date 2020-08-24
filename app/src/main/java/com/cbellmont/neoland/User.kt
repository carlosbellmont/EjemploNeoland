package com.cbellmont.neoland

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    @Embedded
    var name: Name,
    var gender: String,
    var email: String,
    @Embedded
    var picture: Picture
) {
    @PrimaryKey(autoGenerate = true)
    var uid = 0
}

package com.cbellmont.neoland.datamodel.user

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

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

data class Name (
    @SerializedName("first") var name: String,
    @SerializedName("last") var surname: String
)
data class Picture (
    var large : String,
    var medium : String,
    var thumbnail : String
)
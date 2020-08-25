package com.cbellmont.neoland.datamodel.user

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.cbellmont.neoland.datamodel.bootcamp.Bootcamp
import com.google.gson.annotations.SerializedName

@Entity(foreignKeys = arrayOf(
    ForeignKey(entity = Bootcamp::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("bootcampId"),
        onDelete = ForeignKey.SET_NULL),
    ))

data class User(
    @Embedded
    var name: Name,
    var gender: String,
    var email: String,
    @Embedded
    var picture: Picture,
) {
    @PrimaryKey(autoGenerate = true)
    var uid = 0
    var bootcampId : Int? = null
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
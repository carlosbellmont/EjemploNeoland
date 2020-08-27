package com.cbellmont.neoland.datamodel.user

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.cbellmont.neoland.datamodel.bootcamp.Bootcamp
import com.google.gson.annotations.SerializedName


interface UserMostrable {
    fun getNombreEnString() : String
    fun getFotoUrl() : String
    fun getEmailEnString() : String

}



@Entity(foreignKeys = arrayOf(
    ForeignKey(entity = Bootcamp::class,
        parentColumns = arrayOf("bootcampId"),
        childColumns = arrayOf("fkBootcampId"),
        onDelete = ForeignKey.SET_NULL),
))

data class User(
    @Embedded
    var name: Name,
    var gender: String,
    var email: String,
    @Embedded
    var picture: Picture,
) : UserMostrable {
    @PrimaryKey(autoGenerate = true)
    var userId = 0
    var fkBootcampId : Int? = null

    override fun getNombreEnString() : String {
        return String.format("%s (%s)", name.name, name.surname)
    }

    override fun getFotoUrl() : String {
        return picture.large
    }

    override fun getEmailEnString(): String {
        return email
    }
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
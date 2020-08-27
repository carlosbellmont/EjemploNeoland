package com.cbellmont.neoland.datamodel

import androidx.room.Embedded
import com.cbellmont.neoland.datamodel.bootcamp.Bootcamp
import com.cbellmont.neoland.datamodel.user.User
import com.cbellmont.neoland.datamodel.user.UserMostrable


data class UserWithBootcamp (
    @Embedded var user : User,
    @Embedded var bootcamp: Bootcamp
) : UserMostrable {
    override fun getNombreEnString(): String {
        return String.format("%s %s", user.name.name, user.name.surname)
    }

    override fun getFotoUrl(): String {
        return user.picture.large
    }

    override fun getEmailEnString(): String {
        return user.email
    }
}
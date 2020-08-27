package com.cbellmont.neoland.datamodel

import androidx.room.Embedded
import com.cbellmont.neoland.datamodel.bootcamp.Bootcamp
import com.cbellmont.neoland.datamodel.campus.Campus

data class BootcampWithCampus (
    @Embedded var bootcamp: Bootcamp,
    @Embedded var campus : Campus
)
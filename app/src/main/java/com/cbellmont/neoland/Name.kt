package com.cbellmont.neoland

import com.google.gson.annotations.SerializedName

data class Name (
    @SerializedName("first") var name: String,
    @SerializedName("last") var surname: String
)
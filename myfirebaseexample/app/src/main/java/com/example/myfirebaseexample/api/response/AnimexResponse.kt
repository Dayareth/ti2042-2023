package com.example.myfirebaseexample.api.response

import com.google.gson.annotations.SerializedName

data class AnimexResponse(
    @SerializedName("00_Id") var id: String,
    @SerializedName("01_Nombre") var name: String,
    @SerializedName("14_Temporada") var tempo: String,
    @SerializedName("15_Link") var link: String,
    @SerializedName("16_Capitulo") var capi: Long
)

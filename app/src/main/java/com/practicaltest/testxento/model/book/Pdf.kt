package com.practicaltest.testxento.model.book

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Pdf {
    @SerializedName("isAvailable")
    @Expose
    var isAvailable: Boolean? = null

    @SerializedName("acsTokenLink")
    @Expose
    var acsTokenLink: String? = null
}
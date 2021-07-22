package com.practicaltest.testxento.model.book

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class IndustryIdentifier {

    @SerializedName("type")
    @Expose
    var type: String? = null

    @SerializedName("identifier")
    @Expose
    var identifier: String? = null
}
package com.practicaltest.testxento.model.book

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class RetailPrice {
    @SerializedName("amount")
    @Expose
    var amount: Float? = null

    @SerializedName("currencyCode")
    @Expose
    var currencyCode: String? = null
}
package com.practicaltest.testxento.model.book

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class BookItem {
    @SerializedName("kind")
    @Expose
    var kind: String? = null

    @SerializedName("totalItems")
    @Expose
    var totalItems: Int? = null

    @SerializedName("items")
    @Expose
    var items: List<Item>? = null
}
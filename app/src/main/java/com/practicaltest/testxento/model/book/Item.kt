package com.practicaltest.testxento.model.book

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Item {

    @SerializedName("volumeInfo")
    @Expose
    var volumeInfo: VolumeInfo? = null

    @SerializedName("accessInfo")
    @Expose
    var accessInfo: AccessInfo? = null

   /* @SerializedName("saleInfo")
    @Expose
    var saleInfo: SaleInfo? = null



    @SerializedName("searchInfo")
    @Expose
    var searchInfo: SearchInfo? = null*/
}
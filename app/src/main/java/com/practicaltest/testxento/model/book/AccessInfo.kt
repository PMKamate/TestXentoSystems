package com.practicaltest.testxento.model.book

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class AccessInfo {

    @SerializedName("country")
    @Expose
   public var country: String? = null

    @SerializedName("viewability")
    @Expose
    var viewability: String? = null

    @SerializedName("embeddable")
    @Expose
    var embeddable: Boolean? = null

    @SerializedName("publicDomain")
    @Expose
    var publicDomain: Boolean? = null

    @SerializedName("textToSpeechPermission")
    @Expose
    var textToSpeechPermission: String? = null

    @SerializedName("epub")
    @Expose
    var epub: Epub? = null

    @SerializedName("pdf")
    @Expose
    var pdf: Pdf? = null

    @SerializedName("webReaderLink")
    @Expose
    var webReaderLink: String? = null

    @SerializedName("accessViewStatus")
    @Expose
    var accessViewStatus: String? = null

    @SerializedName("quoteSharingAllowed")
    @Expose
    var quoteSharingAllowed: Boolean? = null
}

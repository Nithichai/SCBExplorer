package com.nopyjf.projectscbexplorer.vo

import com.google.gson.annotations.SerializedName

data class OauthAuthorize(
    @SerializedName("data") val data: Data,
    @SerializedName("status") val status: Status
)

data class Data(
    @SerializedName("callbackUrl") val callbackUrl: String
)

data class Status(
    @SerializedName("code") val code: Int,
    @SerializedName("description") val description: String
)
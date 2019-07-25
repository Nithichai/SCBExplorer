package com.nopyjf.projectscbexplorer.vo


import com.google.gson.annotations.SerializedName

data class Transaction(
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("status")
    val status: Status
) {
    data class Status(
        @SerializedName("code")
        val code: Int,
        @SerializedName("description")
        val description: String
    )

    data class Data(
        @SerializedName("deeplinkUrl")
        val deeplinkUrl: String,
        @SerializedName("transactionId")
        val transactionId: String,
        @SerializedName("userRefId")
        val userRefId: String
    )
}
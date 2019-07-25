package com.nopyjf.projectscbexplorer.vo


import com.google.gson.annotations.SerializedName

data class OauthToken(
    @SerializedName("data")
    val data: Data,
    @SerializedName("status")
    val status: Status
) {
    data class Data(
        @SerializedName("accessToken")
        val accessToken: String,
        @SerializedName("expiresAt")
        val expiresAt: Long,
        @SerializedName("expiresIn")
        val expiresIn: Long,
        @SerializedName("refreshExpiresAt")
        val refreshExpiresAt: Long,
        @SerializedName("refreshExpiresIn")
        val refreshExpiresIn: Long,
        @SerializedName("refreshToken")
        val refreshToken: String,
        @SerializedName("tokenType")
        val tokenType: String
    )

    data class Status(
        @SerializedName("code")
        val code: Long,
        @SerializedName("description")
        val description: String
    )
}


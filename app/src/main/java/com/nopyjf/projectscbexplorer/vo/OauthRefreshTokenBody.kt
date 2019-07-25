package com.nopyjf.projectscbexplorer.vo

data class OathRefreshTokenBody(
    val applicationKey: String,
    val applicationSecret: String,
    val refreshToken: String
)
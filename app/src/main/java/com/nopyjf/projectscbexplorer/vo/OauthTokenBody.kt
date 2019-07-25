package com.nopyjf.projectscbexplorer.vo

data class OathTokenBody(
    val applicationKey: String,
    val applicationSecret: String,
    val authCode: String
)
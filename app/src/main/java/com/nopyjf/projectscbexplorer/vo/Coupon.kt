package com.nopyjf.projectscbexplorer.vo

data class Coupon(
    val coupon_id: Int,
    val merchant_name: String,
    val code: String,
    val title_name: String,
    val description: String,
    val created_date: String,
    val expired_date: String,
    val image_path: String,
    val qr_code: String,
    val customer_id: String
)
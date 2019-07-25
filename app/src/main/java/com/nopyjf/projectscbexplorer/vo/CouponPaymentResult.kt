package com.nopyjf.projectscbexplorer.vo


import com.google.gson.annotations.SerializedName

data class CouponPaymentResult(
    @SerializedName("code")
    val code: String,
    @SerializedName("coupon_id")
    val couponId: Int,
    @SerializedName("created_date")
    val createdDate: String,
    @SerializedName("customer_id")
    val customerId: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("expired_date")
    val expiredDate: String,
    @SerializedName("image_path")
    val imagePath: String,
    @SerializedName("merchant_name")
    val merchantName: String,
    @SerializedName("qr_code")
    val qrCode: String,
    @SerializedName("title_name")
    val titleName: String
)
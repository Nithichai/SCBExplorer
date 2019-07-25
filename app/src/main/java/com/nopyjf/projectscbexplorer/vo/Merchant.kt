package com.nopyjf.projectscbexplorer.vo


import com.google.gson.annotations.SerializedName

data class Merchant(
    @SerializedName("biller_id")
    val billerId: String,
    @SerializedName("biller_name")
    val billerName: String,
    @SerializedName("latitude")
    val latitude: Double,
    @SerializedName("longitude")
    val longitude: Double,
    @SerializedName("m_id")
    val mId: Int,
    @SerializedName("merchant_id")
    val merchantId: String,
    @SerializedName("merchant_name")
    val merchantName: String,
    @SerializedName("promotion_list")
    val promotionList: List<Promotion>,
    @SerializedName("terminal_id")
    val terminalId: String,
    @SerializedName("terminal_prefix")
    val terminalPrefix: String
) {
    data class Promotion(
        @SerializedName("coupon_list")
        val couponList: List<Coupon>,
        @SerializedName("description")
        val description: String,
        @SerializedName("discount_price")
        val discountPrice: Double,
        @SerializedName("expires_at")
        val expiresAt: String,
        @SerializedName("image_path")
        val imagePath: Any,
        @SerializedName("original_price")
        val originalPrice: Double,
        @SerializedName("product")
        val product: String,
        @SerializedName("promotion_id")
        val promotionId: Int,
        @SerializedName("quantity")
        val quantity: Int,
        @SerializedName("start_at")
        val startAt: String,
        @SerializedName("title_name")
        val titleName: String
    ) {
        data class Coupon(
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
    }
}
package com.nopyjf.projectscbexplorer.vo


import com.google.gson.annotations.SerializedName

data class TransactionBody(
    @SerializedName("accountTo")
    val accountTo: String,
//    @SerializedName("merchantMetaData"),
//    val merchantMetaData: MerchantMetaData,
    @SerializedName("paymentAmount")
    val paymentAmount: Double,
    @SerializedName("ref1")
    val ref1: String,
    @SerializedName("ref2")
    val ref2: String,
    @SerializedName("ref3")
    val ref3: String,
    @SerializedName("transactionSubType")
    val transactionSubType: String,
    @SerializedName("transactionType")
    val transactionType: String
)
//{
//    data class MerchantMetaData(
//        @SerializedName("analytics")
//        val analytics: Analytics,
//        @SerializedName("paymentInfo")
//        val paymentInfo: ArrayList<PaymentInfo>
//    ) {
//        data class Analytics(
//            @SerializedName("Detail1")
//            val detail1: String,
//            @SerializedName("Detail2")
//            val detail2: String,
//            @SerializedName("Detail3")
//            val detail3: String,
//            @SerializedName("Detail4")
//            val detail4: String,
//            @SerializedName("Detail5")
//            val detail5: String,
//            @SerializedName("Detail6")
//            val detail6: String,
//            @SerializedName("Partner")
//            val partner: String,
//            @SerializedName("Product category")
//            val productCategory: String,
//            @SerializedName("Product code")
//            val productCode: String
//        )
//
//        data class PaymentInfo(
//            @SerializedName("description")
//            val description: String,
//            @SerializedName("header")
//            val header: String,
//            @SerializedName("imageUrl")
//            val imageUrl: String,
//            @SerializedName("title")
//            val title: String,
//            @SerializedName("type")
//            val type: String
//        )
//    }
//}
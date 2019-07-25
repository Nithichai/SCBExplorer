package com.nopyjf.projectscbexplorer.activity

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.nopyjf.projectscbexplorer.R
import com.nopyjf.projectscbexplorer.service.ApiManager
import com.nopyjf.projectscbexplorer.vo.Merchant
import com.nopyjf.projectscbexplorer.vo.Transaction
import com.nopyjf.projectscbexplorer.vo.TransactionBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MerchantActivity : AppCompatActivity() {

    private lateinit var merchantImage: ImageView
    private lateinit var merchantName: TextView
    private lateinit var merchantCouponFood: TextView
    private lateinit var merchantCouponDescribe: TextView
    private lateinit var merchantOriginalPrice: TextView
    private lateinit var merchantCouponExpire: TextView
    private lateinit var merchantCouponButton: Button

    private var discountPrice: Double = 0.0
    private var promotionId: Int = 0

    private val merchantCallback = object : Callback<Merchant> {
        override fun onFailure(call: Call<Merchant>, t: Throwable) {
            Log.e("networking", "Can not call authorize", t)
        }

        @SuppressLint("SetTextI18n")
        override fun onResponse(call: Call<Merchant>, response: Response<Merchant>) {
            val responseData = response.body() ?: return
            merchantName.text = responseData.merchantName
            merchantCouponFood.text = responseData.promotionList[0].couponList[0].titleName
            merchantCouponDescribe.text = responseData.promotionList[0].couponList[0].description
            merchantOriginalPrice.text = "จากราคาปกติ ${responseData.promotionList[0].originalPrice}"
            merchantCouponExpire.text = "หมดอายุใน 21/07/2019"

            discountPrice = responseData.promotionList[0].discountPrice
            merchantCouponButton.text = "${discountPrice} บาท ซื้อเลย"
            Glide.with(applicationContext)
                .load(responseData.promotionList[0].couponList[0].imagePath)
                .into(merchantImage)

            promotionId = responseData.promotionList[0].promotionId

            val prefs =
                getSharedPreferences("SCBExplorerSharedPrefPayment", MODE_PRIVATE).edit()
            prefs.putInt("promotionId", promotionId)
            prefs.apply()
        }
    }

    private val transactionCallback= object : Callback<Transaction> {
        override fun onFailure(call: Call<Transaction>, t: Throwable) {
            Log.e("Nopy", t.localizedMessage!!)
        }

        override fun onResponse(call: Call<Transaction>, response: Response<Transaction>) {
            val body = response.body() ?: return
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse("${body.data.deeplinkUrl}?callback_url=scbexplorer://payment")
            startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_merchant)

        merchantImage = findViewById(R.id.merchant_img)
        merchantName = findViewById(R.id.merchant_name)
        merchantCouponFood = findViewById(R.id.merchant_coupon_food)
        merchantCouponDescribe = findViewById(R.id.merchant_coupon_describe)
        merchantOriginalPrice = findViewById(R.id.merchant_original_price)
        merchantCouponExpire = findViewById(R.id.merchant_coupon_expire)
        merchantCouponButton = findViewById(R.id.merchant_btn)

        val prefs = getSharedPreferences("SCBExplorerSharedPref", Context.MODE_PRIVATE)
        val accessToken = prefs.getString("accessToken", null)

        merchantCouponButton.setOnClickListener {
            val body = TransactionBody(
                "092509466433254",
                discountPrice,
                "REFERENCE1",
                "REFERENCE2",
                "SCB",
                "BPA",
                "PAYMENT"
            )
            ApiManager.transactionService.deeplink(
                "Bearer $accessToken",
                "64847ab5-7be8-4fdc-bde1-5957d2f8017f",
                body
            ).enqueue(transactionCallback)
        }

        getMerchantData()
    }

    private fun getMerchantData() {
        ApiManager.merchantService.getMerchant(1).enqueue(merchantCallback)
    }
}

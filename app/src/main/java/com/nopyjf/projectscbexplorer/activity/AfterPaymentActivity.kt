package com.nopyjf.projectscbexplorer.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.nopyjf.projectscbexplorer.R
import com.nopyjf.projectscbexplorer.service.ApiManager
import com.nopyjf.projectscbexplorer.vo.CouponPaymentResult
import com.nopyjf.projectscbexplorer.vo.CustomerProfile
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AfterPaymentActivity : AppCompatActivity() {

    private val customerCallback= object : Callback<CustomerProfile> {
        override fun onFailure(call: Call<CustomerProfile>, t: Throwable) {
            Log.e("Nopy", t.localizedMessage!!)
        }

        override fun onResponse(call: Call<CustomerProfile>, response: Response<CustomerProfile>) {
            val body = response.body() ?: return
            val citizenId = body.data.profile.citizenID

            val prefs
                    = getSharedPreferences("SCBExplorerSharedPrefPayment", Context.MODE_PRIVATE)
            val promotionId = prefs.getInt("promotionId", -1)

            Log.e("Nopy", "citizenId")
            Log.e("Nopy", "promotionId")

            couponUpdate(promotionId, citizenId)
        }

    }

    private val couponUpdateCallback= object : Callback<CouponPaymentResult> {
        override fun onFailure(call: Call<CouponPaymentResult>, t: Throwable) {
            Log.e("Nopy", t.localizedMessage!!)
        }

        override fun onResponse(call: Call<CouponPaymentResult>, response: Response<CouponPaymentResult>) {
            val code = response.code()
            Log.e("Nopy", code.toString())
            if (code == 200) {
                intent = Intent(this@AfterPaymentActivity, MainActivity::class.java).apply {
                    putExtra("coupon", true)
                }
                startActivity(intent)
            }
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_after_payment)

        val prefs = getSharedPreferences("SCBExplorerSharedPref", Context.MODE_PRIVATE)
        val accessToken = prefs.getString("accessToken", null)

        getCustomerProfile(accessToken!!)
    }

    private fun getCustomerProfile(accessToken: String) {
        ApiManager.customerService.profile(
            "Bearer $accessToken",
            "64847ab5-7be8-4fdc-bde1-5957d2f8017f"
        ).enqueue(customerCallback)
    }

    private fun couponUpdate(promotionId: Int, citizenId: String) {
        ApiManager.couponService.updateCoupon(promotionId,citizenId).enqueue(couponUpdateCallback)
    }


}

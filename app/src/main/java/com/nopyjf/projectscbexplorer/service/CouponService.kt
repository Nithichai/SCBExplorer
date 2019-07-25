package com.nopyjf.projectscbexplorer.service

import com.nopyjf.projectscbexplorer.vo.Coupon
import com.nopyjf.projectscbexplorer.vo.CouponPaymentResult
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Query

interface CouponService {
    @GET("citizen/123456789")
    fun getCoupons(): Call<ArrayList<Coupon>>

    @PUT(".")
    fun updateCoupon(
        @Query("promotionId") promotionId: Int,
        @Query("citizenId") citizenId: String
    ): Call<CouponPaymentResult>


}
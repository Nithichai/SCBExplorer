package com.nopyjf.projectscbexplorer.service

import com.nopyjf.projectscbexplorer.vo.Coupon
import com.nopyjf.projectscbexplorer.vo.CouponPaymentResult
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface CouponService {
    @GET("{id}")
    fun getCoupon(@Path(value="id") id: Int): Call<Coupon>

    @GET("citizen/{id}")
    fun getCoupons(@Path(value="id") id: String): Call<ArrayList<Coupon>>

    @PUT(".")
    fun updateCoupon(
        @Query("promotionId") promotionId: Int,
        @Query("citizenId") citizenId: String
    ): Call<CouponPaymentResult>
}
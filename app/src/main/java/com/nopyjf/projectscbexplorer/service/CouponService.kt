package com.nopyjf.projectscbexplorer.service

import com.nopyjf.projectscbexplorer.vo.Coupon
import retrofit2.Call
import retrofit2.http.GET

interface CouponService {
    @GET("citizen/123456789")
    fun getCoupons(): Call<ArrayList<Coupon>>
}
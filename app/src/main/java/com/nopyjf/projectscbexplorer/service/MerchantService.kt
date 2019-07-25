package com.nopyjf.projectscbexplorer.service

import com.nopyjf.projectscbexplorer.vo.Coupon
import retrofit2.Call
import retrofit2.http.GET

interface MerchantService {
    @GET("citizen/123456789")
    fun getMerchant(): Call<ArrayList<Coupon>>
}
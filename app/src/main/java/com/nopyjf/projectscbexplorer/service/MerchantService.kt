package com.nopyjf.projectscbexplorer.service

import com.nopyjf.projectscbexplorer.vo.Merchant
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface MerchantService {
    @GET("{id}")
    fun getMerchant(@Path(value="id") id: Int): Call<Merchant>
}
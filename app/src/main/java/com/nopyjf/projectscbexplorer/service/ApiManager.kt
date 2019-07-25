package com.nopyjf.projectscbexplorer.service

import com.nopyjf.projectscbexplorer.vo.CustomerService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiManager {

    val oauthV1Service by lazy {
        createService<OauthService>("https://api.partners.scb/partners/sandbox/v1/oauth/")
    }

    val oauthV2Service by lazy {
        createService<OauthService>("https://api.partners.scb/partners/sandbox/v2/oauth/")
    }

    val customerService by lazy {
        createService<CustomerService>("https://api.partners.scb/partners/sandbox/v1/customers/")
    }

    val couponService by lazy {
        createService<CouponService>("http://192.168.101.44:9000/api/coupon/")
    }

    private inline fun <reified T> createService(baseUrl: String): T =
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .run { create(T::class.java) }
}
package com.nopyjf.projectscbexplorer.vo

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers

interface CustomerService {
    @GET("profile")
    @Headers(
        "requestUId: f5a16aeb-0f81-4bb5-b76c-014b61887c7f",
        "accept-language: EN",
        "Content-Type: application/json"
    )
    fun profile(
        @Header("authorization") authorize: String,
        @Header("resourceOwnerId") resourceOwnerId: String
    ): Call<CustomerProfile>
}


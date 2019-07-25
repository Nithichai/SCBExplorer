package com.nopyjf.projectscbexplorer.service

import com.nopyjf.projectscbexplorer.vo.Transaction
import com.nopyjf.projectscbexplorer.vo.TransactionBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

interface TransactionService {
    @Headers(
        "Content-Type: application/json",
        "requestUId: 3c8948e9-52a2-410f-9a56-51f922c5856d",
        "channel: scbeasy",
        "accept-language: EN"
    )
    @POST("transactions")
    fun deeplink(
        @Header("authorization") authorize: String,
        @Header("resourceOwnerId") resourceOwnerId: String,
        @Body body: TransactionBody
    ): Call<Transaction>
}


package com.nopyjf.projectscbexplorer.service

import com.nopyjf.projectscbexplorer.vo.OathRefreshTokenBody
import com.nopyjf.projectscbexplorer.vo.OathTokenBody
import com.nopyjf.projectscbexplorer.vo.OauthAuthorize
import com.nopyjf.projectscbexplorer.vo.OauthToken
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST

interface OauthService {
    @GET("authorize")
    @Headers(
        "accept-language: EN",
        "endState: mobile_app",
        "response-channel: mobile",
        "requestUId: c48ec992-0f22-4aad-8acc-f7f1f22a3607",
        "resourceOwnerId: l7b6a7f49e5b3849bcb6d08e2e46c943c8",
        "apisecret: dbae3c0665fd4f25aa2043ca1fdcb1e1",
        "apikey: l7b6a7f49e5b3849bcb6d08e2e46c943c8"
    )
    fun authorize(): Call<OauthAuthorize>


    @Headers(
        "resourceOwnerId: l7b6a7f49e5b3849bcb6d08e2e46c943c8",
        "requestUId: 4ed551de-ce46-49bd-b088-d251b90f2d5f",
        "Content-Type: application/json")
    @POST("token")
    fun token(@Body body: OathTokenBody): Call<OauthToken>


    @Headers(
        "resourceOwnerId: l7b6a7f49e5b3849bcb6d08e2e46c943c8",
        "requestUId: 4ed551de-ce46-49bd-b088-d251b90f2d5f",
        "Content-Type: application/json")
    @POST("token/refresh")
    fun refreshToken(@Body body: OathRefreshTokenBody): Call<OauthToken>
}


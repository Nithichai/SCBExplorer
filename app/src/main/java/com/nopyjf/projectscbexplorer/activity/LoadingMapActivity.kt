package com.nopyjf.projectscbexplorer.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.nopyjf.projectscbexplorer.service.ApiManager
import com.nopyjf.projectscbexplorer.vo.OathTokenBody
import com.nopyjf.projectscbexplorer.vo.OauthToken
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LoadingMapActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.nopyjf.projectscbexplorer.R.layout.activity_main)
        Log.e("Nopy", getAuthCode())
        getAuthCode().apply {
            getToken(this)
        }
    }

    private fun getAuthCode(): String {
        val uri = intent.data ?: return ""
        return uri.getQueryParameter("code")!!
    }

    private fun getToken(authCode: String) {
        OathTokenBody(
            "l7b6a7f49e5b3849bcb6d08e2e46c943c8",
            "dbae3c0665fd4f25aa2043ca1fdcb1e1",
            authCode
        ).apply {
            ApiManager.oauthV1Service.token(this).enqueue(oauthTokenCallback)
        }
    }

    private val oauthTokenCallback = object : Callback<OauthToken> {
        override fun onFailure(call: Call<OauthToken>, t: Throwable) {
            Log.e("networking", "Can not call token", t)
        }

        override fun onResponse(call: Call<OauthToken>, response: Response<OauthToken>) {
            val responseData = response.body() ?: return
            val accessToken = responseData.data.accessToken
            val refreshToken = responseData.data.refreshToken
            saveToken(accessToken, refreshToken)
            gotoMapPage()
        }
    }

    private fun saveToken(accessToken: String, refreshToken: String) {
        val prefs = getSharedPreferences("SCBExplorerSharedPref", MODE_PRIVATE).edit()
        prefs.putString("accessToken", accessToken)
        prefs.putString("refreshToken", refreshToken)
        prefs.apply()
    }

    private fun gotoMapPage() {
        val intent = Intent(this@LoadingMapActivity, MapsActivity::class.java)

        val prefs = getSharedPreferences("SCBExplorerSharedPref", Context.MODE_PRIVATE)
        val accessToken = prefs.getString("accessToken", null) ?: return
        val refreshToken = prefs.getString("refreshToken", null) ?: return

        intent.putExtra("accessToken", accessToken)
        intent.putExtra("refreshToken", refreshToken)
        startActivity(intent)
    }
}

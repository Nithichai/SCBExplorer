package com.nopyjf.projectscbexplorer.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.nopyjf.projectscbexplorer.R
import com.nopyjf.projectscbexplorer.service.ApiManager
import com.nopyjf.projectscbexplorer.vo.OauthAuthorize
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    private lateinit var loginBtn: Button

    private val oauthAuthorizeCallback = object : Callback<OauthAuthorize> {
        override fun onFailure(call: Call<OauthAuthorize>, t: Throwable) {
            Log.e("networking", "Can not call authorize", t)
        }

        override fun onResponse(call: Call<OauthAuthorize>, response: Response<OauthAuthorize>) {
            val responseData = response.body() ?: return
            val deepLink = responseData.data.callbackUrl
            intoDeepLink(deepLink)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val prefs = getSharedPreferences("SCBExplorerSharedPref", MODE_PRIVATE).edit()
        prefs.clear()
        prefs.apply()

        val prefsPayment
                = getSharedPreferences("SCBExplorerSharedPrefPayment", MODE_PRIVATE).edit()
        prefsPayment.clear()
        prefsPayment.apply()

        loginBtn = findViewById(R.id.login_btn)
        loginBtn.setOnClickListener {
            getDeeplink()
        }
    }

    private fun getDeeplink() {
        ApiManager.oauthV2Service.authorize().enqueue(oauthAuthorizeCallback)
    }

    private fun intoDeepLink(deeplink: String) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(deeplink)
        startActivity(intent)
    }
}

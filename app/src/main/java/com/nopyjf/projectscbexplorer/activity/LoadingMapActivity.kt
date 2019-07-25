package com.nopyjf.projectscbexplorer.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.nopyjf.projectscbexplorer.R
import com.nopyjf.projectscbexplorer.database.AppDatabase
import com.nopyjf.projectscbexplorer.database.TokenEntity
import com.nopyjf.projectscbexplorer.service.ApiManager
import com.nopyjf.projectscbexplorer.utilities.SCBExplorerThread
import com.nopyjf.projectscbexplorer.vo.OathTokenBody
import com.nopyjf.projectscbexplorer.vo.OauthToken
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LoadingMapActivity : AppCompatActivity() {

    private lateinit var dbAdapter: AppDatabase
    private lateinit var thread: SCBExplorerThread

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.e("Nopy", getAuthCode())
        getAuthCode().apply {
//            setupThread()
//            setupDatabase()
            getToken(this)
        }
    }

    private fun getAuthCode(): String {
        val uri = intent.data ?: return ""
        return uri.getQueryParameter("code")!!
    }

    fun replaceFragment(fragmentClass: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.main_container, fragmentClass).commit()
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

    private fun setupThread() {
        thread = SCBExplorerThread("SCB_EXPLORER_THREAD")
        thread.start()
    }

    private val oauthTokenCallback = object : Callback<OauthToken> {
        override fun onFailure(call: Call<OauthToken>, t: Throwable) {
            Log.e("networking", "Can not call token", t)
        }

        override fun onResponse(call: Call<OauthToken>, response: Response<OauthToken>) {
            Log.e("Nopy", response.body().toString())
            Log.e("Nopy", response.code().toString())
            val responseData = response.body() ?: return
            val accessToken = responseData.data.accessToken
            val refreshToken = responseData.data.refreshToken
//            saveToken(accessToken, refreshToken)
            gotoMapPage(accessToken, refreshToken)
        }
    }

    private fun saveToken(accessToken: String, refreshToken: String) {

        val task = Runnable {
            val result = dbAdapter.tokenDao().getToken(1)
            Log.e("Nopy", result.toString())

            if (result == null) {
                val token = TokenEntity(1, accessToken, refreshToken)
                dbAdapter.tokenDao().insert(token)
            } else {
                val token = TokenEntity(1, accessToken, refreshToken)
                dbAdapter.tokenDao().update(token)
            }
        }
        thread.postTask(task)
    }

    private fun gotoMapPage(accessToken: String, refreshToken: String) {
        val intent = Intent(this@LoadingMapActivity, MapsActivity::class.java)
        intent.putExtra("accessToken", accessToken)
        intent.putExtra("refreshToken", refreshToken)
        startActivity(intent)
    }

    private fun setupDatabase() {
        dbAdapter = AppDatabase.getInstance(this).also {
            // Instance does not create the database.
            // It will do so once call writableDatabase or readableDatabase
            it.openHelper.readableDatabase
        }
    }
}

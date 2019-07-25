package com.nopyjf.projectscbexplorer.fragment


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.nopyjf.projectscbexplorer.R
import com.nopyjf.projectscbexplorer.activity.MainActivity
import com.nopyjf.projectscbexplorer.service.ApiManager
import com.nopyjf.projectscbexplorer.vo.OathTokenBody
import com.nopyjf.projectscbexplorer.vo.OauthToken
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainLoadingFragment(private var authCode: String) : Fragment() {

    private val oauthTokenCallback = object : Callback<OauthToken> {
        override fun onFailure(call: Call<OauthToken>, t: Throwable) {
            Log.e("networking", "Can not call token", t)
        }

        override fun onResponse(call: Call<OauthToken>, response: Response<OauthToken>) {
            val responseData = response.body() ?: return
            gotoMainShowPage(MainShowFragment(responseData.data.refreshToken))
        }
    }

    private fun gotoMainShowPage(mainShowFragment: Fragment) {
        (activity as MainActivity).replaceFragment(mainShowFragment)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_main_loading, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getToken(this.authCode)
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
}

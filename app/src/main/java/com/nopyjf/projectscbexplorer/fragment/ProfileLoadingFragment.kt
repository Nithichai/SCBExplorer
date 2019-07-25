package com.nopyjf.projectscbexplorer.fragment


import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.nopyjf.projectscbexplorer.R
import com.nopyjf.projectscbexplorer.activity.ProfileActivity
import com.nopyjf.projectscbexplorer.fragment.ProfileShowFragment
import com.nopyjf.projectscbexplorer.service.ApiManager
import com.nopyjf.projectscbexplorer.vo.CustomerProfile
import com.nopyjf.projectscbexplorer.vo.OathRefreshTokenBody
import com.nopyjf.projectscbexplorer.vo.OauthToken
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ProfileLoadingFragment(private val refreshToken: String) : Fragment() {

    private val oauthRefreshTokenCallback = object : Callback<OauthToken> {
        override fun onFailure(call: Call<OauthToken>, t: Throwable) {
            Log.e("networking", "Can not call token", t)
        }

        override fun onResponse(call: Call<OauthToken>, response: Response<OauthToken>) {
            val resourceOwnerId = response.headers().get("resourceOwnerId") ?: return
            val responseData = response.body() ?: return
            saveToken(responseData.data.refreshToken)
            getProfile(resourceOwnerId, responseData.data.accessToken)
        }
    }

    private val customerProfileCallback = object : Callback<CustomerProfile> {
        override fun onFailure(call: Call<CustomerProfile>, t: Throwable) {

            Log.e("networking", "Can not call token", t)
        }

        override fun onResponse(call: Call<CustomerProfile>, response: Response<CustomerProfile>) {
            val responseData = response.body() ?: return
            gotoProfileShowPage(ProfileShowFragment(responseData))
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile_loading, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getRefreshToken(this.refreshToken)
    }

    private fun getRefreshToken(refreshToken: String) {
        OathRefreshTokenBody(
            "l7b6a7f49e5b3849bcb6d08e2e46c943c8",
            "dbae3c0665fd4f25aa2043ca1fdcb1e1",
            refreshToken
        ).apply {
            ApiManager.oauthV1Service.refreshToken(this).enqueue(oauthRefreshTokenCallback)
        }
    }

    private fun saveToken(refreshToken: String) {
        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return
        with(sharedPref.edit()) {
            putString("refreshToken", refreshToken)
            apply()
        }
    }

    private fun getProfile(resourceOwnerId: String, accessToken: String) {
        ApiManager.customerService.profile(
            "Bearer $accessToken",
            resourceOwnerId
        ).enqueue(customerProfileCallback)
    }

    private fun gotoProfileShowPage(profileShowFragment: ProfileShowFragment) {
        (activity as ProfileActivity).replaceFragment(profileShowFragment)
    }
}

package com.nopyjf.projectscbexplorer.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.nopyjf.projectscbexplorer.R
import com.nopyjf.projectscbexplorer.fragment.ProfileLoadingFragment

class ProfileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        val refreshToken = intent.getStringExtra("refreshToken") ?: return
        checkRefreshToken(refreshToken)
    }

    private fun checkRefreshToken(refreshToken: String) {
        showProfileLoading(refreshToken)
    }

    private fun showProfileLoading(refreshToken: String) {
        val currentFragment = supportFragmentManager.findFragmentById(R.id.profile_container)
        if (currentFragment is ProfileLoadingFragment) return
        replaceFragment(ProfileLoadingFragment(refreshToken))
    }

    fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.profile_container, fragment).commit()
    }
}

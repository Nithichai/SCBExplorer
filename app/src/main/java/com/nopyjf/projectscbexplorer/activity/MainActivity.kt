package com.nopyjf.projectscbexplorer.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.nopyjf.projectscbexplorer.R
import com.nopyjf.projectscbexplorer.fragment.MainLoadingFragment


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        getAuthCode().apply {
            showMainLoading(this)
        }
    }

    private fun getAuthCode(): String {
        val uri = intent.data ?: return ""
        return uri.getQueryParameter("code")!!
    }

    private fun showMainLoading(authCode: String) {
        val currentFragment = supportFragmentManager.findFragmentById(R.id.main_container)
        if (currentFragment is MainLoadingFragment) return
        replaceFragment(MainLoadingFragment(authCode))
    }

    fun replaceFragment(fragmentClass: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.main_container, fragmentClass).commit()
    }
}

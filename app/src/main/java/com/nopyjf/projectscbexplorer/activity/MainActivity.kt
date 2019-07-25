package com.nopyjf.projectscbexplorer.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.nopyjf.projectscbexplorer.R
import com.nopyjf.projectscbexplorer.fragment.CouponFragment
import com.nopyjf.projectscbexplorer.fragment.MapFragment
import com.nopyjf.projectscbexplorer.fragment.ProfileFragment

class MainActivity : AppCompatActivity() {

    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_map -> {
                supportFragmentManager.beginTransaction().replace(R.id.main_container, MapFragment()).commit()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_coupon -> {
                supportFragmentManager.beginTransaction().replace(R.id.main_container, CouponFragment()).commit()
                return@OnNavigationItemSelectedListener true
            }
            else -> {
                supportFragmentManager.beginTransaction().replace(R.id.main_container, ProfileFragment()).commit()
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        navView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
    }
}

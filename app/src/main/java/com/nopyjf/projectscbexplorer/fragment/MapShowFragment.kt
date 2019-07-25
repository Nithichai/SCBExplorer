package com.nopyjf.projectscbexplorer.fragment


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.nopyjf.projectscbexplorer.R
import com.nopyjf.projectscbexplorer.activity.ProfileActivity


class MapShowFragment(
    private val accressToken: String,
    private val refreshToken: String
) : Fragment() {

    private lateinit var profileButton: Button

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_main_show, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        profileButton = view.findViewById(R.id.main_btn)
        profileButton.setOnClickListener {
            // TODO put extras
            val intent = Intent(it.context, ProfileActivity::class.java)
            intent.putExtra("refreshToken", this.refreshToken)
            startActivity(intent)
        }
    }
}

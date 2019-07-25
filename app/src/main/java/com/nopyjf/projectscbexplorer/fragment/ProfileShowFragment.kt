package com.nopyjf.projectscbexplorer.fragment


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.nopyjf.projectscbexplorer.R
import com.nopyjf.projectscbexplorer.vo.CustomerProfile


class ProfileShowFragment(private val customerData: CustomerProfile) : Fragment() {

    private lateinit var partnerIDText: TextView
    private lateinit var citizenIDText: TextView
    private lateinit var nameText: TextView
    private lateinit var emailText: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile_show, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        partnerIDText = view.findViewById(R.id.partner_id)
        citizenIDText = view.findViewById(R.id.citizen_id)
        nameText = view.findViewById(R.id.name)
        emailText = view.findViewById(R.id.email)

        partnerIDText.text = customerData.data.profile.partnerID
        citizenIDText.text = customerData.data.profile.citizenID
        nameText.text = "${customerData.data.profile.engFirstName} ${customerData.data.profile.engLastName}"
        emailText.text = customerData.data.profile.email
    }


}

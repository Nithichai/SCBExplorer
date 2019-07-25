package com.nopyjf.projectscbexplorer.fragment


import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.nopyjf.projectscbexplorer.R


class CouponDialogFragment(
    private val promotionName: String,
    private val merchantName: String,
    private val datetitle: String,
    private val expire_timeouttitle: String,
    private val date: String,
    private val expire_timeout: String,
    private val couponDialogQR: String
) : DialogFragment() {

    private lateinit var promotionTextView: TextView
    private lateinit var merchantTextView: TextView
    private lateinit var datetitleTextView: TextView
    private lateinit var expireTimeoutTitleTextView: TextView
    private lateinit var dateTextView: TextView
    private lateinit var expireTimeout: TextView
    private lateinit var qrCodeImage: ImageView
    private lateinit var button: Button



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        promotionTextView = findViewById
        private lateinit var merchantTextView: TextView
        private lateinit var datetitleTextView: TextView
        private lateinit var expireTimeoutTitleTextView: TextView
        private lateinit var dateTextView: TextView
        private lateinit var expireTimeout: TextView
        private lateinit var qrCodeImage: ImageView
        private lateinit var button: Button
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        var _view: View = activity!!.layoutInflater.inflate(R.layout.fragment_coupon_dialog, null)

        button = _view.findViewById(R.id.coupon_dialog_close)

        var alert = AlertDialog.Builder(activity)
        alert.setView(_view)

        this.button!!.setOnClickListener {
            dismiss()
        }

        return alert.create()
    }


}

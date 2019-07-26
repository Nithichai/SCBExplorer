package com.nopyjf.projectscbexplorer.fragment


import android.app.AlertDialog
import android.app.Dialog
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.nopyjf.projectscbexplorer.R


class CouponDialogFragment(
    private val title_name: String,
    private val merchant_name: String,
    private val created_date: String,
    private val expired_date: String,
    private val qrCode: String
) : DialogFragment() {

    private lateinit var promotionTextView: TextView
    private lateinit var merchantTextView: TextView
    private lateinit var dateTextView: TextView
    private lateinit var expireTimeout: TextView
    private lateinit var qrCodeImage: ImageView
    private lateinit var button: Button

    private lateinit var alert: AlertDialog.Builder

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val _view: View = activity!!.layoutInflater.inflate(R.layout.fragment_coupon_dialog, null)

        button = _view.findViewById(R.id.coupon_dialog_close)
        promotionTextView = _view.findViewById(R.id.coupon_dialog_promotion_name)
        merchantTextView = _view.findViewById(R.id.coupon_dialog_merchant_name)
//        dateTextView = _view.findViewById(R.id.coupon_dialog_date)
//        expireTimeout = _view.findViewById(R.id.coupon_dialog_expire_timeout)
        qrCodeImage = _view.findViewById(R.id.coupon_dialog_qr)

        promotionTextView.text = title_name
        merchantTextView.text = merchant_name
//        dateTextView.text = created_date
//        expireTimeout.text = expired_date

        val decodedString = Base64.decode(qrCode, Base64.DEFAULT)
        val decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
        qrCodeImage.setImageBitmap(decodedByte)

        val alert = AlertDialog.Builder(activity)
        alert.setView(_view)
        this.button.setOnClickListener {
            dismiss()
        }
        return alert.create()
    }

}

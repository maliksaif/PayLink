package com.pay.link.presentation.utils

import android.app.Activity
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.widget.TextView
import com.pay.link.R
import javax.inject.Inject


class CustomProgressDialog @Inject constructor(activity: Activity?) {
    private val progressDialog: Dialog?
    private var messageTextView: TextView? = null

    init {
        progressDialog = Dialog(activity!!)
        progressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        progressDialog.setCancelable(false)
        progressDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val view: View = LayoutInflater.from(activity).inflate(R.layout.dialog_progress, null)
        progressDialog.setContentView(view)

        if (progressDialog.window != null) {
            progressDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }

        messageTextView = view.findViewById(R.id.progressMessage);

    }

    fun show(message: String?) {
        message?.let {
            setMessage(it)
        }
        if (progressDialog != null && !progressDialog.isShowing) {
            progressDialog.show()
        }
    }

    fun dismiss() {
        if (progressDialog != null && progressDialog.isShowing) {
            progressDialog.dismiss()
        }
    }

    fun setMessage(message: String) {
        messageTextView?.text = message
    }

    fun show(isLoading : Boolean,message: String = "Loading..."){
        if(isLoading)
            show(message)
        else
            dismiss()
    }

}

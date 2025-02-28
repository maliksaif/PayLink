package com.pay.link.presentation.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.Window
import android.widget.TextView
import com.pay.link.R
import javax.inject.Inject


class CustomProgressDialog @Inject constructor(activity: Activity?) {
    @SuppressLint("InflateParams")
    private val progressDialog: Dialog = Dialog(requireNotNull(activity)).apply {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setCancelable(false)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        setContentView(LayoutInflater.from(activity).inflate(R.layout.dialog_progress, null).also { view ->
            messageTextView = view.findViewById(R.id.progressMessage)
        })
    }

    private var messageTextView: TextView? = null

    fun show(isLoading: Boolean, message: String = "Loading...") {
        if (isLoading) {
            setMessage(message)
            if (!progressDialog.isShowing) {
                progressDialog.show()
            }
        } else {
            dismiss()
        }
    }

    private fun dismiss() {
        if (progressDialog.isShowing) {
            progressDialog.dismiss()
        }
    }

    private fun setMessage(message: String) {
        messageTextView?.text = message
    }
}


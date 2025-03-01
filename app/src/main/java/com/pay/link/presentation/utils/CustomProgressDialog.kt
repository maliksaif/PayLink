package com.pay.link.presentation.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.Window
import android.widget.TextView
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.pay.link.R
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CustomProgressDialog @Inject constructor() : DefaultLifecycleObserver {

    private var progressDialog: Dialog? = null
    private var messageTextView: TextView? = null

    fun attachToLifecycle(owner: LifecycleOwner, activity: Activity) {
        owner.lifecycle.addObserver(this)
        progressDialog = createDialog(activity)
    }

    private fun createDialog(activity: Activity): Dialog {
        return Dialog(activity).apply {
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            setCancelable(false)
            window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            setContentView(LayoutInflater.from(activity).inflate(R.layout.dialog_progress, null).also { view ->
                messageTextView = view.findViewById(R.id.progressMessage)
            })

            window?.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        }
    }

    fun show(isLoading: Boolean, message: String = "Loading...") {
        if (isLoading) {
            if (progressDialog?.isShowing == false) {
                setMessage(message)
                progressDialog?.show()
            }
        } else {
            dismiss()
        }
    }

    private fun setMessage(message: String) {
        messageTextView?.text = message
    }

    private fun dismiss() {
        progressDialog?.takeIf { it.isShowing }?.dismiss()
        progressDialog = null
    }

    override fun onDestroy(owner: LifecycleOwner) {
        dismiss()
    }
}



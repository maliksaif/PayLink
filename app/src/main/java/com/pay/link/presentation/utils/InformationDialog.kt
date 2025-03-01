package com.pay.link.presentation.utils

import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import com.pay.link.R
import com.pay.link.databinding.DialogGenericBinding

import dagger.hilt.android.qualifiers.ActivityContext
import javax.inject.Inject

class InformationDialog @Inject constructor(@ActivityContext private val context: Context) {

    interface DialogCallback {
        /** Called when the positive button is clicked. */
        fun onPositiveClick() {}

        /** Called when the negative button is clicked. */
        fun onNegativeClick() {}

    }

    private var dialog: Dialog? = null

    /**
     * Displays the dialog.
     *
     * @param title The title of the dialog.
     * @param subTitle The subtitle or message body.
     * @param positiveButtonText The text for the positive button.
     * @param negativeButtonText The text for the negative button.
     * @param lifecycleOwner The lifecycle owner to tie dialog dismissal.
     * @param callback The dialog callback for button actions.
     */
    fun showDialog(
        title: String,
        subTitle: String,
        positiveButtonText: String,
        negativeButtonText: String? = null,
        showNegativeButton : Boolean = false,
        lifecycleOwner: LifecycleOwner,
        callback: DialogCallback,
        textAlignment: Int = View.TEXT_ALIGNMENT_CENTER
    ) {
        if (dialog?.isShowing == true) return
        val binding = DialogGenericBinding.inflate(LayoutInflater.from(context))

        binding.apply {
            titleTextView.text = title
            subTitleTextView.textAlignment = textAlignment
            subTitleTextView.text = subTitle
            positiveActionButton.text = positiveButtonText
            negativeActionButton.text = negativeButtonText

            negativeActionButton.isVisible = showNegativeButton
        }

        val alertDialog = AlertDialog.Builder(context).apply {
            setView(binding.root)
            setCancelable(false)
        }.create().apply {
            window?.setBackgroundDrawableResource(R.drawable.rounded_background)
        }

        binding.positiveActionButton.setOnClickListener {
            callback.onPositiveClick()
            alertDialog.dismiss()
        }

        binding.negativeActionButton.setOnClickListener {
            callback.onNegativeClick()
            alertDialog.dismiss()
        }


        handleLifecycle(lifecycleOwner, alertDialog)

        alertDialog.show()
        dialog = alertDialog
    }

    /**
     * Handles lifecycle events to ensure the dialog is dismissed if the lifecycleOwner is destroyed.
     */
    private fun handleLifecycle(lifecycleOwner: LifecycleOwner, dialog: Dialog) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_DESTROY) {
                dialog.dismiss()
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        dialog.setOnDismissListener {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    /** Dismiss the dialog if it is currently shown. */
    fun dismiss() {
        dialog?.dismiss()
    }
}

package com.sample.smsFinder.utils

import android.app.ActionBar
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import com.sample.smsFinder.R
import kotlinx.android.synthetic.main.dialog_add_tag.*

class DialogUtils {
    fun showAlertDialog(context: Context) {
        context.apply {
            val dialog = AlertDialog.Builder(this)
                .setMessage(getString(R.string.msg_enable_contact_permission))
                .setCancelable(false)
                .setPositiveButton(getString(R.string.label_ok)) { _, _ ->
                    openPermissionSettings()
                }.create()
            dialog.show()
        }

    }

    fun setAddTagDialog(context: Context, callbackSuccess: (String,Dialog) -> Unit) {
        val dialog = Dialog(context).apply {
            setContentView(R.layout.dialog_add_tag)
            setCancelable(true)
            window?.setBackgroundDrawableResource(android.R.color.transparent)
            window?.setLayout(
                ActionBar.LayoutParams.MATCH_PARENT,
                ActionBar.LayoutParams.WRAP_CONTENT
            )
            this.buttonOk.setOnClickListener {
                if (this.editTextAddTAG.text.toString().isNotEmpty()) {
                    callbackSuccess.invoke(editTextAddTAG.text.toString(),this)
                }
            }
        }
        dialog.show()
    }
}
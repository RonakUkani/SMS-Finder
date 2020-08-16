package com.sample.smsFinder.activity

import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.sample.smsFinder.R
import com.sample.smsFinder.SMSFinder
import com.sample.smsFinder.utils.Constants
import com.sample.smsFinder.utils.DialogUtils
import kotlinx.android.synthetic.main.activity_transactions_detail.*
import java.lang.Exception


@Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS",
    "NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS"
)
class TransactionsDetailActivity : AppCompatActivity() {
    var index : Int? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transactions_detail)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        textViewAddTag.setOnClickListener(this::onClick)
        setData()
    }

    private fun onClick(view: View){
        when(view.id){
            R.id.textViewAddTag->{
              showADDTAGDialog()
            }
        }
    }

    /**
     * Here open dialog and Insert and Update Data to SMSFinder.messageList
     */
    private fun showADDTAGDialog() {
        DialogUtils().setAddTagDialog(this){text,dialog->
            SMSFinder.messageList[index!!].TAG = text
            dialog.dismiss()
            setTag()

        }
    }

    private fun setData() {
        try {
            this.supportActionBar?.title = intent.getBundleExtra(Constants.KEY_BUNDLE).getString(Constants.KEY_TITLE)
            textViewMessage.movementMethod = ScrollingMovementMethod()
            textViewMessage.text =intent.getBundleExtra(Constants.KEY_BUNDLE).getString(Constants.KEY_MESSAGE)
            index= intent.getBundleExtra(Constants.KEY_BUNDLE).getInt(Constants.KEY_INDEX)
            setTag()

        }catch (e:Exception){
            e.printStackTrace()
        }
    }

    private fun setTag(){
        if (SMSFinder.messageList[index!!].TAG.isNotEmpty()){
            textViewTag.visibility = View.VISIBLE
            textViewTag.text = "TAG : ${SMSFinder.messageList[index!!].TAG}"
        }else{
            textViewTag.visibility = View.GONE
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }


}
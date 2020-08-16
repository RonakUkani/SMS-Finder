package com.sample.smsFinder.activity

import android.Manifest.permission.READ_SMS
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import com.google.gson.Gson
import com.sample.smsFinder.R
import com.sample.smsFinder.SMSFinder
import com.sample.smsFinder.adapter.TransactionSMSAdapter
import com.sample.smsFinder.model.SMS
import com.sample.smsFinder.utils.Constants
import com.sample.smsFinder.utils.DialogUtils
import kotlinx.android.synthetic.main.activity_transcations.*
import java.util.*


class TransactionsActivity : AppCompatActivity() {
    private var messageList: MutableList<SMS> = mutableListOf()
    private val PERMISSION_REQUEST_CODE = 200
    private var permissionFlag = false
    private var adapter: TransactionSMSAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transcations)
        this.supportActionBar?.title = getString(R.string.label_transcaction_sms)
        textViewShowPieChart.setOnClickListener(this::onClick)
        search()
        init()
    }

    /**
     * Here checkPermission if true then fetch sms from device.otherwise it's take runtime permission
     */
    private fun init() {
        if (checkPermission())
            readSms()
        else
            requestPermission()
    }

    /**
     * Here Search TAG
     */
    private fun search() {
        editTextSearch.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(p0: Editable?) {

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                adapter?.list?.clear()
                for (item in SMSFinder.messageList) {
                    if (item.TAG.contains(p0.toString()) || item.TAG.toLowerCase()
                            .contains(p0.toString().toLowerCase())
                    ) {
                        adapter?.list?.add(item)
                    }
                }
                adapter?.notifyDataSetChanged()
            }
        })
    }

    private fun onClick(view: View) {
        when (view.id) {
            R.id.textViewShowPieChart -> {
                if (messageList.isNotEmpty()) {
                    startActivity(
                        Intent(this@TransactionsActivity, ChartActivity::class.java)
                            .putParcelableArrayListExtra(Constants.KEY_DATA, ArrayList(messageList))
                    )
                }
            }
        }
    }

    private fun checkPermission(): Boolean {
        val result = ContextCompat.checkSelfPermission(applicationContext, READ_SMS)
        return result == PackageManager.PERMISSION_GRANTED
    }

    private fun requestPermission() {
        ActivityCompat.requestPermissions(this, arrayOf(READ_SMS), PERMISSION_REQUEST_CODE)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            PERMISSION_REQUEST_CODE -> {
                when {
                    grantResults[0] == PackageManager.PERMISSION_GRANTED -> {
                        permissionFlag = false
                        readSms()
                    }
                    grantResults[0] == PackageManager.PERMISSION_DENIED -> {
                        DialogUtils().showAlertDialog(this)
                    }
                    else -> {

                    }
                }
            }
        }
    }


    private fun readSms() {
        SMSFinder.messageList.clear()
        messageList.clear()
        val uri: Uri = Uri.parse("content://sms/inbox")
        val c: Cursor? = contentResolver.query(uri, null, null, null, null)

        /**
         * Read the sms data.
         * Here data in start from 0 and when it's insert then it's ++ for unique id perpose
         */
        var data = 0
        if (c!!.moveToFirst()) {
            for (i in 0 until c.count) {
                val title: String = c.getString(c.getColumnIndexOrThrow("address")).toString()
                val message: String = c.getString(c.getColumnIndexOrThrow("body")).toString()

                /**
                 * Here Check Message contains and add data to list
                 */
                if (message.contains(Constants.KEYWORD_DEBITED) ||
                    message.contains(Constants.KEYWORD_DEBIT) ||
                    message.contains(Constants.KEYWORD_PURCHASING) ||
                    message.contains(Constants.KEYWORD_PURCHASE) ||
                    message.contains(Constants.KEYWORD_CREDITED) ||
                    message.contains(Constants.KEYWORD_CREDIT)
                ) {
                    SMSFinder.messageList.add(SMS(data, title, message))
                    data++
                }
                c.moveToNext()
            }
        }
        c.close()
        messageList.addAll(SMSFinder.messageList)
        Log.e("LIST ", Gson().toJson(messageList))
        setAdapter()
    }

    private fun setAdapter() {
        adapter = TransactionSMSAdapter(messageList) { index, it ->
            startActivity(
                Intent(this@TransactionsActivity, TransactionsDetailActivity::class.java).putExtra(
                    Constants.KEY_BUNDLE,
                    bundleOf(
                        Constants.KEY_TITLE to it.title, Constants.KEY_MESSAGE to
                                it.message, Constants.KEY_INDEX to it.id
                    )
                )
            )
        }
        recyclerview.adapter = adapter
    }


}
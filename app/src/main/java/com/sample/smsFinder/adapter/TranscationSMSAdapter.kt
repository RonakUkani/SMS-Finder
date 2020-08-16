package com.sample.smsFinder.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sample.smsFinder.R
import com.sample.smsFinder.model.SMS
import kotlinx.android.synthetic.main.row_transaction_sms.view.*

class TransactionSMSAdapter(
    val list: MutableList<SMS>,var callback : (Int,SMS) ->Unit
) : RecyclerView.Adapter<TransactionSMSAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(
            R.layout.row_transaction_sms, parent, false
        )
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.textViewTitle.text = list[position].title
        holder.itemView.textViewMessage.text = list[position].message
        holder.itemView.setOnClickListener{
            callback(position,list[position])
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}
package com.sample.smsFinder.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SMS(var id:Int,var title: String, var message: String,var TAG :String= ""):Parcelable
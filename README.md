SMS Finder
======================
This App is fetch Transaction SMS from Device and display. show Transaction Income and Expanse
Data to Pie Chart. user can Create ADD/EDIT TAG for every SMS and also search SMS by TAG.

# 1. TransactionsActivity
This Activity Filter Transaction Related SMS and Display By some Keywords. here User also can
Search SMS by TAG   

# 2. TransactionsDetailActivity 
This Activity Showing Detail of SMS. Here User can ADD/EDIT TAG of SMS

# 3. ChartActivity
This Activity Showing Income and Expanse Data to Pie Char using MP Android Chart

## SMS Model
```Kotlin 
@Parcelize
data class SMS(var id:Int,var title: String, var message: String,var TAG :String= ""):Parcelable
```
id -> is store unique Int id
title -> showing message title
message -> showing message Detail
TAG -> storing the TAG. it's Adding By User

##3rd party Lib.

[Google Gson](https://github.com/google/gson). 
for Convert object to Gson and Json

[MP Andoid Chart](https://github.com/PhilJay/MPAndroidChart). 
showing income expanse data to piechart

[Material Design](https://github.com/material-components/material-components-android)



   
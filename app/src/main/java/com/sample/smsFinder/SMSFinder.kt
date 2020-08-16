package com.sample.smsFinder

import android.app.Application
import com.sample.smsFinder.model.SMS

class SMSFinder : Application() {
    /**
     * This List is create for global data saving purpose.
     * In this list is save global data to saving TAG Data Locally insted of DB.
     * this list intialize every time when launch app
     */
    companion object{
         var messageList: MutableList<SMS> = mutableListOf()
    }
}
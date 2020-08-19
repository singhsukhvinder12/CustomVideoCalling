package com.customvideocalling.application

import android.app.Application
import com.customvideocalling.utils.FontStyle

class MyApplication : Application() {
    private var customFontFamily: FontStyle? = null

    override fun onCreate() {
        super.onCreate()
        instance = this
        customFontFamily = FontStyle.instance
        customFontFamily!!.addFont("regular", "Montserrat-Regular_0.ttf")
        customFontFamily!!.addFont("semibold", "Montserrat-Medium_0.ttf")
        customFontFamily!!.addFont("semibold_italic", "Montserrat-SemiBold_0.ttf")
         //appDatabase = Room.databaseBuilder(MyApplication.instance, AppDatabase::class.java, "database-name").build()
    }


    companion object {
        /**
         * @return ApplicationController singleton instance
         */
        @get:Synchronized
        lateinit var instance: MyApplication

     /*   @get:Synchronized
         var appDatabase: AppDatabase*/
    }
}
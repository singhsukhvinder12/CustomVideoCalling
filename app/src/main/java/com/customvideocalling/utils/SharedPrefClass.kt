package com.customvideocalling.utils

/*
 * Created by admin on 23-01-2018.
 */

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.customvideocalling.constants.GlobalConstants
import java.lang.reflect.Modifier

class SharedPrefClass {
    private val gson: Gson

    init {
        val mBuilder = GsonBuilder()
        mBuilder.excludeFieldsWithModifiers(Modifier.FINAL, Modifier.TRANSIENT, Modifier.STATIC)
        gson = mBuilder.serializeNulls().create()
    }

    fun cleanPref(mContext: Context) {
        val mPreferences = mContext.getSharedPreferences(GlobalConstants.SHARED_PREF, Context.MODE_PRIVATE)
        val settings = mPreferences
        settings.edit().clear().apply()
    }

    fun getPrefValue(mContext: Context, mValueKey: String): Any? {
        val mPreferences = mContext.getSharedPreferences(GlobalConstants.SHARED_PREF, Context.MODE_PRIVATE)
        val mMap = mPreferences.all
        if (!mMap.isEmpty())
            if (mMap.containsKey(mValueKey))
                return mMap[mValueKey]
        return null
    }

    fun putObject(mContext: Context, mObjectKey: String, mObject: Any?) {
        val mPreferences = mContext.getSharedPreferences(GlobalConstants.SHARED_PREF, Context.MODE_PRIVATE)
        val mEditor = mPreferences.edit()
        if (mObject == null || mObjectKey == "") {
            throw IllegalArgumentException("object/key is empty or null")
        }
        if (mObject.javaClass == String::class.java) {
            mEditor.putString(mObjectKey, mObject.toString())

        } else {
            mEditor.putString(mObjectKey, gson.toJson(mObject))

        }
        mEditor.apply()
    }


}

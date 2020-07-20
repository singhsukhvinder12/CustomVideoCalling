package com.secretmenu.roomdatabase

import androidx.room.TypeConverter
import com.google.gson.reflect.TypeToken
import com.google.gson.Gson
import com.secretmenu.model.RideListModel

class DataConverter  {
    @TypeConverter // note this annotation
    fun fromOptionValuesList(optionValues: List<RideListModel.Datum>?): String? {
        if (optionValues == null) {
            return null
        }
        val gson = Gson()
        val type = object : TypeToken<List<RideListModel.Datum>>() {
        }.type
        return gson.toJson(optionValues, type)
    }

    @TypeConverter // note this annotation
    fun toOptionValuesList(optionValuesString: String?): List<RideListModel.Datum>? {
        if (optionValuesString == null) {
            return null
        }
        val gson = Gson()
        val type = object : TypeToken<List<RideListModel.Datum>>() {
        }.type
        return gson.fromJson<List<RideListModel.Datum>>(optionValuesString, type)
    }

}
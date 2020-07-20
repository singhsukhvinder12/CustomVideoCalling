package com.secretmenu.model

import androidx.databinding.BaseObservable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

class RideListModel : BaseObservable() {
    var code: Int? = null
    var message: String? = null
    var data: ArrayList<Datum>? = null


@Entity(tableName = "ridehistory")
data class Datum (
    @PrimaryKey(autoGenerate = true)
    val hid: Int,

    @ColumnInfo(name = "createdAt")
    var createdAt: String? = null,

    @ColumnInfo(name = "scooter_id")
    @SerializedName("scooter_id")
    var scooterId: String? = null

//    @TypeConverters(DataConverter::class)
//    @ColumnInfo(name = "historyList")
//    var historyList: ArrayList<RideListModel.Datum>? = null


)
}







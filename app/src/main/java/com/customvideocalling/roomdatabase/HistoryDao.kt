package com.customvideocalling.roomdatabase

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.customvideocalling.model.RideListModel

@Dao
interface HistoryDao {
    @Query("SELECT * FROM ridehistory")
    fun getAll(): List<RideListModel.Datum>

    @Query("SELECT * FROM ridehistory WHERE scooter_id IN (:scooterIds)")
    fun loadAllByIds(scooterIds: IntArray): List<RideListModel.Datum>

    @Insert
    fun insertAll(vararg history: RideListModel.Datum)

    @Delete
    fun delete(history: RideListModel.Datum)

    @Query("DELETE FROM ridehistory")
    fun nukeTable()
}
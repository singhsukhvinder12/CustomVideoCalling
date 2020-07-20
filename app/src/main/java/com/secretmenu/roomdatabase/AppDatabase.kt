package com.secretmenu.roomdatabase

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.secretmenu.model.RideListModel

@Database(entities = [User::class, RideListModel.Datum::class], version = 1)
@TypeConverters(DataConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun historyDao(): HistoryDao

}
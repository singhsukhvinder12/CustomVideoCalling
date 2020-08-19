package com.customvideocalling.roomdatabase

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserDao {
    @Query("SELECT * FROM user")
    fun getAll(): List<User>

    @Query("SELECT * FROM user WHERE uid IN (:userIds)")
    fun loadAllByIds(userIds: IntArray): List<User>

    @Query("SELECT * FROM user WHERE first_name LIKE :first AND " +
            "last_name LIKE :last LIMIT 1")
    fun findByName(first: String, last: String): User

    @Query("SELECT * FROM user ORDER BY uid DESC  LIMIT 1")
    fun findLatestEntry (): User

    @Query("SELECT  * FROM user WHERE sync_status=0")
    fun findOffline(): List<User>


    @Query("UPDATE user SET sync_status=:status WHERE uid = :id")
    fun updateStatus(status:Int , id:Int)


    @Insert
    fun insertAll(vararg users: User)

    @Delete
    fun delete(user: User)

    @Query("DELETE FROM user")
    fun nukeTable()
}
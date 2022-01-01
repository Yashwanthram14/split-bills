package com.example.splitbill.room_db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.splitbill.room_db.entity.User

@Dao
interface UserDao {

    @Insert
    suspend fun insert(user: User?)

    @Delete
    suspend fun delete(delete: User?)

    @Query(
        "SELECT * FROM user " +
                "WHERE group_id= :group_id " +
                "ORDER BY date_created"
    )
    fun getAllUserByGroupId(group_id: Int): LiveData<List<User>>?
}
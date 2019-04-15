package com.florianschoeberl.hiringfs.model

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ClubDao {

    @Insert
    fun insert(club: Club)

    @Query("DELETE FROM club")
    fun deleteAll()

    @Query("SELECT * FROM club" )
    fun getAll() : LiveData<List<Club>>

}
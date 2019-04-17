package com.florianschoeberl.hiringfs.model

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction

@Dao
interface ClubDao {

    @Transaction
    fun replaceAll(clubs: List<Club>) {
        deleteAll()
        insertAll(clubs)
    }

    @Insert
    fun insertAll(clubs: List<Club>)

    @Query("DELETE FROM club")
    fun deleteAll()

    @Query("SELECT * FROM club order by name asc" )
    fun getAllAsc() : LiveData<List<Club>>

    @Query("SELECT * FROM club order by value desc" )
    fun getAllDesc() : LiveData<List<Club>>

}
package com.florianschoeberl.hiringfs.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = arrayOf(Club::class), version = 1)
abstract class ClubDB : RoomDatabase() {

    abstract fun clubDao(): ClubDao

    companion object {
        @Volatile
        private var INSTANCE: ClubDB? = null

        fun getDatabase(context: Context): ClubDB {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                        context.applicationContext,
                        ClubDB::class.java,
                        "Word_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}
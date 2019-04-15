package com.florianschoeberl.hiringfs.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "club")
data class Club(@PrimaryKey(autoGenerate = true) val id: Int,

                @ColumnInfo(name = "name") val name: String,

                @ColumnInfo(name = "country") val country: String,

                @ColumnInfo(name = "value") val value: Long,

                @ColumnInfo(name = "image") val image: String)
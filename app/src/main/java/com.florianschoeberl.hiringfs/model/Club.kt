package com.florianschoeberl.hiringfs.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "club")
data class Club(@PrimaryKey(autoGenerate = true) val id: Int = 0,

                @ColumnInfo(name = "name") val name: String,

                @ColumnInfo(name = "country") val country: String,

                @ColumnInfo(name = "value") val value: Long,

                @ColumnInfo(name = "image") val image: String) : Parcelable
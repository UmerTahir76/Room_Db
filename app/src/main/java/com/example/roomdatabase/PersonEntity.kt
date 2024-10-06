package com.example.roomdatabase

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "person_table")
data class PersonEntity(
    @PrimaryKey(autoGenerate = true) val pid : Int,
    @ColumnInfo("person_name") val name : String,
    @ColumnInfo("person_age") val age : Int,
    @ColumnInfo("person_city") val city : String
) : Parcelable

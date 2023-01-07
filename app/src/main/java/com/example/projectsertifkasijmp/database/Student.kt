package com.example.projectsertifkasijmp.database

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "Student")
@Parcelize
data class Student(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0,

    @ColumnInfo(name = "name")
    var name: String? = null,

    @ColumnInfo(name = "address")
    var address: String? = null,

    @ColumnInfo(name = "phone")
    var phone: String? = null,

    @ColumnInfo(name = "gender")
    var gender: String? = null,

    @ColumnInfo(name = "location")
    var location: String? = null,

    @ColumnInfo(name = "image")
    var image: String? = null,

    @ColumnInfo(name = "date")
    var date: String? = null,
) : Parcelable

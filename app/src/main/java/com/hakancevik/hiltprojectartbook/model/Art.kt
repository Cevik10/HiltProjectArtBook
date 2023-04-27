package com.hakancevik.hiltprojectartbook.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.Year
import java.time.ZoneId

@Entity("arts")
data class Art(
    var name: String,
    var artistName: String,
    var year: Int,
    var artUrl: String,
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null
)
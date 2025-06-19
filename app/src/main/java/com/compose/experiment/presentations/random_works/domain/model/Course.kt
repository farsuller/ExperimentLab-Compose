package com.compose.experiment.presentations.random_works.domain.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "course")
@Parcelize
data class Course(

    @PrimaryKey
    val id : Int? = null,
    val name : String? = null
) : Parcelable

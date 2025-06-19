package com.compose.experiment.presentations.random_works.domain.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "student")
@Parcelize
data class Student(

    @PrimaryKey
    val id : Int? = null,
    val firstName : String? = null,
    val lastName : String? = null,
    val courseTaken : Course? = null,
) : Parcelable

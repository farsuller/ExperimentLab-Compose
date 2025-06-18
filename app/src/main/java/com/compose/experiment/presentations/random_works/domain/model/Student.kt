package com.compose.experiment.presentations.random_works.domain.model

data class Student(
    val id : Int? = null,
    val firstName : String? = null,
    val lastName : String? = null,
    val courseTaken : Course? = null,
)

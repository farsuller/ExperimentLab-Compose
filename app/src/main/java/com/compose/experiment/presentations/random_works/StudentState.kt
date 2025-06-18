package com.compose.experiment.presentations.random_works

import com.compose.experiment.presentations.random_works.domain.model.Course
import com.compose.experiment.presentations.random_works.domain.model.Student

data class StudentState (
    val studentList : List<Student>? = null,
    val coursesList : List<Course>? = null,
    val isLoading : Boolean = true
)
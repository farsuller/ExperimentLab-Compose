package com.compose.experiment.presentations.random_works.domain.repository

import com.compose.experiment.presentations.random_works.domain.model.Course
import com.compose.experiment.presentations.random_works.domain.model.Student

interface StudentRepository {

    suspend fun getStudents() : List<Student>?
    suspend fun getCourses() : List<Course>?

    suspend fun addStudent(student: Student)
    suspend fun addCourse(course: Course)

    suspend fun updateStudentCourse(student: Student)
}
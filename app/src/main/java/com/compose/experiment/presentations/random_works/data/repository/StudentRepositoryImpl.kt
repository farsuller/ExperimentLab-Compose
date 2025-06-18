package com.compose.experiment.presentations.random_works.data.repository

import com.compose.experiment.presentations.random_works.domain.model.Course
import com.compose.experiment.presentations.random_works.domain.model.Student
import com.compose.experiment.presentations.random_works.domain.repository.StudentRepository

class StudentRepositoryImpl : StudentRepository {

    val students : MutableList<Student> = mutableListOf()
    val courses : MutableList<Course> = mutableListOf()

    override suspend fun getStudents(): List<Student>? = students.toList()

    override suspend fun getCourses(): List<Course>? = courses.toList()

    override suspend fun addStudent(student: Student) {
        students.add(student)
    }

    override suspend fun addCourse(course: Course) {
        courses.add(course)
    }

    override suspend fun updateStudentCourse(student: Student) {
        val index = students.indexOfFirst { it.id == student.id }
        if (index != -1){
            students[index] = student
        }
    }
}
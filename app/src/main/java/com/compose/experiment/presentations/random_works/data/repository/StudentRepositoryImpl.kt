package com.compose.experiment.presentations.random_works.data.repository

import com.compose.experiment.presentations.random_works.data.local.CourseDao
import com.compose.experiment.presentations.random_works.data.local.StudentDao
import com.compose.experiment.presentations.random_works.domain.model.Course
import com.compose.experiment.presentations.random_works.domain.model.Student
import com.compose.experiment.presentations.random_works.domain.repository.StudentRepository
import javax.inject.Inject

class StudentRepositoryImpl @Inject constructor(
    private val studentDao: StudentDao,
    private val courseDao: CourseDao
): StudentRepository {

    override suspend fun getStudents(): List<Student>? = studentDao.getStudents()

    override suspend fun getCourses(): List<Course>? = courseDao.getCourses()

    override suspend fun addStudent(student: Student) {
        studentDao.insertStudent(student)
    }

    override suspend fun addCourse(course: Course) {
        courseDao.insertCourse(course)
    }

    override suspend fun updateStudentCourse(student: Student) {
        studentDao.updateStudentCourseById(student.id, student.courseTaken)
    }
}
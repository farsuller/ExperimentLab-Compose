package com.compose.experiment.presentations.random_works.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.compose.experiment.presentations.random_works.domain.model.Course
import com.compose.experiment.presentations.random_works.domain.model.Student

@Dao
interface StudentDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStudent(student: Student)

    @Query("SELECT * FROM student")
    fun getStudents(): List<Student>

    @Query("UPDATE student SET courseTaken = :course WHERE id = :id")
    suspend fun updateStudentCourseById(id: Int?, course: Course?)
}
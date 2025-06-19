package com.compose.experiment.presentations.random_works.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.compose.experiment.presentations.random_works.domain.model.Course
import com.compose.experiment.presentations.random_works.domain.model.Student

@Database(entities = [Student::class, Course::class], version = 1)
@TypeConverters(CourseConverter::class)
abstract class StudentDatabase : RoomDatabase() {

    abstract val studentDao: StudentDao
    abstract val courseDao: CourseDao
}
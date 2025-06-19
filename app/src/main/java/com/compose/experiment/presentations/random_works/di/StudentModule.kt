package com.compose.experiment.presentations.random_works.di

import android.content.Context
import androidx.room.Room
import com.compose.experiment.presentations.random_works.data.local.CourseConverter
import com.compose.experiment.presentations.random_works.data.local.StudentDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object StudentModule {

    @Singleton
    @Provides
    fun provideStudentDatabase(@ApplicationContext context: Context) : StudentDatabase {
        return Room.databaseBuilder(
                context = context,
                klass = StudentDatabase::class.java,
                name = "student_db"
            ).addTypeConverter(CourseConverter())
            .fallbackToDestructiveMigration(false).build()
    }

    @Provides
    @Singleton
    fun provideStudentDao(studentDatabase: StudentDatabase) = studentDatabase.studentDao

    @Provides
    @Singleton
    fun provideCourseDao(studentDatabase: StudentDatabase) = studentDatabase.courseDao


}
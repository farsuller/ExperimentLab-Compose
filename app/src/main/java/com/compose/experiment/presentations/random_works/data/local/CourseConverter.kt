package com.compose.experiment.presentations.random_works.data.local

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.compose.experiment.presentations.random_works.domain.model.Course
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

@ProvidedTypeConverter
class CourseConverter {

    private val gson = Gson()

    @TypeConverter
    fun fromCourse(course: Course): String {
        return gson.toJson(course)
    }

    @TypeConverter
    fun toCourse(data: String?): Course? {
        if (data.isNullOrEmpty()) return null
        val type = object : TypeToken<Course>() {}.type
        return gson.fromJson(data, type)
    }


}
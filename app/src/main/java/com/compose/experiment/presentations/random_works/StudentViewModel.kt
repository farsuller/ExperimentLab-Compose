package com.compose.experiment.presentations.random_works

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.compose.experiment.presentations.random_works.domain.model.Course
import com.compose.experiment.presentations.random_works.domain.model.Student
import com.compose.experiment.presentations.random_works.domain.repository.StudentRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StudentViewModel @Inject constructor(
    private val repository: StudentRepository
) : ViewModel() {

    private val _studentsState = MutableStateFlow(StudentState())
    val studentsState : StateFlow<StudentState> = _studentsState.asStateFlow()

    init {
        getStudents()
        getCourses()
    }

    private fun getStudents(){
        viewModelScope.launch(Dispatchers.IO) {
            _studentsState.update {
                it.copy(
                    isLoading = false,
                    studentList = repository.getStudents().also { println("getStudents $it") }
                )
            }
        }
    }

    private fun getCourses(){
        viewModelScope.launch(Dispatchers.IO) {
            _studentsState.update {
                it.copy(
                    isLoading = false,
                    coursesList = repository.getCourses()
                )
            }
        }
    }

    fun addStudent(student: Student){
        viewModelScope.launch(Dispatchers.IO) {
            repository.addStudent(student)
            getStudents()
        }
    }

    fun addCourse(course: Course){
        viewModelScope.launch(Dispatchers.IO) {
            repository.addCourse(course)
            getCourses()
            getStudents()
        }
    }

    fun enrollCourse(student: Student){
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateStudentCourse(student).also { println("updateStudentCourse $it") }
            getStudents()
        }
    }

}
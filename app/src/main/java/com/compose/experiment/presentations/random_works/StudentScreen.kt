package com.compose.experiment.presentations.random_works

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.compose.experiment.presentations.random_works.domain.model.Course
import com.compose.experiment.presentations.random_works.domain.model.Student
import kotlin.random.Random

@Composable
fun StudentScreen(){

    val viewModel : StudentViewModel = hiltViewModel()
    val studentState by viewModel.studentsState.collectAsState()

    var expanded by remember { mutableStateOf(false) }
    var courseSelected by remember { mutableStateOf<Course>(Course(id = 0, name = "")) }

    var courses = "Courses"
    var selectedIndex = -1

    Scaffold(

    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)) {
            StudentTextField(onCLickAddStudent = viewModel::addStudent)

            Spacer(modifier = Modifier.height(8.dp))

            CourseTextField(onClickAddCourse = viewModel::addCourse)

            LazyColumn (
                modifier = Modifier.fillMaxSize()
            ) {

                item {
                    if(studentState.isLoading){
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ){
                            CircularProgressIndicator()
                        }
                    }
                    else {
                        if(studentState.studentList.isNullOrEmpty()){
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(200.dp),
                                contentAlignment = Alignment.Center
                            ){
                                Text(text = "No Students")
                            }
                        }
                        else {
                            Column {
                                Text(
                                    text = "Students",
                                    style = TextStyle(fontSize = MaterialTheme.typography.titleLarge.fontSize))

                                studentState.studentList?.forEach { student ->

                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Column {
                                            Text(text = "${student.firstName} ${student.lastName}")
                                            if(student.courseTaken != null){
                                                Text(text = "Course Taken: ${student.courseTaken.name}")
                                            } else if(selectedIndex != -1){
                                                Button(
                                                    onClick = {
                                                        viewModel.enrollCourse(
                                                            Student(
                                                                id = student.id,
                                                                firstName = student.firstName,
                                                                lastName = student.lastName,
                                                                courseTaken = courseSelected))
                                                        selectedIndex = -1
                                                        courses = "Courses"

                                                    }) { Text(text = "Enroll") }
                                            }
                                            Spacer(
                                                modifier = Modifier.height(10.dp)
                                            )
                                        }

                                        if(student.courseTaken == null){
                                            Row {
                                                Text(text = courses)
                                                IconButton (
                                                    onClick = { expanded = !expanded }
                                                ) {
                                                    Icon(
                                                        imageVector = Icons.Default.ArrowDropDown,
                                                        contentDescription = "Drop Down"
                                                    )
                                                }
                                                DropdownMenu(
                                                    expanded = expanded,
                                                    onDismissRequest = { expanded = false }
                                                ) {
                                                    studentState.coursesList?.forEachIndexed { index , course ->
                                                        DropdownMenuItem(
                                                            text = { Text(text = "${course.name}") },
                                                            onClick = {
                                                                courseSelected = Course(
                                                                    id = course.id,
                                                                    name = course.name
                                                                )
                                                                courses = course.name.toString()
                                                                expanded = false
                                                                selectedIndex = index
                                                            }
                                                        )
                                                    }
                                                }
                                            }
                                        }

                                    }
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        if(studentState.coursesList.isNullOrEmpty()){
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(200.dp),
                                contentAlignment = Alignment.Center
                            ){
                                Text(text = "No Courses")
                            }
                        } else {
                            Column {
                                Text(
                                    text = "Courses",
                                    style = TextStyle(fontSize = MaterialTheme.typography.titleLarge.fontSize))

                                studentState.coursesList?.forEach { course ->
                                    Text(text = "${course.name}")
                                }
                            }
                        }

                    }
                }
            }
        }
    }
}

@Composable
fun StudentTextField(onCLickAddStudent : (Student) -> Unit){

    var firstName by remember { mutableStateOf("")}
    var lastName by remember { mutableStateOf("")}

    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column {
            TextField(
                value = firstName,
                onValueChange = {
                    firstName = it
                },
                placeholder = {
                    Text(text = "First Name")
                },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                )
            )
            TextField(
                value = lastName,
                onValueChange = {
                    lastName = it
                },
                placeholder = {
                    Text(text = "Last Name")
                },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                )
            )
        }


        Button(
            onClick = {
                onCLickAddStudent(
                    Student(
                        id = Random.nextInt(),
                        firstName = firstName,
                        lastName = lastName
                    )
                )
            }
        ) {
            Text(text = "Add Student")
        }
    }
}

@Composable
fun CourseTextField(onClickAddCourse : (Course) -> Unit) {
    var course by remember { mutableStateOf("")}

    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        TextField(
            value = course,
            onValueChange = {
                course = it
            },
            placeholder = {
                Text(text = "Course")
            },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
            )
        )

        Button(
            onClick = { onClickAddCourse(Course(
                id = Random.nextInt(),
                name = course)) }
        ) {
            Text(text = "Add Course")
        }
    }

}
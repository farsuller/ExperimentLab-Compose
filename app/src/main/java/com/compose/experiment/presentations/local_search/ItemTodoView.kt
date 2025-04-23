//package com.compose.experiment.presentations.local_search
//
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.padding
//import androidx.compose.material3.Checkbox
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//
//@Composable
//fun TodoItemView(
//    todo: Todo,
//    onDoneChange: (Boolean) -> Unit,
//    modifier: Modifier = Modifier
//) {
//    Row(
//        modifier = modifier
//            .fillMaxWidth()
//            .padding(8.dp)
//    ) {
//        Column(
//            modifier = Modifier.weight(1f)
//        ) {
//            Text(
//                text = todo.title,
//                fontSize = 16.sp
//            )
//            Text(
//                text = todo.text,
//                fontSize = 10.sp
//            )
//        }
//        Checkbox(
//            checked = todo.isDone,
//            onCheckedChange = onDoneChange
//        )
//    }
//}

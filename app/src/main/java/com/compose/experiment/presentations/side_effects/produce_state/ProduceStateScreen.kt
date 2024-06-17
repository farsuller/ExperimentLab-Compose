package com.compose.experiment.presentations.side_effects.produce_state

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import kotlin.time.Duration.Companion.seconds

@Composable
fun ProduceStateScreen(){



}

@Composable
fun PostCard(postId : Int){
    val postProduceState by produceState<Result<Post>?>(initialValue = null){
        value = getPostInformation(postId)
    }

    //this postMutableState implementation with LaunchedEffect is just the same how produceState works
        //    var postMutableState by remember {
        //        mutableStateOf<Result<Post>?>(null)
        //    }
        //    LaunchedEffect(postId){
        //        postMutableState = getPostInformation(postId)
        //    }

    if(postProduceState == null){

    }else{
        postProduceState?.onSuccess{

        }?.onFailure{

        }
    }
}

suspend fun getPostInformation(id: Int): Result<Post>{
    return withContext(Dispatchers.IO){
        delay(3.seconds)
        Result.success(Post(
            id = 1,
            name = "Title",
            imageUrl = "Description"
        ))
    }
}

data class Post(val id : Int, val name : String, val imageUrl : String)
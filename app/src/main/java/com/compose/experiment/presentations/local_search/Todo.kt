//package com.compose.experiment.presentations.local_search
//
//import androidx.appsearch.annotation.Document
//import androidx.appsearch.annotation.Document.BooleanProperty
//import androidx.appsearch.annotation.Document.Id
//import androidx.appsearch.annotation.Document.Namespace
//import androidx.appsearch.annotation.Document.Score
//import androidx.appsearch.annotation.Document.StringProperty
//import androidx.appsearch.app.AppSearchSchema
//
//@Document
//data class Todo(
//    // Required field for a document class. All documents MUST have a namespace.
//    @Namespace
//    val namespace: String,
//
//    // Required field for a document class. All documents MUST have an Id.
//    @Id
//    val id: String,
//
//    // Optional field for a document class, used to set the score of the
//    // document. If this is not included in a document class, the score is set
//    // to a default of 0.
//    @Score
//    val score: Int,
//
//    //@StringProperty is required for App Search to work properly and for better indexing performance
//    @StringProperty(indexingType = AppSearchSchema.StringPropertyConfig.INDEXING_TYPE_PREFIXES)
//    val title: String,
//
//    //@StringProperty is required for App Search to work properly and for better indexing performance
//    @StringProperty(indexingType = AppSearchSchema.StringPropertyConfig.INDEXING_TYPE_PREFIXES)
//    val text: String,
//
//    //@BooleanProperty is required for App Search to work properly and for better indexing performance
//    @BooleanProperty
//    val isDone: Boolean
//)

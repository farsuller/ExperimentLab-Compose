package com.compose.experiment.sealed_enum

// in sealed class we deal with instances
// sealed class offer more flexibility than enum class
sealed class HttpError(val code : Int) {
    data object Unauthorized : HttpError(402)
    data object NotFound : HttpError(404)
    data class AuthenticationFailed(val message : String) : HttpError(401)

    fun doSomething() {

    }
}

// in enum class we deal with constants
// with enum class you cant have individual instances for each constant
enum class HttpErrorEnum(val code : Int){
    Unauthorized(402),
    NotFound(404)
}

// sealed interface doesn't have a constructor
sealed interface HttpErrorInterface {
    data object Unauthorized : HttpErrorInterface
    data object NotFound : HttpErrorInterface
    data class AuthenticationFailed(val message : String) : HttpErrorInterface

    fun doSomething(){}
}
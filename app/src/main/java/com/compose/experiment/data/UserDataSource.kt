package com.compose.experiment.data

import com.compose.experiment.model.Menu
import com.compose.experiment.model.Test
import com.compose.experiment.model.User
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.postgrest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UserDataSource @Inject constructor(
    private val supaBaseClient: SupabaseClient
) {

    fun findTest():Flow<ApiResult<List<Test>>>{
        return flow {
            emit(ApiResult.Loading)
            try {
                val result = supaBaseClient.postgrest["test"].select()
                val users = result.decodeList<Test>()
                emit(ApiResult.Success(users))

            } catch (e: Exception) {

                ApiResult.Error(e.message ?: "Unknown error")
            }
        }
    }
    fun findAll():Flow<ApiResult<List<User>>>{
        return flow {
            emit(ApiResult.Loading)
            try {
                val result = supaBaseClient.postgrest["users"].select()
                val users = result.decodeList<User>()
                emit(ApiResult.Success(users))

            } catch (e: Exception) {

                ApiResult.Error(e.message ?: "Unknown error")
            }
        }
    }
    fun menuAll():Flow<ApiResult<List<Menu>>>{
        return flow {
            emit(ApiResult.Loading)
            try {
                val result = supaBaseClient.postgrest["menu"].select()
                val users = result.decodeList<Menu>()
                emit(ApiResult.Success(users))

            } catch (e: Exception) {

                ApiResult.Error(e.message ?: "Unknown error")
            }
        }
    }

    fun insert(user: User): Flow<ApiResult<Unit>>  {
        return flow {
            emit(ApiResult.Loading)
            try {
                supaBaseClient.postgrest["users"].insert(user)
                emit(ApiResult.Success(Unit))

            } catch (e: Exception) {

                ApiResult.Error(e.message ?: "Unknown error")
            }
        }
    }

    fun deleteById(userId: Int): Flow<ApiResult<Unit>> {
        return flow {
            emit(ApiResult.Loading)
            try {
                supaBaseClient.postgrest["users"].delete {
                    filter { eq("id", userId) }
                }
                emit(ApiResult.Success(Unit))

            } catch (e: Exception) {

                ApiResult.Error(e.message ?: "Unknown error")
            }
        }
    }

    fun updateById(userId: Int, user: User): Flow<ApiResult<Unit>> {
        return flow {
            emit(ApiResult.Loading)
            try {
                supaBaseClient.postgrest["users"].update(user) {
                    filter { eq("id", userId) }
                }
                emit(ApiResult.Success(Unit))

            } catch (e: Exception) {

                ApiResult.Error(e.message ?: "Unknown error")
            }
        }
    }
}

sealed class ApiResult<out R> {
    data class Success<out R>(val data: R) : ApiResult<R>()
    data class Error(val message: String) : ApiResult<Nothing>()
    data object Loading : ApiResult<Nothing>()
}
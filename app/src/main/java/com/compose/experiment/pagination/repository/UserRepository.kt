package com.compose.experiment.pagination.repository

import com.compose.experiment.data.ApiResult
import com.compose.experiment.data.UserDataSource
import com.compose.experiment.model.Menu
import com.compose.experiment.model.Test
import com.compose.experiment.model.User
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


interface UserRepository {
    fun findAll(): Flow<ApiResult<List<User>>>
    fun menuAll(): Flow<ApiResult<List<Menu>>>
    fun insert(user: User): Flow<ApiResult<Unit>>
    fun updateById(id: Int, user: User): Flow<ApiResult<Unit>>
    fun deleteById(id: Int): Flow<ApiResult<Unit>>
}

class DefaultUserRepository @Inject constructor(
    private val userDataSource: UserDataSource
) : UserRepository {
    override fun findAll(): Flow<ApiResult<List<User>>> {
        return userDataSource.findAll()
    }

    override fun menuAll(): Flow<ApiResult<List<Menu>>> {
        return userDataSource.menuAll()
    }

    override fun insert(user: User): Flow<ApiResult<Unit>> {
        return userDataSource.insert(user = user)
    }

    override fun updateById(id: Int, user: User): Flow<ApiResult<Unit>> {
        return userDataSource.updateById(userId = id, user = user)
    }

    override fun deleteById(id: Int): Flow<ApiResult<Unit>> {
        return userDataSource.deleteById(userId = id)
    }
}
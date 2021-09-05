package com.example.data

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

suspend fun <T> call(
    dispatcher: CoroutineDispatcher = Dispatchers.IO,
    apiCall: suspend () -> T
): Result<T> = withContext(dispatcher) {
    try {
        Result.success(apiCall.invoke())
    } catch (error: Throwable) {
        Result.failure(error)
    }
}
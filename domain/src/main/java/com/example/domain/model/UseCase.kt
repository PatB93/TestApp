package com.example.domain.model

interface UseCase<T, R> {
    suspend fun execute(params: T): Result<R>
}

suspend operator fun <T, R> UseCase<T, R>.invoke(params: T) = execute(params)
package com.yasinmoridi.miniverse.data.remote.util

import androidx.compose.runtime.Immutable


@Immutable
sealed interface NetworkResult<out T> {

    @Immutable
    data class Success<out T>(
        val data: T,
        val message: String,
        val code: Int = 200
    ) : NetworkResult<T>

    @Immutable
    data class Error(
        val message: String,
        val code: Int? = null,
        val throwable: Throwable? = null
    ) : NetworkResult<Nothing>

    data object Loading : NetworkResult<Nothing>

    data object Idle : NetworkResult<Nothing>
}

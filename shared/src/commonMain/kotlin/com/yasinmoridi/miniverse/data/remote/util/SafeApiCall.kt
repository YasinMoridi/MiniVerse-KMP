package com.yasinmoridi.miniverse.data.remote.util

import com.yasinmoridi.miniverse.data.remote.model.ApiResponse
import com.yasinmoridi.miniverse.data.remote.model.NetworkMessages
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.RedirectResponseException
import io.ktor.client.plugins.ServerResponseException
import kotlinx.io.IOException

/**
 * A wrapper function to execute network requests safely using Ktor.
 */
suspend fun <T> safeApiCall(
    apiCall: suspend () -> ApiResponse<T>
): NetworkResult<T> {
    return try {
        val response = apiCall()
        
        if (response.success && response.data != null) {
            NetworkResult.Success(
                data = response.data,
                message = response.message ?: NetworkMessages.SUCCESS,
                code = 200
            )
        } else {
            NetworkResult.Error(
                message = response.message ?: NetworkMessages.REQUEST_FAILED,
                code = 200
            )
        }
    } catch (e: RedirectResponseException) {
        NetworkResult.Error(
            message = NetworkMessages.httpError(e.response.status.value),
            code = e.response.status.value,
            throwable = e
        )
    } catch (e: ClientRequestException) {
        NetworkResult.Error(
            message = NetworkMessages.httpError(e.response.status.value),
            code = e.response.status.value,
            throwable = e
        )
    } catch (e: ServerResponseException) {
        NetworkResult.Error(
            message = NetworkMessages.httpError(e.response.status.value),
            code = e.response.status.value,
            throwable = e
        )
    } catch (e: IOException) {
        NetworkResult.Error(
            message = NetworkMessages.NETWORK_ERROR,
            throwable = e
        )
    } catch (e: Exception) {
        NetworkResult.Error(
            message = NetworkMessages.UNKNOWN_ERROR,
            throwable = e
        )
    }
}

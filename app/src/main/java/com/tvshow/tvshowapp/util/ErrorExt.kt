package com.tvshow.tvshowapp.util

import androidx.paging.PagingSource
import com.google.gson.JsonParseException
import kotlinx.coroutines.TimeoutCancellationException
import retrofit2.HttpException
import java.io.IOException

data class ErrorResult(
    val message: String,
    val cause: Throwable? = null
)

fun Throwable.toErrorResult(): ErrorResult {
    val message = when (this) {
        is IOException -> "Check Your Internet Connection"
        is HttpException -> when (this.code()) {
            404 -> "Page Not Found...404 Error"
            500 -> "Server error. Please try again later."
            else -> "An unknown HTTP error: ${this.message}"
        }
        is JsonParseException -> "An error occurred while processing data: ${this.message}"
        is TimeoutCancellationException -> "There was no response from the server. Please try again."
        else -> "Something went wrong: ${this.message}"
    }
    return ErrorResult(message, this)
}



private fun <T : Any> Throwable.toLoadResultError(): PagingSource.LoadResult.Error<Int, T> {
    val message = when (this) {
        is IOException -> "Check Your Internet Connection"
        is HttpException -> when (this.code()) {
            404 -> "Page Not Found...404 Error"
            500 -> "Server error. Please try again later."
            else -> "An unknown HTTP error: ${this.message}"
        }
        is JsonParseException -> "An error occurred while processing data: ${this.message}"
        is TimeoutCancellationException -> "There was no response from the server. Please try again."
        else -> "Something went wrong: ${this.message}"
    }
    return PagingSource.LoadResult.Error(Exception(message, this))
}

sealed class UiError {
    data object NetworkError : UiError()
    data class HttpError(val code: Int, val message: String) : UiError()
    data class ParsingError(val message: String) : UiError()
    data class TimeoutError(val message: String) : UiError()
    data class UnknownError(val message: String) : UiError()
}
fun Throwable.toUiError(): UiError {
    return when (this) {
        is IOException -> UiError.NetworkError
        is HttpException -> UiError.HttpError(this.code(), this.message ?: "Bilinmeyen HTTP hatası")
        is JsonParseException -> UiError.ParsingError("Veri işlenirken hata oluştu")
        is TimeoutCancellationException -> UiError.TimeoutError("Sunucudan yanıt alınamadı")
        else -> UiError.UnknownError("Bir şeyler ters gitti: ${this.message}")
    }
}
package com.tvshow.tvshowapp.util

sealed class Response<T>(
    val data: T? = null,
    val error: UIError? = null
) {
    class Success<T>(data: T?=null) : Response<T>(data = data)
    class Error<T>(error: UIError?) : Response<T>(error = error)
    class Loading<T> : Response<T>()
}
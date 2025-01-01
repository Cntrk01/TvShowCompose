package com.tvshow.tvshowapp.common

import com.tvshow.tvshowapp.core.CustomExceptions

sealed class Response<T>(
    val data: T? = null,
    val error: CustomExceptions? = null
) {
    class Success<T>(data: T?=null) : Response<T>(data = data)
    class Error<T>(error: CustomExceptions?) : Response<T>(error = error)
    class Loading<T> : Response<T>()
}
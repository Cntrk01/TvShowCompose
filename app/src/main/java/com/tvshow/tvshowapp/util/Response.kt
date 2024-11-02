package com.tvshow.tvshowapp.util

sealed class Response<T>(val data: T? = null, val message:String? = null, val cause: Throwable? = null) {
    class Success<T>(data: T?=null) : Response<T>(data = data)
    class Error<T>(message: String,cause: Throwable? = null, data:T? = null) : Response<T>(data = data, cause = cause,message=message)
    class Loading<T> : Response<T>()
}
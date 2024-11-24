package com.tvshow.tvshowapp.util

import com.google.gson.JsonParseException
import kotlinx.coroutines.TimeoutCancellationException
import retrofit2.HttpException
import java.io.IOException

//Companion objecte yazdığım bu method ile constructor görevi sağlar.
//UiError consturctoruna message vericektim val message fakat kullanılmayan yerlerde bile gereksiz değer setlemesi yapacağı için bunun yerine class içerisinde ortak olduğu için abstract olarak tanımladım.
//sealed class Tanımlandığı paket dışında bir daha impl edilmesini istemedim.Nesne de oluşturulamaz.Miras edilebilir.
//enummlara benzer ama daha geniş kullanım alanı var.;
//sealed class imp eden tüm classlar compile timda tüm impl tipi biliniyor.
//Genelde alacağım hatalar üzerinde çalışma yaparak tanımlama yaptım.Bilmediğim durum varsa CustomError tanımladım.
sealed class UIError : Exception(){

    private data class HttpError(val code: Int, override val message: String) : UIError()
    private data class NetworkError(override val message: String) : UIError()
    private data class ParsingError(override val message: String) : UIError()
    private data class TimeoutError(override val message: String) : UIError()
    private data class CustomError(override val message: String) : UIError()

    companion object {
        operator fun invoke(throwable: Throwable): UIError {
            return when (throwable) {
                is IOException -> NetworkError("Check Your Internet Connection !")

                is HttpException -> {
                    when (throwable.code()) {
                        404 ->  HttpError(throwable.code(), "Page Not Found...404 Error")
                        500 -> HttpError(throwable.code(), "Server error. Please try again later.")
                        else ->  HttpError(throwable.code(), "An unknown HTTP error")
                    }
                }

                is JsonParseException -> {
                    ParsingError("Json Parse Exception: ${throwable.message}")
                }

                is TimeoutCancellationException -> {
                    TimeoutError("Server Timeout Error")
                }

                else -> CustomError(throwable.message ?: "UnknownError.")
           }
        }

        operator fun invoke(code: Int, message: String) : UIError {
            return HttpError(code, message)
        }

        operator fun invoke(code: Int = 0, message: String = "", throwable: Throwable? = null) : UIError{
            return when (throwable) {
                is IOException -> NetworkError("Check Your Internet Connection !")

                is HttpException -> {
                    when (throwable.code()) {
                        404 ->  HttpError(throwable.code(), "Page Not Found...404 Error")
                        500 -> HttpError(throwable.code(), "Server error. Please try again later.")
                        else ->  HttpError(throwable.code(), "An unknown HTTP error")
                    }
                }

                is JsonParseException -> {
                    ParsingError("Json Parse Exception: ${throwable.message}")
                }

                is TimeoutCancellationException -> {
                    TimeoutError("Server Timeout Error")
                }

                else -> CustomError(throwable?.message ?: "UnknownError.")
            }
        }
    }
}

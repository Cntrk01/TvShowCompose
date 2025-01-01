package com.tvshow.tvshowapp.core

import com.google.gson.JsonParseException
import kotlinx.coroutines.TimeoutCancellationException
import retrofit2.HttpException
import java.io.IOException
import java.util.concurrent.TimeoutException

//Companion objecte yazdığım bu method ile constructor görevi sağlar.
//sealed class Tanımlandığı paket dışında bir daha impl edilmesini istemedim.Nesne de oluşturulamaz.Miras edilebilir.
//enummlara benzer ama daha geniş kullanım alanı var.;
//sealed class imp eden tüm classlar compile timda tüm impl tipi biliniyor.
//Genelde alacağım hatalar üzerinde çalışma yaparak tanımlama yaptım.Bilmediğim durum varsa CustomError tanımladım.
sealed class CustomExceptions(
    open val isShowAction : Boolean = false,
    open val exceptionCode : Int = 0,
) : Exception() {

    private data class HttpError(
        override val exceptionCode: Int,
        override val message: String,
    ) : CustomExceptions(exceptionCode = exceptionCode)

    private data class NetworkError(
        override val message: String,
        override val isShowAction: Boolean
    ) : CustomExceptions(isShowAction = isShowAction)

    private data class ParsingError(
        override val message: String
    ) : CustomExceptions()

    private data class TimeoutError(
        override val message: String,
        override val isShowAction: Boolean
    ) : CustomExceptions(isShowAction = isShowAction)

    private data class CustomError(
        override val message: String
    ) : CustomExceptions()

    companion object {
        operator fun invoke(throwable: Throwable): CustomExceptions {
            return when (throwable) {
                is IOException -> NetworkError(
                    message = "Check Your Internet Connection !",
                    isShowAction = true
                )

                is HttpException -> {
                    when (throwable.code()) {
                        404 ->  HttpError(
                            exceptionCode = throwable.code(),
                            message = throwable.message ?: "Page Not Found...404 Error"
                        )
                        500 -> HttpError(
                            exceptionCode = throwable.code(),
                            message = throwable.message ?: "Server error. Please try again later.")
                        else ->  HttpError(
                            exceptionCode = throwable.code(),
                            message = throwable.message ?:"An unknown HTTP error")
                    }
                }

                is JsonParseException -> {
                    ParsingError(message ="Json Parse Exception: ${throwable.message}")
                }

                is TimeoutException ->{
                    TimeoutError(
                        message = "Server Timeout Error",
                        isShowAction = true
                    )
                }

                //Coroutine'lerin zaman aşımıyla iptal edilmesi durumunda atılır.
                //is TimeoutCancellationException -> {}

                else -> CustomError(message = throwable.message ?: "UnknownError.")
           }
        }

        operator fun invoke(
            code: Int,
            message: String
        ) : CustomExceptions {
            return HttpError(exceptionCode = code, message = message)
        }

        operator fun invoke(customErrorMessage : String) : CustomExceptions {
            return CustomError(message = customErrorMessage)
        }

        operator fun invoke(
            message: String = "",
            throwable: Throwable? = null
        ) : CustomExceptions {
            return when (throwable) {
                is IOException -> NetworkError(
                    message = "Check Your Internet Connection !",
                    isShowAction = true
                )

                is HttpException -> {
                    when (throwable.code()) {
                        404 ->  HttpError(
                            exceptionCode = throwable.code(),
                            message = throwable.message ?: "Page Not Found...404 Error"
                        )
                        500 -> HttpError(
                            exceptionCode = throwable.code(),
                            message = throwable.message ?: "Server error. Please try again later.")
                        else ->  HttpError(
                            exceptionCode = throwable.code(),
                            message = throwable.message ?:"An unknown HTTP error")
                    }
                }

                is JsonParseException -> {
                    ParsingError(message = "Json Parse Exception: ${throwable.message}")
                }

                is TimeoutCancellationException -> {
                    TimeoutError(
                        message = "Server Timeout Error",
                        isShowAction = true
                    )
                }

                else -> CustomError(message = throwable?.message ?: "UnknownError.")
            }
        }
    }
}

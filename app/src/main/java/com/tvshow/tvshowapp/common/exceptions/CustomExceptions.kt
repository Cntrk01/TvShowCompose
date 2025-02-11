package com.tvshow.tvshowapp.common.exceptions

import com.google.gson.JsonParseException
import kotlinx.coroutines.TimeoutCancellationException
import retrofit2.HttpException
import java.io.IOException
import java.util.concurrent.TimeoutException

/**
 * 07.02.2025
 * Companion object içinde tanımlanan bu metot, bir constructor görevi görür.
 *
 * ### `sealed class` Özellikleri:
 * - **Tanımlandığı Paketin Dışında Implement Edilemez**: `sealed class`, yalnızca tanımlandığı paket içinde genişletilebilir.
 * - **Nesne Olarak Oluşturulamaz**: Doğrudan bir örneği oluşturulamaz, ancak alt sınıfları türetilebilir.
 * - **Miras Alınabilir**: Alt sınıflar tarafından genişletilebilir.
 * - **Enum'lara Benzer Ancak Daha Esnek**: `enum` sınıflarına benzese de daha geniş kullanım alanına sahiptir.
 * - **Tüm Implementasyonlar Compile Time'da Bilinir**: `sealed class` implement eden tüm alt sınıflar derleme zamanında bilinir.
 *
 * ### Kullanım Amacı:
 * - Belirli hata türlerini yönetmek için tasarlandı.
 * - Bilinen hata durumları için özel alt sınıflar tanımlandı.
 * - Bilinmeyen hata durumlarını yakalamak için `CustomError` sınıfı oluşturuldu.
 */
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

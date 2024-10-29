package com.tvshow.tvshowapp.util

import com.google.gson.JsonParseException
import kotlinx.coroutines.TimeoutCancellationException
import retrofit2.HttpException
import java.io.IOException

//Companion objecte yazdığım bu method ile constructor görevi gibi çalışıyor.
//UiError consturctoruna message vericektim val message fakat kullanılmayan yerlerde bile gereksiz değer setlemesi yapacağı için bunun yerine class içerisinde ortak olduğu için abstract olarak tanımladım.
//sealed class Tanımlandığı paket dışında bir daha impl edilmesini istemedim.Nesne de oluşturulamaz.Miras edilebilir.
//enummlara benzer ama daha geniş kullanım alanı var.;
//sealed class imp eden tüm classlar compile timda tüm impl tipi biliniyor.
//Genelde alacağım hatalar üzerinde çalışma yaparak tanımlama yaptım.Bilmediğim durum varsa CustomError tanımladım.
sealed class UIError : UiScreenDelegate {
    abstract val message : String

    override fun handle(value: UIError, isShow: Boolean) {
        onHttpError{ message, code ->

        }
    }

    internal data class HttpError(val code: Int, override val message: String) : UIError()
    private data class NetworkError(override val message: String) : UIError()
    private data class ParsingError(override val message: String) : UIError()
    private data class TimeoutError(override val message: String) : UIError()
    private data class CustomError(override val message: String) : UIError()

    companion object {
        operator fun invoke(throwable: Throwable): UIError {
            return when (throwable) {
                is IOException -> NetworkError(throwable.message ?: "")

                is HttpException -> {
                    when (throwable.code()) {
                        404 ->  HttpError(throwable.code(), "Page Not Found...404 Error")
                        500 -> HttpError(throwable.code(), "Server error. Please try again later.")
                        else ->  HttpError(throwable.code(), "An unknown HTTP error")
                    }
                }

                is JsonParseException -> {
                    ParsingError("Veri işlenirken hata oluştu: ${throwable.message}")
                }

                is TimeoutCancellationException -> {
                    TimeoutError("Sunucudan yanıt alınamadı.")
                }

                else -> CustomError(throwable.message ?: "Bilinmeyen bir hata oluştu.")
           }
        }

        operator fun invoke(code: Int, message: String) : UIError {
            return HttpError(code, message)
        }

        operator fun invoke(code: Int = 0, message: String = "", throwable: Throwable? = null) : UIError{
            return when (throwable) {
                is IOException -> NetworkError(throwable.message ?: "")

                is HttpException -> {
                    HttpError(throwable.code(), throwable.message ?: "HTTP Hatası")
                }

                is JsonParseException -> {
                    ParsingError("Veri işlenirken hata oluştu: ${throwable.message}")
                }

                is TimeoutCancellationException -> {
                    TimeoutError("Sunucudan yanıt alınamadı.")
                }

                else -> CustomError(throwable?.message ?: "Bilinmeyen bir hata oluştu.")
            }
        }
    }
}

fun UIError.onHttpError(callback: (String, Int) -> Unit): UIError = apply {
    if (this is UIError.HttpError) {
        callback(message, code)
    }
}

fun interface UiScreenDelegate{
    fun handle(value: UIError,isShow  : Boolean)
}

//class UiScreenDelegateImpl(
//    private val value  : UIError,
//    private val isShow : Boolean,
//) : UiScreenDelegate{
//    override fun handle(value: UIError, isShow: Boolean) {
//        if (isShow){
//            value.onHttpError { message, code ->
//
//            }
//        }
//    }
//}

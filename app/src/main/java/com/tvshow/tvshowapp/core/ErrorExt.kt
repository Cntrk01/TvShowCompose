package com.tvshow.tvshowapp.core

import androidx.paging.LoadState

//val throwable = (loadState.refresh as LoadState.Error).error tip olarak zaten NetworkError geliyor.Ondan dolayı tekrar UIError içerisinde throw vermeye gerek yok.Eğer verirsek custom errora düşüyor.Bundan dolayıda retry gözükmüyuor
//println("Before conversion: ${throwable::class.simpleName}")
//val uiError = UIError(throwable)
//println("After conversion: ${uiError::class.simpleName}, isShowAction: ${uiError.isShowAction}") Bundan dolayı da cast ettim.Zaten UiError da bir error olduğu için sorun çıkmıyor.
private fun LoadState.Error.toUIError(): CustomExceptions {
    return this.error as CustomExceptions
}

fun LoadState.Error.errorMessage(): String {
    return toUIError().message ?: "An unexpected error occurred"
}

fun LoadState.Error.isRetryAvailable(): Boolean {
    return toUIError().isShowAction
}
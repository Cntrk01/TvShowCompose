package com.tvshow.tvshowapp.common.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

abstract class BaseViewModel (
    private val dispatcher: Dispatchers ?= null
): ViewModel() {

    fun launchOnMain(coroutine: suspend CoroutineScope.() -> Unit) =
        dispatcher?.let { launchOnViewModelScope(it.Main, coroutine) }

    fun launchOnIO(coroutine: suspend CoroutineScope.() -> Unit) =
        dispatcher?.let { launchOnViewModelScope(it.IO, coroutine) }

    fun launchOnDefault(coroutine: suspend CoroutineScope.() -> Unit) =
        dispatcher?.let { launchOnViewModelScope(it.Default, coroutine) }

    fun launchOnUnconfined(coroutine: suspend CoroutineScope.() -> Unit) =
        dispatcher?.let { launchOnViewModelScope(it.Unconfined, coroutine) }

    private fun launchOnViewModelScope(
        coroutineDispatcher: CoroutineDispatcher,
        coroutine: suspend CoroutineScope.() -> Unit
    ) = viewModelScope.launch(coroutineDispatcher, block = coroutine)
}
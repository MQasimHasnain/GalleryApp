package com.app.galleryapp.coroutines

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope

interface DispatcherProvider {
    fun main(): CoroutineDispatcher
    fun default(): CoroutineDispatcher
    fun io(): CoroutineDispatcher
    fun unconfined(): CoroutineDispatcher

    fun scopeMain(): CoroutineScope
    fun scopeIO(): CoroutineScope
    fun scopeDefault(): CoroutineScope
    fun scopeUnconfined(): CoroutineScope

    fun onDestroy()
}
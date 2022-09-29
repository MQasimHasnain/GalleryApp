package com.app.galleryapp.coroutines

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import javax.inject.Inject

class DispatcherProviderImp @Inject constructor() : DispatcherProvider {

    private val job = Job()

    override fun main(): CoroutineDispatcher {
        return Dispatchers.Main
    }

    override fun default(): CoroutineDispatcher {
        return Dispatchers.Default
    }

    override fun io(): CoroutineDispatcher {
        return Dispatchers.IO
    }

    override fun unconfined(): CoroutineDispatcher {
        return Dispatchers.Unconfined
    }

    override fun scopeMain(): CoroutineScope {
        return CoroutineScope(job + main())
    }

    override fun scopeIO(): CoroutineScope {
        return CoroutineScope(job + io())
    }

    override fun scopeDefault(): CoroutineScope {
        return CoroutineScope(job + default())
    }

    override fun scopeUnconfined(): CoroutineScope {
        return CoroutineScope(job + unconfined())
    }

    override fun onDestroy() {
        job.cancel()
    }
}
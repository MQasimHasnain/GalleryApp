package com.app.galleryapp.di

import com.app.galleryapp.coroutines.DispatcherProvider
import com.app.galleryapp.coroutines.DispatcherProviderImp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object CoroutineModule {

    @Provides
    fun provideDispatcher() : DispatcherProvider = DispatcherProviderImp()
}
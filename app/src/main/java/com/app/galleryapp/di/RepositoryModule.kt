package com.app.galleryapp.di

import com.app.galleryapp.features.details.repository.AlbumDetailsRepository
import com.app.galleryapp.features.details.repository.AlbumDetailsRepositoryImp
import com.app.galleryapp.features.home.repository.AlbumsRepository
import com.app.galleryapp.features.home.repository.AlbumsRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideAlbumsRepository() : AlbumsRepository{
        return AlbumsRepositoryImpl()
    }

    @Singleton
    @Provides
    fun provideAlbumDetailsRepository() : AlbumDetailsRepository{
        return AlbumDetailsRepositoryImp()
    }
}
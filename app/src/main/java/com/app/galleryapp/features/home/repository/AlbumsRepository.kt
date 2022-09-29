package com.app.galleryapp.features.home.repository

import android.content.Context
import com.app.galleryapp.features.home.model.MediaAlbumItem

interface AlbumsRepository {
    suspend fun getAllAlbums(context : Context) : ArrayList<MediaAlbumItem>
    fun onClear()
}
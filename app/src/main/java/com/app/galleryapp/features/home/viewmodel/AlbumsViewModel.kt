package com.app.galleryapp.features.home.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.galleryapp.coroutines.DispatcherProvider
import com.app.galleryapp.features.home.model.MediaAlbumItem
import com.app.galleryapp.features.home.repository.AlbumsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AlbumsViewModel @Inject constructor(private val albumsRepository: AlbumsRepository,
                                        private val dispatcherProvider: DispatcherProvider
) : ViewModel(){

    private val _albumsListData  = MutableLiveData<List<MediaAlbumItem>>()
    val albumsListData = _albumsListData

    fun getAlbums(context: Context) {
        var albumsList : List<MediaAlbumItem>
        dispatcherProvider.scopeDefault().launch{
            albumsList = albumsRepository.getAllAlbums(context)
            albumsListData.postValue( albumsList)
        }
    }

    override fun onCleared() {
        super.onCleared()
        albumsRepository.onClear()
        dispatcherProvider.onDestroy()
    }
}
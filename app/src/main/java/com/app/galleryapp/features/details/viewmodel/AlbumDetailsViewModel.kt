package com.app.galleryapp.features.details.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.galleryapp.utils.MEDIA_TYPE
import com.app.galleryapp.coroutines.DispatcherProvider
import com.app.galleryapp.features.details.model.MediaItem
import com.app.galleryapp.features.details.repository.AlbumDetailsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AlbumDetailsViewModel @Inject constructor(private val albumDetailsRepository: AlbumDetailsRepository,
                                                private val dispatcherProvider: DispatcherProvider) : ViewModel(){

    private val _mediaListData  = MutableLiveData<List<MediaItem>>()
    val mediaListData = _mediaListData

    fun getMediaItems(context: Context,
                         itemId : Long,
                         itemTitle : String,
                         itemType : MEDIA_TYPE
    ) {
        var albumsList : List<MediaItem>
        dispatcherProvider.scopeDefault().launch{
            albumsList = albumDetailsRepository.getAllMediaItems(context, itemId, itemTitle, itemType)
            mediaListData.postValue( albumsList)
        }
    }

    override fun onCleared() {
        super.onCleared()
        dispatcherProvider.onDestroy()
    }
}
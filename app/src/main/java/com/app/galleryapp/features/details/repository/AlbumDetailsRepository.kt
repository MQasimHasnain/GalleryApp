package com.app.galleryapp.features.details.repository

import android.content.Context
import com.app.galleryapp.utils.MEDIA_TYPE
import com.app.galleryapp.features.details.model.MediaItem

interface AlbumDetailsRepository {
    suspend fun getAllMediaItems(context : Context,
                                 itemId : Long,
                                 itemTitle : String,
                                 itemType : MEDIA_TYPE
    ) :
            ArrayList<MediaItem>
}
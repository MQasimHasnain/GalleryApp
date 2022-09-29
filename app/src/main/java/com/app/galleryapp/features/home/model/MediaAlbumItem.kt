package com.app.galleryapp.features.home.model

import android.net.Uri
import com.app.galleryapp.utils.MEDIA_TYPE

data class MediaAlbumItem(val itemId : Long, val title : String, val itemType : MEDIA_TYPE, var count : Int, val itemPath : Uri?
)

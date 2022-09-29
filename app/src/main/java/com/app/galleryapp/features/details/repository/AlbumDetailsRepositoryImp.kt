package com.app.galleryapp.features.details.repository

import android.content.ContentUris
import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import com.app.galleryapp.utils.Constants.ALL_PHOTOS_ID
import com.app.galleryapp.utils.MEDIA_TYPE
import com.app.galleryapp.features.details.model.MediaItem

class AlbumDetailsRepositoryImp : AlbumDetailsRepository {
    override suspend fun getAllMediaItems(
        context: Context,
        itemId: Long,
        itemTitle: String,
        itemType: MEDIA_TYPE
    ): ArrayList<MediaItem> {
        val mediaList = ArrayList<MediaItem>()
        if (itemType == MEDIA_TYPE.VIDEO_MEDIA){
            val contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI
            val projection = arrayOf(
                MediaStore.Video.Media._ID,
                MediaStore.Video.Media.DISPLAY_NAME
            )

            val mediaQuery = context.contentResolver.query(contentUri,
                projection,
                null,
                null,
                null)

            mediaQuery?.use { cursor ->

                val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media._ID)
                val nameColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DISPLAY_NAME)
                while (cursor.moveToNext()) {
                    val id = cursor.getLong(idColumn)
                    val name = cursor.getString(nameColumn)
                    val contentUri: Uri = ContentUris.withAppendedId(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        id)
                    mediaList += MediaItem(id, name, MEDIA_TYPE.VIDEO_MEDIA, contentUri)
                }
                cursor?.close()
            }

            return mediaList
        }else{
            val contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI

            val projection = arrayOf(
                MediaStore.Images.Media._ID,
                MediaStore.Images.Media.DISPLAY_NAME
            )

            val selection = if (itemId != ALL_PHOTOS_ID) "${MediaStore.Images.Media.BUCKET_ID} = $itemId" else null

            val mediaQuery = context.contentResolver.query(contentUri,
                projection,
                selection,
                null,
                null)
            mediaQuery?.use { cursor ->

                val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID)
                val nameColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME)

                while (cursor.moveToNext()) {
                    val id = cursor.getLong(idColumn)
                    val name = cursor.getString(nameColumn)
                    val contentUri: Uri = ContentUris.withAppendedId(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        id
                    )
                    mediaList += MediaItem(id, name, MEDIA_TYPE.PHOTO_MEDIA, contentUri)
                }

                cursor?.close()
            }

            return mediaList
        }
    }
}
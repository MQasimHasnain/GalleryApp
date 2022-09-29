package com.app.galleryapp.features.home.repository

import android.content.ContentUris
import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import com.app.galleryapp.utils.Constants.ALL_PHOTOS_ID
import com.app.galleryapp.utils.Constants.ALL_VIDEOS_ID
import com.app.galleryapp.utils.MEDIA_TYPE
import com.app.galleryapp.R
import com.app.galleryapp.features.home.model.MediaAlbumItem

class AlbumsRepositoryImpl : AlbumsRepository {
    override suspend fun getAllAlbums(context : Context): ArrayList<MediaAlbumItem> {
        val photosList = ArrayList<MediaAlbumItem>()
        val videosList = ArrayList<MediaAlbumItem>()

        val videoUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI
        val photoUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI

        val videoProjection = arrayOf(
            MediaStore.Video.Media._ID
        )

        val bucketProjections = arrayOf(
            MediaStore.Images.Media._ID,
            MediaStore.Images.ImageColumns.BUCKET_ID,
            MediaStore.Images.ImageColumns.BUCKET_DISPLAY_NAME)

        val imageQuery = context.contentResolver.query(
            photoUri,
            bucketProjections,
            null,
            null,
            null
        )
        imageQuery?.use { cursor ->

            val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID)
            val bucketId = cursor.getColumnIndexOrThrow( MediaStore.Images.ImageColumns.BUCKET_ID)
            val bucketName = cursor.getColumnIndexOrThrow(MediaStore.Images.ImageColumns.BUCKET_DISPLAY_NAME)

            while (cursor.moveToNext()) {
                val id = cursor.getLong(idColumn)
                val contentUri: Uri = ContentUris.withAppendedId(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    id
                )

                val bId = cursor.getLong(bucketId)
                val name = cursor.getString(bucketName)
                photosList += MediaAlbumItem(bId, name, MEDIA_TYPE.PHOTO_MEDIA, 0, contentUri)
            }
        }

        var videoContent : Uri? = null
        val videoQuery = context.contentResolver.query(videoUri,
            videoProjection,
            null,
            null,
            null)
        videoQuery?.use { cursor ->
            val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media._ID)
            while (cursor.moveToNext()) {
                val id = cursor.getLong(idColumn)
                val contentUri: Uri = ContentUris.withAppendedId(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    id
                )

                if (videoContent == null)
                    videoContent = contentUri
                videosList += MediaAlbumItem(id, "", MEDIA_TYPE.VIDEO_MEDIA, 0, contentUri)
            }

            cursor?.close()
        }

        return count(context, photosList, videosList.size, videoContent)

    }

    private fun count(context : Context,
                      images: List<MediaAlbumItem>,
                      videoCount : Int,
                      videoContentUri: Uri?):
            ArrayList<MediaAlbumItem> {
        val albums = mutableListOf<MediaAlbumItem>()
        albums.add(MediaAlbumItem(ALL_PHOTOS_ID,
            context.resources.getString(R.string.all_photos),
            MEDIA_TYPE.PHOTO_MEDIA,
            images.size,
            images[0].itemPath))

        if (videoCount > 0) {
            albums.add(
                MediaAlbumItem(
                    ALL_VIDEOS_ID,
                    context.resources.getString(R.string.all_videos),
                    MEDIA_TYPE
                        .VIDEO_MEDIA,
                    videoCount,
                    videoContentUri
                )
            )
        }

        images.forEach {
            val last = albums.lastOrNull()
            if (last?.itemId == it.itemId) {
                last.count++
            } else {
                albums.add(MediaAlbumItem(it.itemId,
                    it.title,
                    MEDIA_TYPE.PHOTO_MEDIA,
                    1,
                    it.itemPath))
            }
        }
        return ArrayList(albums)
    }

    override fun onClear() {
    }
}
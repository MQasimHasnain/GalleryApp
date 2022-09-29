package com.app.galleryapp.features.details.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.app.galleryapp.R
import com.app.galleryapp.base.BaseViewHolder
import com.app.galleryapp.features.details.model.MediaItem
import com.app.galleryapp.utils.MEDIA_TYPE
import com.bumptech.glide.Glide
import java.lang.Exception

class AlbumDetailsAdapter(
    private var mediaList : MutableList<MediaItem>
) : RecyclerView.Adapter<AlbumDetailsAdapter.AlbumsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumsViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        val view: View = inflater.inflate(R.layout.item_media, parent, false)
        return AlbumsViewHolder(view)
    }

    override fun onBindViewHolder(holder: AlbumsViewHolder, position: Int) {
        val item: MediaItem = mediaList[position]
        holder.bind(item, position)
    }

    override fun getItemCount(): Int {
        return mediaList.size
    }

    fun setData(albums: List<MediaItem>?) {
        albums?.let {
            this.mediaList.clear()
            this.mediaList.addAll(albums)
            notifyDataSetChanged()
        }
    }

    inner class AlbumsViewHolder(itemView : View) : BaseViewHolder(itemView){
        var itemImage = itemView.findViewById(R.id.itemImage) as ImageView
        var videoIcon = itemView.findViewById(R.id.videoIcon) as ImageView

        override fun bind(item: Any, position: Int, listener: (position: Int) -> Unit) {
            item?.let {
                val mediaItem = item as MediaItem
                if(mediaItem.itemType == MEDIA_TYPE.VIDEO_MEDIA){
                    videoIcon?.visibility = View.VISIBLE
                }else{
                    videoIcon?.visibility = View.GONE
                }
                try {
                    itemView?.context?.let {
                        Glide.with(it)
                            .load(mediaItem.itemUri)
                            .thumbnail(0.25f)
                            .into(itemImage)
                    }
                }catch (e : Exception){

                }
            }
        }

    }
}
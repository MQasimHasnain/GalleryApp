package com.app.galleryapp.features.home.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.app.galleryapp.R
import com.app.galleryapp.base.BaseViewHolder
import com.app.galleryapp.features.home.model.MediaAlbumItem
import com.bumptech.glide.Glide
import java.lang.Exception

class AlbumsGridAdapter(
    var mediaList : MutableList<MediaAlbumItem>,
    private val albumsListener: (MediaAlbumItem) -> Unit
) : RecyclerView.Adapter<AlbumsGridAdapter.AlbumsViewHolder>() {
    private var layoutResource : Int = R.layout.item_album_grid

    fun setLayoutResource(res : Int){
        layoutResource = res

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumsViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        val view: View = inflater.inflate(layoutResource, parent, false)
        return AlbumsViewHolder(view)
    }

    override fun onBindViewHolder(holder: AlbumsViewHolder, position: Int) {
        val item: MediaAlbumItem = mediaList[position]
        holder.bind(item, position)
        holder.itemView?.setOnClickListener{
            albumsListener(item)
        }
    }

    override fun getItemCount(): Int {
        return mediaList.size
    }

    fun setData(albums: List<MediaAlbumItem>?) {
        albums?.let {
            this.mediaList.clear()
            this.mediaList.addAll(albums)
            notifyDataSetChanged()
        }
    }

    inner class AlbumsViewHolder(itemView : View) : BaseViewHolder(itemView){
        var itemTitle = itemView.findViewById(R.id.itemTitle) as TextView
        var itemCount = itemView.findViewById(R.id.itemCount) as TextView
        var itemImage = itemView.findViewById(R.id.itemImage) as ImageView

        override fun bind(item: Any, position: Int, listener: (position: Int) -> Unit) {
            item?.let {
                val mediaItem = item as MediaAlbumItem
                itemTitle.text = mediaItem.title
                itemCount.text = ""+mediaItem.count
                try {
                    itemView?.context?.let {
                        Glide.with(it)
                            .load(mediaItem.itemPath)
                            .thumbnail(0.25f)
                            .into(itemImage)
                    }
                }catch (e : Exception){
                }
            }
        }

    }
}
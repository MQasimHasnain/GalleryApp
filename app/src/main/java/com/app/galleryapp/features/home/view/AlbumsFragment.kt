package com.app.galleryapp.features.home.view

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.galleryapp.R
import com.app.galleryapp.base.BaseFragment
import com.app.galleryapp.databinding.FragmentAlbumsBinding
import com.app.galleryapp.features.details.view.DetailsActivity
import com.app.galleryapp.features.home.model.MediaAlbumItem
import com.app.galleryapp.features.home.view.adapters.AlbumsGridAdapter
import com.app.galleryapp.features.home.viewmodel.AlbumsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AlbumsFragment : BaseFragment<FragmentAlbumsBinding>(){

    lateinit var albumsGridAdapter: AlbumsGridAdapter
    private val albumsViewModel : AlbumsViewModel by viewModels()
    private var albumsList : ArrayList<MediaAlbumItem> = ArrayList()

    companion object {
        fun newInstance() = AlbumsFragment()
    }

    override fun getLayoutResource(): Int {
        return R.layout.fragment_albums
    }

    override fun getViewBinging(): FragmentAlbumsBinding {
        return FragmentAlbumsBinding.inflate(layoutInflater)
    }

    override fun init(savedInstanceState: Bundle?) {
        super.init(savedInstanceState)
        initView()
        getContent()
    }

    private fun initView(){
        (activity as? MainActivity)?.showProgressBar()
        albumsGridAdapter = AlbumsGridAdapter(albumsList, ::onAlbumClicked)
        binding.albumsListView.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.albumsListView.adapter = albumsGridAdapter
    }

    private fun getContent(){
        albumsViewModel?.albumsListData?.observe(viewLifecycleOwner){
            it?.let {
                albumsList?.clear()
                albumsList?.addAll(it)
                albumsGridAdapter?.setData(it)
                (activity as? MainActivity)?.hideProgressBar()
            }
        }

        context?.let { albumsViewModel?.getAlbums(it) }
    }

    private fun onAlbumClicked(item : MediaAlbumItem){
        launchDetailsScreen(item)
    }

    fun switchView(isGridView : Boolean){
        if (isGridView){
            albumsGridAdapter = AlbumsGridAdapter(albumsList, ::onAlbumClicked)
            binding.albumsListView.layoutManager = GridLayoutManager(requireContext(), 2)
            binding.albumsListView.adapter = albumsGridAdapter
            albumsGridAdapter?.notifyDataSetChanged()

        }else{
            albumsGridAdapter = AlbumsGridAdapter(albumsList, ::onAlbumClicked)
            albumsGridAdapter?.setLayoutResource(R.layout.item_album_list)
            binding.albumsListView.layoutManager = LinearLayoutManager(requireContext())
            binding.albumsListView.adapter = albumsGridAdapter
            albumsGridAdapter?.notifyDataSetChanged()
        }
    }

    private fun launchDetailsScreen(item : MediaAlbumItem){
        activity?.let {
            it.startActivity(DetailsActivity.detailsActivityIntent(it,
                item.itemId,
                item.title,
                item.itemType))
        }
    }
}
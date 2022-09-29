package com.app.galleryapp.features.details.view

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.app.galleryapp.utils.Constants.ALBUM_ID
import com.app.galleryapp.utils.Constants.ALBUM_TITLE
import com.app.galleryapp.utils.Constants.MEDIA_TYPE
import com.app.galleryapp.utils.MEDIA_TYPE
import com.app.galleryapp.R
import com.app.galleryapp.base.BaseFragment
import com.app.galleryapp.databinding.FragmentDetailsBinding
import com.app.galleryapp.features.details.view.adapters.AlbumDetailsAdapter
import com.app.galleryapp.features.details.viewmodel.AlbumDetailsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsFragment : BaseFragment<FragmentDetailsBinding>() {

    lateinit var mediaItemsAdapter : AlbumDetailsAdapter
    private val albumDetailsViewModel : AlbumDetailsViewModel by viewModels()
    private var itemId : Long = 0
    private var itemTitle : String? = null
    private var itemType : MEDIA_TYPE = com.app.galleryapp.utils.MEDIA_TYPE.PHOTO_MEDIA

    companion object {
        fun newInstance(albumId : Long, albumTitle : String, mediaType: MEDIA_TYPE) : DetailsFragment{
            val fragment = DetailsFragment()
            var bundle = Bundle()
            bundle.putLong(ALBUM_ID, albumId)
            bundle.putString(ALBUM_TITLE, albumTitle)
            bundle.putSerializable(MEDIA_TYPE, mediaType)
            fragment.arguments = bundle

            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        itemId = arguments?.getLong(ALBUM_ID)?:0
        itemTitle = arguments?.getString(ALBUM_TITLE)
        itemType = arguments?.getSerializable(MEDIA_TYPE) as MEDIA_TYPE
    }

    override fun getLayoutResource(): Int {
        return R.layout.fragment_details
    }

    override fun getViewBinging(): FragmentDetailsBinding {
        return FragmentDetailsBinding.inflate(layoutInflater)
    }

    override fun init(savedInstanceState: Bundle?) {
        super.init(savedInstanceState)
        initView()
        getContent()
    }

    private fun initView(){
        mediaItemsAdapter = AlbumDetailsAdapter(mutableListOf())
        binding.mediaListView.layoutManager = GridLayoutManager(requireContext(), 4)
        binding.mediaListView.adapter = mediaItemsAdapter
    }

    private fun getContent(){
        albumDetailsViewModel?.mediaListData?.observe(viewLifecycleOwner){
            it?.let { mediaItemsAdapter?.setData(it) }
        }

        context?.let { albumDetailsViewModel?.getMediaItems(it, itemId, itemTitle?:"", itemType) }
    }
}
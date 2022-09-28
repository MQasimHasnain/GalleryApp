package com.app.galleryapp.features.home.view

import com.app.galleryapp.R
import com.app.galleryapp.base.BaseFragment
import com.app.galleryapp.databinding.FragmentAlbumsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AlbumsFragment : BaseFragment<FragmentAlbumsBinding>(){

    companion object {
        fun newInstance() = AlbumsFragment()
    }

    override fun getLayoutResource(): Int {
        return R.layout.fragment_albums
    }

    override fun getViewBinging(): FragmentAlbumsBinding {
        return FragmentAlbumsBinding.inflate(layoutInflater)
    }
}
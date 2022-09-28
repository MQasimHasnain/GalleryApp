package com.app.galleryapp.features.home.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.app.galleryapp.R
import com.app.galleryapp.base.BaseActivity
import com.app.galleryapp.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setActionBar(binding.toolbar)
        addFragment(AlbumsFragment.newInstance(),
            R.id.container,
            clearBackStack = true,
            addToBackStack = true)
    }

    override fun getLayoutRes(): Int {
        return R.layout.activity_main
    }

    override fun showProgressBar() {
        binding.progressBar.visibility = View.VISIBLE
    }

    override fun hideProgressBar() {
        binding.progressBar.visibility = View.GONE
    }

    override fun getViewBinging(): ActivityMainBinding {
        return ActivityMainBinding.inflate(layoutInflater)
    }
}
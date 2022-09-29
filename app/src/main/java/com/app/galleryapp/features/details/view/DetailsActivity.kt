package com.app.galleryapp.features.details.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import com.app.galleryapp.utils.Constants
import com.app.galleryapp.utils.Constants.ALBUM_ID
import com.app.galleryapp.utils.Constants.ALBUM_TITLE
import com.app.galleryapp.utils.MEDIA_TYPE
import com.app.galleryapp.R
import com.app.galleryapp.base.BaseActivity
import com.app.galleryapp.databinding.ActivityDetailsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsActivity : BaseActivity<ActivityDetailsBinding>(){

    companion object {
        fun detailsActivityIntent(context : Context,
                                  albumId : Long,
                                  albumTitle: String,
                                  mediaType: MEDIA_TYPE
        ) : Intent{
            var bundle = Bundle()
            bundle.putLong(ALBUM_ID, albumId)
            bundle.putString(ALBUM_TITLE, albumTitle)
            bundle.putSerializable(Constants.MEDIA_TYPE, mediaType)
            val intent = Intent(context, DetailsActivity::class.java)
            intent.putExtras(bundle)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setActionBar(binding.toolbar)
        actionBar?.setDisplayHomeAsUpEnabled(true)
        val albumId = intent.getLongExtra(ALBUM_ID, 0L)
        val albumTitle = intent.getStringExtra(ALBUM_TITLE)
        val mediaType: MEDIA_TYPE = intent.getSerializableExtra(Constants.MEDIA_TYPE) as MEDIA_TYPE
        actionBar?.title = albumTitle
        addFragment(
            DetailsFragment.newInstance(albumId, albumTitle?:"", mediaType),
            R.id.container,
            clearBackStack = true,
            addToBackStack = true
        )
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            android.R.id.home -> { finish()
            return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun getLayoutRes(): Int {
        return R.layout.activity_details
    }

    override fun showProgressBar() {
        binding.progressBar.visibility = View.VISIBLE
    }

    override fun hideProgressBar() {
        binding.progressBar.visibility = View.GONE
    }

    override fun getViewBinging(): ActivityDetailsBinding {
        return ActivityDetailsBinding.inflate(layoutInflater)
    }
}
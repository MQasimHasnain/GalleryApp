package com.app.galleryapp.features.home.view

import android.Manifest
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.app.galleryapp.R
import com.app.galleryapp.base.BaseActivity
import com.app.galleryapp.databinding.ActivityMainBinding
import com.app.galleryapp.utils.ManagePermissions
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {

    private val PermissionsRequestCode = 123
    private lateinit var managePermissions: ManagePermissions
    private var isGridView : Boolean = true
    private lateinit var fragment : AlbumsFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setActionBar(binding.toolbar)

        val list = listOf<String>(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )

        managePermissions = ManagePermissions(this,list,PermissionsRequestCode)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            if(managePermissions.checkPermissions()){
                fragment = AlbumsFragment.newInstance()
                addFragment(
                    fragment,
                    R.id.container,
                    clearBackStack = true,
                    addToBackStack = true)
            }
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.custom_menu, menu);
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.switch_view ->{
                isGridView = !isGridView
                fragment?.let {
                    if (it.isAdded){
                        it.switchView(isGridView)
                    }
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if(managePermissions.processPermissionsResult(requestCode, permissions, grantResults)){
            fragment = AlbumsFragment.newInstance()
            addFragment(
                fragment,
                R.id.container,
                clearBackStack = true,
                addToBackStack = true)
        }else{
            finish()
        }
    }
}
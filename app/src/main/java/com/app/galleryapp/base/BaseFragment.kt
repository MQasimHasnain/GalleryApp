package com.app.galleryapp.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

abstract class BaseFragment<VB : ViewBinding> : Fragment() {

    open lateinit var binding: VB

    lateinit var fragmentHelper: FragmentNavigationHelper

    @LayoutRes
    abstract fun getLayoutResource(): Int

    abstract fun getViewBinging(): VB

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        init(inflater, container!!)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init(savedInstanceState)
    }

    override fun onAttach(context: Context) {
        try {
            fragmentHelper = context as FragmentNavigationHelper
        } catch (e: Exception) {
            throw e
        }

        super.onAttach(context)
    }

    private fun init(inflater: LayoutInflater, container: ViewGroup) {
        binding = getViewBinging()
    }

    open fun init(savedInstanceState: Bundle?) {}

    open fun showProgress() {
        (activity as? BaseActivity<*>)?.showProgressBar()
    }

    open fun hideProgress() {
        (activity as? BaseActivity<*>)?.hideProgressBar()
    }

    interface FragmentNavigationHelper {
        fun addFragment(fragment: Fragment, layoutId: Int, clearBackStack: Boolean, addToBackStack: Boolean)
        fun replaceFragment(fragment: Fragment, layoutId: Int, clearBackStack: Boolean, addToBackStack: Boolean)
        fun onBack()
        fun getCurrentFragment(): Fragment?
    }
}
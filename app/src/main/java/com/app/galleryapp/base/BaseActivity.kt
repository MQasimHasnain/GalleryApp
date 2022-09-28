package com.app.galleryapp.base

import android.os.Bundle
import android.view.KeyEvent
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewbinding.ViewBinding
import java.util.*

abstract class BaseActivity<VB : ViewBinding> : FragmentActivity(), BaseFragment.FragmentNavigationHelper{

    lateinit var binding: VB

    private var currFragment: Fragment? = null

    private val fragments = Stack<Fragment>()

    @LayoutRes
    abstract fun getLayoutRes(): Int

    abstract fun showProgressBar()

    abstract fun hideProgressBar()

    abstract fun getViewBinging(): VB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = getViewBinging()
        setContentView(binding.root)
    }

    /**
     * Adds the fragment to the referenced layout
     *
     * @param fragment Fragment to be added
     * @param resourceId Resource Id where fragment to be added
     * @param clearBackStack true/false telling if should clear fragment back stack or not
     * @param addToBackStack true/false telling if should add fragment to back stack or not
     */
    override fun addFragment(
        fragment: Fragment, layoutId: Int, clearBackStack: Boolean, addToBackStack: Boolean
    ) {
        if (clearBackStack) {
            clearFragmentBackStack()
        }
        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(layoutId, fragment)
        if (addToBackStack) transaction.addToBackStack(null)
        transaction.commit()

        setCurrentFragment(fragment)
        if (addToBackStack) fragments.push(fragment)

        onFragmentBackStackChanged()
    }

    /**
     * Replaces the fragment in the referenced layout
     *
     * @param fragment Fragment to be replaced
     * @param resourceId Resource Id where fragment to be replaced
     * @param clearBackStack true/false telling if should clear fragment back stack or not
     * @param addToBackStack true/false telling if should add fragment to back stack or not
     */
    override fun replaceFragment(
        fragment: Fragment, layoutId: Int, clearBackStack: Boolean, addToBackStack: Boolean
    ) {
        if (clearBackStack) {
            clearFragmentBackStack()
        }

        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(layoutId, fragment)
        if (addToBackStack) {
            transaction.addToBackStack(null)
        }
        transaction.commit()

        setCurrentFragment(fragment)
        fragments.push(fragment)

        onFragmentBackStackChanged()
    }

    /**
     * Override this method if need to perform any action on fragment transactions
     */
    open fun onFragmentBackStackChanged() {

    }

    /**
     * Sets the current current fragment in local stack
     */
    private fun setCurrentFragment(currentFragment: Fragment) {
        this.currFragment = currentFragment
    }

    /**
     * Clears the fragment back stack
     */
    private fun clearFragmentBackStack() {
        val fm = supportFragmentManager
        for (i in 0 until fm.backStackEntryCount - 1) {
            fm.popBackStack()
        }

        if (!fragments.isEmpty()) {
            currFragment = null
            fragments.clear()
        }
    }

    /**
     * Overridden method
     * Gets invoked when device's back button is pressed
     */
    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            onBack()
            return true
        }
        return super.onKeyDown(keyCode, event)
    }

    override fun getCurrentFragment(): Fragment? {
        return currFragment
    }

    /**
     *Pops fragment from back stack. If only one fragment then finishes activity
     */
    override fun onBack() {
        if (supportFragmentManager.backStackEntryCount <= 1) {
            finish()
            return
        }
        supportFragmentManager.popBackStack()
        fragments.pop()
        currFragment = when {
            fragments.isEmpty() -> null
            fragments.peek() is BaseFragment<*> -> fragments.peek() as BaseFragment<*>
            else -> null
        }

        onFragmentBackStackChanged()
    }
}
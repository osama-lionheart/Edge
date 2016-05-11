package com.example.edge.common

import android.support.v7.widget.Toolbar
import android.view.MenuItem
import mortar.Presenter
import mortar.bundler.BundleService
import javax.inject.Inject

@ScopeSingleton(MainActivityComponent::class)
class ActivityOwner @Inject constructor() : Presenter<MainActivity>() {
    override fun extractBundleService(view: MainActivity?): BundleService? {
        return BundleService.getBundleService(view)
    }

    fun getActivity(): MainActivity? {
        return view;
    }

    var onOptionsItemsSelectedListener: ((MenuItem) -> Boolean)? = null

    fun setToolbar(toolbar: Toolbar, hasHome: Boolean, hasUp: Boolean) {
        view?.setSupportActionBar(toolbar)

        if (hasUp) {
            view?.supportActionBar?.setDisplayHomeAsUpEnabled(true)
            view?.supportActionBar?.setDisplayShowHomeEnabled(true)
        } else if (hasHome) {
            view?.supportActionBar?.setDisplayHomeAsUpEnabled(true)
            view?.supportActionBar?.setDisplayShowHomeEnabled(true)
        }
    }

    fun onOptionsItemSelected(item: MenuItem): Boolean {
        return onOptionsItemsSelectedListener?.invoke(item) ?: false
    }
}

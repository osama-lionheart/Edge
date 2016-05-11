package com.example.edge.common

import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.MenuItem
import javax.inject.Inject

@ScopeSingleton(MainActivityComponent::class)
class ActivityOwner @Inject constructor() : Presenter<MainActivity> {
    var activity: MainActivity? = null
    var onOptionsItemsSelectedListener: ((MenuItem) -> Boolean)? = null

    override fun onEnter() {
        Log.e("EDGE", "ActivityOwner.onEnter")
    }

    override fun attach(activity: MainActivity) {
        this.activity = activity
    }

    override fun detach(activity: MainActivity) {
        this.activity = null
    }

    override fun onExit() {
        Log.e("EDGE", "ActivityOwner.onExit")
    }

    fun setToolbar(toolbar: Toolbar, hasHome: Boolean, hasUp: Boolean) {
        activity?.setSupportActionBar(toolbar)

        if (hasUp) {
            activity?.supportActionBar?.setDisplayHomeAsUpEnabled(true)
            activity?.supportActionBar?.setDisplayShowHomeEnabled(true)
        } else if (hasHome) {
            activity?.supportActionBar?.setDisplayHomeAsUpEnabled(true)
            activity?.supportActionBar?.setDisplayShowHomeEnabled(true)
        }
    }

    fun onOptionsItemSelected(item: MenuItem): Boolean {
        return onOptionsItemsSelectedListener?.invoke(item) ?: false
    }
}

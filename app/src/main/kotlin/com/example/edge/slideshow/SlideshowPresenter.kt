package com.example.edge.slideshow

import android.util.Log
import android.view.View
import com.example.edge.common.HandlesBack
import com.example.edge.common.Presenter
import com.example.edge.common.ScopeSingleton
import javax.inject.Inject

@ScopeSingleton(SlideshowComponent::class)
class SlideshowPresenter @Inject constructor() : Presenter<View>, HandlesBack {
    private var view: View? = null

    override fun onEnter() {
        Log.e("EDGE", "SlideshowPresenter.onEnter")
    }

    override fun attach(view: View) {
        this.view = view
    }

    override fun detach(view: View) {
        this.view = null
    }

    override fun onExit() {
        Log.e("EDGE", "SlideshowPresenter.onExit")
    }

    override fun onBackPressed(): Boolean {
        return false
    }
}

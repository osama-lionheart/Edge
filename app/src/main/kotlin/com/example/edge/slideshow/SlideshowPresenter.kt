package com.example.edge.slideshow

import android.support.v7.widget.Toolbar
import android.view.View
import com.example.edge.R
import com.example.edge.common.ActivityOwner
import com.example.edge.common.Presenter
import com.example.edge.common.ScopeSingleton
import javax.inject.Inject

@ScopeSingleton(SlideshowComponent::class)
class SlideshowPresenter @Inject constructor(private val activityOwner: ActivityOwner) : Presenter<View> {
    private var view: View? = null

    override fun attach(view: View) {
        this.view = view

        val toolbar = view.findViewById(R.id.toolbar) as Toolbar
        activityOwner.setToolbar(toolbar, false, true)
    }

    override fun detach(view: View) {
        this.view = null
    }
}
